package com.jsharper.dyndns.server.entities.cascade;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class CustomerCascade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customerCascade",cascade = CascadeType.PERSIST)
    private Set<PhoneCascade> phones;

    public CustomerCascade(String name) {
        this.name = name;
    }

    public CustomerCascade(String name, Set<PhoneCascade> phones) {
        this.name = name;
        this.phones = phones;
    }

    public CustomerCascade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PhoneCascade> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneCascade> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "CustomerCascade{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
