package org.example;

import java.time.LocalDate;
import org.example.converter.CustomBirthdayConverter;
import org.example.entity.CustomBirthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {

    public static void main(String[] args) {

        Configuration conf = new Configuration();
        //add custom date converter
        conf.addAttributeConverter(CustomBirthdayConverter.class,true);
        conf.configure();

        try (final SessionFactory sessionFactory = conf.buildSessionFactory(); final Session session = sessionFactory.openSession();) {

	   System.out.println("ok");

	   session.beginTransaction();

	   final User user1 =
		  User.builder()
			 .firstName("dzmitry")
			 .userName("customBirthday")
			 .lastname("aliaks")
			 .birthDate(LocalDate.of(2000, 01, 01))
			 .age(31)
			 .role(Role.ADMIN)
			 .customBirthday(
				new CustomBirthday(LocalDate.of(2000, 01, 01)))
			 .build();

	   session.save(user1);

	   session.update(user1); // throw exception if user not persent in DB
	   session.saveOrUpdate(user1); // no exception
	   session.delete(user1); //remove
	   session.get(User.class,"dara"); //dara - primary key in table


	   session.getTransaction().commit();
        } catch (HibernateException e) {
	   throw new RuntimeException(e);
        }
    }
}
