package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.Cleanup;
import org.example.entity.Company;
import org.example.entity.Role;
import org.example.entity.User;
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
}