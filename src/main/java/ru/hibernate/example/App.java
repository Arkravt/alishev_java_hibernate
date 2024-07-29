package ru.hibernate.example;

import org.hibernate.cfg.Configuration;
import org.hibernate.*;
import ru.hibernate.example.model.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {

//         One To many
//        simpleExample_OneToMany();
//        advancedExample_OneToMany();

//        One to One
//        example_OneToOne();

//        Many to Many
        example_ManyToMany();

    }


    public static void simpleExample_OneToMany() {

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

    public static void advancedExample_OneToMany() {

//        getItemsByPersonId(1);
//        getPersonByItemId(3);
//        saveNewItem(4);
//        saveNewItemAndNewPerson();
//        deleteItemByPersonId(1);
//        deletePersonById(6);
//        setNewOwnerForItem(4, 7);
    }

    public static void example_OneToOne() {

//        addNewPersonAndNewPassport();
//        getPassportByPersonId(16);
//        getPersonByPassportOwner(16);
//        changePassportNumberByPersonId(16);
//        deletePerson(16);
    }

    public static void example_ManyToMany() {

//        addNewMovieAndActor();
//        getActorsByMovie();
//        addNewMovieForActor();
        deleteMovieFromPerson();
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

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class)
                .addAnnotatedClass(Item.class);

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


    public static void addNewPersonAndNewPassport() {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = new Person("test", 27);
            Passport passport = new Passport(234);
            person.setPassport(passport);

            session.persist(person);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    public static void getPassportByPersonId(int id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, id);

            System.out.println(person.getPassport().getPassportNumber());

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    public static void getPersonByPassportOwner(int id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Passport passport = session.get(Passport.class, id);
            System.out.println(passport.getPerson().getName());

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    public static void changePassportNumberByPersonId(int id) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, id);
            person.getPassport().setPassportNumber(99999);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }


    public static void addNewMovieAndActor() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Movie.class).addAnnotatedClass(Actor.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

//        try with resources
        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Movie movie = new Movie("The best movie", 2010);
            Actor actor1 = new Actor("Tom", 33);
            Actor actor2 = new Actor("Poly", 28);

            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));

            actor1.setMovies(new ArrayList<>(Arrays.asList(movie)));
            actor2.setMovies(new ArrayList<>(Arrays.asList(movie)));

            session.save(movie);

            session.save(actor1);
            session.save(actor2);

            session.getTransaction().commit();
        }
    }

    public static void getActorsByMovie() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Movie movie = session.get(Movie.class, 1);
            List<Actor> actors = movie.getActors();

            actors.forEach(e -> System.out.println(e.getName()));

            session.getTransaction().commit();
        }
    }

    public static void addNewMovieForActor() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Actor actor = session.get(Actor.class, 2);
            Movie newMovie = new Movie("Gone", 2021);

            if (newMovie.getActors() == null) {
                newMovie.setActors(Arrays.asList(actor));
            } else {
                newMovie.getActors().add(actor);
            }

            actor.getMovies().add(newMovie);

            session.save(newMovie);

            session.getTransaction().commit();
        }
    }

    public static void deleteMovieFromPerson() {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Actor actor = session.get(Actor.class, 2);
            Movie movie = actor.getMovies().get(0);

            actor.getMovies().remove(0);
            movie.getActors().remove(actor);

            session.getTransaction().commit();
        }
    }
}
