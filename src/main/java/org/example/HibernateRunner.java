package org.example;


import java.lang.reflect.Proxy;
import java.util.Optional;
import org.example.dao.PaymentRepository;
import org.example.entity.Payment;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {

    public static void main(String[] args) {

//        Company companyGoogle = Company.builder().name("Google").build();
//        Company companyYandex = Company.builder().name("Yandex").build();
//        Company companyYahoo = Company.builder().name("Yahoo").build();
//
//        Configuration conf = new Configuration();
//        //add custom date converter
//        conf.addAttributeConverter(CustomBirthdayConverter.class, true);
//        conf.configure();
//
//        try (final SessionFactory sessionFactory = conf.buildSessionFactory(); final Session session = sessionFactory.openSession();) {
//
//	   System.out.println("ok");
//
//	   session.beginTransaction();

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

//	   final User userYahoo = session.get(User.class, 3);
//	   session.delete(userYahoo);

        //entity Graph
//
//	   final RootGraph<User> entityGraph = session.createEntityGraph(User.class);
//	   entityGraph.addAttributeNodes("company","userChats");
//	   final SubGraph<UserChat> userChats =
//		  entityGraph.addSubgraph("userChats", UserChat.class);
//	   userChats.addAttributeNodes("chat");
//
//	   //for entitys
//	   final Map<String, Object> properties =
//		  Map.of(GraphSemantic.LOAD.getJpaHintName(), entityGraph);
//
//	   final User user = session.find(User.class, 1, properties);
//	   System.out.println(user.getCompany().getName());
//	   System.out.println(user.getUserChats().size());
//
//	   //for HQL
//	   session.createQuery("select u from User u ", User.class)
//		  .setHint(GraphSemantic.LOAD.getJpaHintName(),entityGraph).list();
//	   System.out.println(user.getCompany().getName());
//	   System.out.println(user.getUserChats().size());

//	   Session session1 = sessionFactory.openSession();
//
//
//	   // 2 clients trying to get same ticket in the same time
//	   final Payment payment = session.get(Payment.class, 1, LockMode.OPTIMISTIC);
//	   payment.setAmount(payment.getAmount()+10);
//
//	   final Payment payment1 = session1.get(Payment.class, 1,LockMode.OPTIMISTIC);
//	   payment1.setAmount(payment1.getAmount()+30);
//		// should be Optimistic lock exception, например : два клиента хотят одновременно заказать билет на самолет, чтобы не произошло неприятностей, оди клиент полусит булет, а второй получит ответ - извините
//	   	// можно испольщовать уровень изоляции транзакции - Serialzzable, но это очень дорого, по этому используются Локи
//	   	// это исключение будет проброшено выше, обработано, и клиент полуить ответ- сори
//
//	   session.getTransaction().commit();
//	   session1.getTransaction().commit();
//        } catch (HibernateException e) {
//	   throw new RuntimeException(e);
//        }
//    }

        //CRUD

        try (SessionFactory factory = HibernateUtil.buildSessionFactory();) {
	   Session session =
		  (Session) Proxy.newProxyInstance(
			 SessionFactory.class.getClassLoader(),
			 new Class[]{Session.class},
			 (proxy, method, args1) -> method.invoke(factory.getCurrentSession(), args1));

	   session.beginTransaction();

//	   var payment = new PaymentRepository(factory);
	   var payment = new PaymentRepository(session);
	   final Optional<Payment> byId = payment.findById(1);
	   System.out.printf(byId.get().toString());


        } catch (HibernateException e) {
	   throw new RuntimeException(e);
        }

    }
}