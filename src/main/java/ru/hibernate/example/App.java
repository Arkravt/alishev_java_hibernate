package ru.hibernate.example;

import org.hibernate.cfg.Configuration;
import org.hibernate.*;
import ru.hibernate.example.model.Person;

public class App {
    public static void main(String[] args) {

//        Person person = getPerson(1); // get person from DB

//        savePerson(new Person("Ivan", 21)); // save person to DB
//        savePerson(new Person("Artem", 36)); // save person to DB
//        savePerson(new Person("Katya", 43)); // save person to DB
//        savePerson(new Person("Tanya", 21)); // save person to DB
//        savePerson(new Person("Alexey", 30)); // save person to DB
//        savePerson(new Person("Sergey", 48)); // save person to DB
//        savePerson(new Person("Misha", 29)); // save person to DB
//        savePerson(new Person("Sveta", 34)); // save person to DB
//        savePerson(new Person("Zina", 44)); // save person to DB

//        updatePerson(2); // update person in DB

//        deletePerson(3); // delete person from DB

//        getPeopleHQL(); // HQL

    }

    public static Person getPerson(int id) {

        Person person;
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            person = session.get(Person.class, 1);
            System.out.println(person.getAge());
            System.out.println(person.getName());

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
        return person;

    }

    public static void savePerson(Person person) {

        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }

    public static void updatePerson(int id) {

        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, id);
            person.setName("person778");
            person.setAge(32);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    public static void deletePerson(int id) {

        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, id);
            session.delete(person);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    // HQL //
    public static void getPeopleHQL() {

        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

//            List<Person> people = session.createQuery("FROM Person", Person.class).getResultList();
//            List<Person> people = session.createQuery("FROM Person WHERE age > 30", Person.class).getResultList();
//            List<Person> people = session.createQuery("FROM Person WHERE name LIKE 'T%'", Person.class).getResultList();
//            session.createQuery("UPDATE Person SET name='TEST' WHERE age > 30", Person.class).executeUpdate();
            session.createQuery("DELETE FROM Person WHERE age > 30", Person.class).executeUpdate();

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
