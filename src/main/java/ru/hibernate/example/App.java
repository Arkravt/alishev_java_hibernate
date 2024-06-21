package ru.hibernate.example;

import org.hibernate.cfg.Configuration;
import org.hibernate.*;
import ru.hibernate.example.model.Item;
import ru.hibernate.example.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {

        // simpleExample();

        advancedExample();

    }


    public static void simpleExample() {

        Person person = getPerson(1); // get person from DB

        savePerson(new Person("Ivan", 21)); // save person to DB
        savePerson(new Person("Artem", 36)); // save person to DB
        savePerson(new Person("Katya", 43)); // save person to DB
        savePerson(new Person("Tanya", 21)); // save person to DB
        savePerson(new Person("Alexey", 30)); // save person to DB
        savePerson(new Person("Sergey", 48)); // save person to DB
        savePerson(new Person("Misha", 29)); // save person to DB
        savePerson(new Person("Sveta", 34)); // save person to DB
        savePerson(new Person("Zina", 44)); // save person to DB

        updatePerson(2); // update person in DB

        deletePerson(3); // delete person from DB

        getPeopleHQL(); // HQL

    }

    public static void advancedExample() {

//        getItemsByPersonId(1);
//        getPersonByItemId(3);
//        saveNewItem(4);
//        saveNewItemAndNewPerson();
//        deleteItemByPersonId(1);
        //deletePersonById(6);
        setNewOwnerForItem(4, 7);
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


    public static void getItemsByPersonId(int id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, id);
            List<Item> items = person.getItems();

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void getPersonByItemId(int id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Item item = session.get(Item.class, id);
            Person person = item.getOwner();
            System.out.println(person.getName());

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void saveNewItem(int person_id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, person_id);
            Item newItem = new Item("Mercedes", person);
            person.getItems().add(newItem);

            session.save(newItem);

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void saveNewItemAndNewPerson() {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person newPerson = new Person("Jack", 44);
            Item newItem = new Item("Sega", newPerson);

            newPerson.setItems(new ArrayList<>(Collections.singleton(newItem)));

            session.save(newPerson);
            session.save(newItem);

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void deleteItemByPersonId(int person_id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, person_id);

            List<Item> items = person.getItems();

            // Порождает sql запрос
            for (Item item : items)
                session.remove(item);

            // Не порождает sql запрос, но необходимо для синхронизации кэша и базы данных
            person.getItems().clear();

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void deletePersonById(int person_id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, person_id);

            // Порождает sql запрос
            session.remove(person);

            // Не порождает sql запрос, но необходимо для синхронизации кэша и базы данных
            person.getItems().forEach(i -> i.setOwner(null));

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void setNewOwnerForItem(int newOwner_id, int item_id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, newOwner_id);
            Item item = session.get(Item.class, item_id);

            // Не порождает sql запрос, но необходимо для синхронизации кэша и базы данных
            person.getItems().add(item);
            item.getOwner().getItems().remove(item);

            // Порождает sql запрос
            item.setOwner(person);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

}
