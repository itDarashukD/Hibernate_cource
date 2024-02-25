package org.example;

import java.time.LocalDate;
import org.example.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {

    public static void main(String[] args) {

        Configuration conf = new Configuration();
        conf.configure();

        try (final SessionFactory sessionFactory = conf.buildSessionFactory(); final Session session = sessionFactory.openSession();) {

	   System.out.println("ok");

	   session.beginTransaction();

	   final User user1 =
		  User.builder()
			 .firstName("dzmitry")
			 .userName("dara")
			 .lastname("aliaks")
			 .birthDate(LocalDate.of(2000, 01, 01))
			 .age(31)
			 .build();

	   session.save(user1);

	   session.getTransaction().commit();
        } catch (HibernateException e) {
	   throw new RuntimeException(e);
        }
    }
}
