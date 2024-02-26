package org.example.util;

import org.example.converter.CustomBirthdayConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration conf = new Configuration();
        conf.configure();
        conf.addAttributeConverter(CustomBirthdayConverter.class, true);

        return conf.buildSessionFactory();
    }
}