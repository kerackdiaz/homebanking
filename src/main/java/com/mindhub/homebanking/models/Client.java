package com.mindhub.homebanking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
    private String firstName;
    private String lastName;

    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    public Client() { }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //    public Client(String first, String last, String email) {
//        firstName = first;
//        lastName = last;
//        email = email;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{"+"id= "+ id +"Name= "+ firstName + ' ' + lastName + "\n" +
                ", email='" + email + '\'' +
                '}';
    }
}
