package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
import org.hibernate.query.Query;
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

    @Test
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

        Company companyOracle = session.get(Company.class, 7);
        companyOracle.getUsers().removeIf(user -> user.id == 4);

        session.getTransaction().commit();
    }

    @Test
    void testOneToOne() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

        final Company company = session.get(Company.class, 7);

        Profile profile = Profile.builder().language("Bl").street("Sovetskaja2").build();

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

        Chat chat = session.get(Chat.class, 1);

        final User user = session.get(User.class, 1);

        UserChat userChat = UserChat.builder()

	       .build();

        userChat.setCreated_by("Anton");
        userChat.setCreated_at(Instant.now());

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

        Company company = Company.builder().name("h2").build();

        session.save(company);

        session.getTransaction().commit();
    }

//    @Test
//    void checkInheritance() {
//        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup final Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Company company = Company.builder()
//	       .name("h2H2")
//	       .build();
//
//        session.save(company);
//
//        final Programmer
//	       testH2Name =
//	       Programmer.builder()
//		      .userName("testH2Name")
//		      .language(Language.JAVA)
//		      .company(company)
//		      .build();
//
//        final Manager
//	       manager =
//	       Manager.builder()
//		      .userName("testManager")
//		      .project("java project")
//		      .company(company)
//		      .build();
//
//
//        session.save(manager);
//
//        session.flush();
//        session.clear();
//
//        final Programmer programmer = session.get(Programmer.class, 1);
//        session.get(User.class,2);
//
//        session.getTransaction().commit();
//    }


    @Test
    void checkHQL() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.getTransaction().commit();
    }


    @Test
    void saveUser() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();
        session.beginTransaction();

//        get all users,
//        final Query selectUFromUserU = session.createQuery("select u From User u");
//        final List users = selectUFromUserU.list();
//        final List users = selectUFromUserU.list();

        //get by age
//        final Query selectFromUserByAge = session.createQuery("""
//	       select u From User u
//	       where u.age = '1'
//
//	       """);
//        final List users = selectFromUserByAge.list();

        //get by age and company with automatic Join
//        final Query selectFromUserByAgeAndCompany = session.createQuery("""
//	       select u From User u
//	       where u.age = :age
//	       and u.company.name = :company
//
//	       """)
//	       .setParameter("age",1)
//	       .setParameter("company","qwe");

        //get by age and company with Join by hand
        final Query selectFromUserByAgeAndCompany = session.createQuery(
        """
	       select u From User u
	       left join
	       u.company c
	       where u.age = :age
	       and
	       c.name = :company 
	               
	       """)
	       .setParameter("age",1)
	       .setParameter("company","qwe");



        final List users = selectFromUserByAgeAndCompany.list();

        System.out.println(users);
        session.getTransaction().commit();
    }


}