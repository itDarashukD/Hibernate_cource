package org.example.dao;

import static org.example.entity.QPayment.payment;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Payment;
import org.example.entity.QPayment;
import org.example.entity.QUser;
import org.example.entity.User;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {


    private static final User user = new User();

    // by Criteria
    public List<User> findAllUsers(Session session) {
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<User> critery = criteriaBuilder.createQuery(User.class);

        final Root<User> user = critery.from(User.class);
        critery.select(user);

        return session.createQuery(critery).list();
    }

    // by Criteria
    public List<User> findAllUsersBySex(Session session, String sex) {
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<User> critery = criteriaBuilder.createQuery(User.class);
        final Root<User> user = critery.from(User.class);

        critery.select(user).where(criteriaBuilder.equal(user.get("personalInfo").get("sex"), sex));

        return session.createQuery(critery).list();
    }

    // by Criteria with hibernate-jpamodelgen library
    public List<User> findAllUsersBySexWithLibrary(Session session, String sex) {
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<User> critery = criteriaBuilder.createQuery(User.class);
        final Root<User> user = critery.from(User.class);

//        critery.select(user).where(criteriaBuilder.equal(
//                user.get(User_.personalInfo)
//                        .get(PersonalInfo_.sex), sex));

        return session.createQuery(critery).list();
    }

    // by CriteriaDSL
    public List<User> findAllUsersWithDSL(Session session) {
        return new JPAQuery<User>(session).select(QUser.user).from(QUser.user).fetch();
    }


    // by CriteriaDSL
    public List<User> findAllUsersBySexWithDSL(Session session, String sex) {
        return new JPAQuery<User>(session).select(QUser.user)
	       .from(QUser.user)
	       .where(QUser.user.personalInfo().sex.eq(sex))
	       .fetch();

    }

    // by CriteriaDSL
    public List<User> findLimitedUsersOrderedByBithdayWithDSL(Session session, int limit) {
        return new JPAQuery<User>(session).select(QUser.user)
	       .from(QUser.user)
	       .orderBy(new OrderSpecifier<>(Order.ASC, QUser.user.birthDate))
	       .limit(limit)
	       .fetch();

    }

    // by CriteriaDSL
    public List<User> findAllByCompanyNameWithDSL(Session session, String companyName) {
        return new JPAQuery<User>(session).select(QUser.user)
	       .from(QUser.user.company())
	       .join(QUser.user.company().users, QUser.user)
	       .where(QUser.user.company().name.eq(companyName))
	       .fetch();

    }

//    // by CriteriaDSL
//    public List<User> findAllPaymentsBySexWithDSL(Session session, String sex) {
//        return new JPAQuery<Payment>(session)
//	       .select(payment)
//	       .from(QUser.user.company())
//	       .join(QUser.user.company().users, QUser.user)
//	       .join(QUser.user.payments, payment)
//	       .where(QUser.user.personalInfo().sex.eq(sex))
//	       .orderBy(QUser.user.personalInfo().sex.asc(),payment.amount.asc())
//	       .fetch();
//    }


    //    // by CriteriaDSL
    public Double findAvaragePaymentAmountByFirsAndLastName(Session session, String firstname, String  lastname) {
        return new JPAQuery<Double>(session)
	       .select(payment.amount.avg())
	       .from(payment)
	       .join(payment.receiver(), QUser.user)
	       .where(QUser.user.firstName.eq(firstname)
	       .and(QUser.user.lastname.eq(lastname)))
	       .fetchOne();
    }

    //    // by CriteriaDSL
//	       .groupBy(QUser.user.company().name)
    public List<Tuple> findACompanyNamesWithAvarageUserPaymantsOrderedByConpanyName(Session session) {
        return new JPAQuery<Tuple>(session)
	       .select(QUser.user.company().name, payment.amount.avg())
	       .from(QUser.user.company())
	       .join(QUser.user.company().users, QUser.user)
	       .join(QUser.user.payments, QPayment.payment)
	       .orderBy(QUser.user.company().name.asc())
	       .fetch();
    }

    //    // by CriteriaDSL
    public List<Tuple> IsItPosible(Session session) {
        return new JPAQuery<Tuple>(session).select(QUser.user, payment.amount.avg())
	       .from(QUser.user)
	       .join(QUser.user.payments, payment)
	       .groupBy(QUser.user.id)
	       .having(payment.amount.avg().gt(  //gt - greate then (больше чем)

		      new JPAQuery<Double>(session).select(payment.amount.avg())
			     .from(payment))

	       )
	       .orderBy(QUser.user.firstName.asc())
	       .fetch();

    }
}
