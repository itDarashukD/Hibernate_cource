package org.example;

import java.time.LocalDate;
import org.example.converter.CustomBirthdayConverter;
import org.example.entity.Company;
import org.example.entity.CustomBirthday;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateRunner {

    public static void main(String[] args) {

        Company companyGoogle = Company.builder().name("Google").build();
        Company companyYandex = Company.builder().name("Yandex").build();
        Company companyYahoo = Company.builder().name("Yahoo").build();

        Configuration conf = new Configuration();
        //add custom date converter
        conf.addAttributeConverter(CustomBirthdayConverter.class, true);
        conf.configure();

        try (final SessionFactory sessionFactory = conf.buildSessionFactory(); final Session session = sessionFactory.openSession();) {

	   System.out.println("ok");

	   session.beginTransaction();

//	   final User user1 = User.builder()
//			 .firstName("dzmitry")
//			 .userName("with yahoo")
//			 .lastname("aliaks")
//			 .birthDate(LocalDate.of(2000, 01, 01))
//			 .age(31)
//			 .role(Role.ADMIN)
//			 .customBirthday(new CustomBirthday(LocalDate.of(2000, 01, 01)))
//
//			 .personalInfo(PersonalInfo.builder()
//				.sex("mail")
//				.moneyCount(100l)
//				.build())
//			 .company(companyYahoo)
//			 .build();

//	   session.save(user1);
//	   session.update(user1); // throw exception if user not persent in DB
//	   session.saveOrUpdate(user1); // no exception
//	   session.delete(user1); //remove
//	   session.get(User.class,"dara"); //dara - primary key in table

	   /*return from DB only 1 time, second and third call will get from 1st level catch*/

//	   user1.setUserName("aaaaaa");
//	   session.refresh(user1); //refresh- обновит кэш ИЗ БД  (аааа - не будет в БД)

//	   session.isDirty(); //есть-ли в кеше данные, которые уже есть в кеше, но еще нет в БД

//	   session.saveOrUpdate(companyYahoo); // если используем @Cascade.ALL, то сейв во вторую табл будет автоматически
//	   session.saveOrUpdate(user1);

	   final User userYahoo = session.get(User.class, 3);
	   session.delete(userYahoo);

	   session.getTransaction().commit();
        } catch (HibernateException e) {
	   throw new RuntimeException(e);
        }
    }
}
