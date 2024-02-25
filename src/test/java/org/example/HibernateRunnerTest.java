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
import org.example.entity.User;
import org.junit.jupiter.api.Test;

class HibernateRunnerTest {

    @Test
    void testHibernateApi() throws SQLException, IllegalAccessException {
        final User
	       testUser =
	       User.builder()
		      .userName("dara2")
		      .firstName("dzmitry")
		      .lastname("aliaks")
		      .birthDate(LocalDate.of(2000, 01, 01))
		      .age(31)
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
}