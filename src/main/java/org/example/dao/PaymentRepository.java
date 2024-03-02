package org.example.dao;

import javax.persistence.EntityManager;
import org.example.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentRepository extends BaseRepository<Integer, Payment>{


//    public PaymentRepository( SessionFactory factory) {
//        super(Payment.class, factory);
//    }

    public PaymentRepository( EntityManager manager) {
        super(Payment.class, manager);
    }
}
