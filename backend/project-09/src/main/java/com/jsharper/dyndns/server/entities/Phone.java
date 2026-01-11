package com.jsharper.dyndns.server.entities;

import jakarta.persistence.*;

import java.util.Set;
@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String number;
    private String type;
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public Phone( Long id, String number, String type, Customer c) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.customer = c;
    }

    public Phone() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", customer=" + customer +
                '}';
    }
}
