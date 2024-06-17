package ru.hibernate.example;

import org.hibernate.cfg.Configuration;
import org.hibernate.*;
import ru.hibernate.example.model.Person;

public class App {
    public static void main(String[] args) {

        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println(person.getAge());
            System.out.println(person.getName());

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
