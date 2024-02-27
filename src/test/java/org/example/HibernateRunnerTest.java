package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.Cleanup;
import org.example.entity.Chat;
import org.example.entity.Company;
import org.example.entity.Profile;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.entity.UserChat;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

class HibernateRunnerTest {

    final User
	   testUser =
	   User.builder()
		  .userName("dara4")
		  .firstName("dzmitry")
		  .lastname("aliaks")
		  .birthDate(LocalDate.of(2000, 01, 01))
		  .age(31)
		  .role(Role.ADMIN)
		  .build();

    //    @Test
    void testHibernateApi() throws SQLException, IllegalAccessException {
        final User
	       testUser =
	       User.builder()
		      .userName("dara4")
		      .firstName("dzmitry")
		      .lastname("aliaks")
		      .birthDate(LocalDate.of(2000, 01, 01))
		      .age(31)
		      .role(Role.ADMIN)
		      .build();

        String sql = """
	       insert into %s
	       (%s)
	       values
	       (%s)
	       """;

        final String
	       tableName =
	       Optional.ofNullable(testUser.getClass().getAnnotation(Table.class))
		      .map(annotValue -> annotValue.name())
		      .orElse(testUser.getClass().getName());

        final Field[] fields = testUser.getClass().getDeclaredFields();

        final String
	       columnNames =
	       Arrays.stream(fields)
		      .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
			     .map(Column::name)
			     .orElse(field.getName()))
		      .collect(Collectors.joining(", "));

        final String
	       columnValues =
	       Arrays.stream(fields).map(field -> "?").collect(Collectors.joining(", "));

        final Connection
	       connection =
	       DriverManager.getConnection("jdbc:postgresql://localhost:5432/hiber",
		      "postgres",
		      "root");

        final PreparedStatement
	       preparedStatement =
	       connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));

        for (int i = 0; i < fields.length; i++) {
	   fields[i].setAccessible(true);

	   preparedStatement.setObject(i + 1, fields[i].get(testUser));
        }
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    //@Cleanup - try with resources by lombok
    @Test
    void testOneToMany() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

        final Company company = session.get(Company.class, 1);
        System.out.println(company.getUsers());

        session.getTransaction().commit();


    }




    @Test
    void testOrphalRemoval() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company companyOracle =session.get(Company.class, 7);
        companyOracle.getUsers().removeIf(user -> user.id == 4);

        session.getTransaction().commit();
    }

    @Test
    void testOneToOne() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

        final Company company = session.get(Company.class, 7);

        Profile profile = Profile.builder()
	       .language("Bl")
	       .street("Sovetskaja2")
	       .build();

        final User
	       testUser5 =
	       User.builder()
		      .userName("dara6")
		      .firstName("dzmitry6")
		      .lastname("aliaks6")
		      .birthDate(LocalDate.of(2000, 01, 01))
		      .age(31)
		      .role(Role.ADMIN)
		      .company(company)
		      .build();

        session.save(testUser5);
        profile.setUser(testUser5);
        session.save(profile);

        session.getTransaction().commit();
    }

//    @Test
//    void testManyToMany() {
//        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup final Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Chat chat = Chat.builder()
//	       .name("chatNAme")
//	       .build();
//
//        final User user = session.get(User.class, 1);
//        user.addChat(chat);
//
//        session.save(chat);
//
//        session.getTransaction().commit();
//    }


    @Test
    void testManyToMany_as2TimesOneToMany() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

        Chat chat = session.get(Chat.class,1);

        final User user = session.get(User.class, 1);

        UserChat userChat = UserChat.builder()
	       .created_at(Instant.now())
	       .created_by("Dzmmitry")
	       .build();

        userChat.setChat(chat);
        userChat.setUser(user);

        session.save(userChat);

        session.getTransaction().commit();
    }

    @Test
    void checkIfH2() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

     	Company company = Company.builder()
	        .name("h2")
	        .build();

        session.save(company);

        session.getTransaction().commit();
    }

}