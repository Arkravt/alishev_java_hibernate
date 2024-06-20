package ru.hibernate.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Item> items;

}
