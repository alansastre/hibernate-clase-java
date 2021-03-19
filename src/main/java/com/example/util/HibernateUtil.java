package com.example.util;

import com.example.model.*;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;


/**
 * Multitenancy
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory(){

        if (sessionFactory == null){

            Configuration settings = new Configuration();

            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3300/hibernatedb");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "admin");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.FORMAT_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "create");
            properties.put(Environment.HBM2DDL_IMPORT_FILES, "data.sql");

            settings.setProperties(properties);

            // añadir entidades
            settings.addAnnotatedClass(Company.class);
            settings.addAnnotatedClass(CreditCard.class);
            settings.addAnnotatedClass(Department.class);
            settings.addAnnotatedClass(Direction.class);
            settings.addAnnotatedClass(Employee.class);
            settings.addAnnotatedClass(Project.class);
            settings.addAnnotatedClass(ProjectType.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(settings.getProperties()).build();

            sessionFactory = settings.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}
