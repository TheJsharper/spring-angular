package com.jsharper.dyndns.server.entities.fetch;

import jakarta.persistence.*;

import java.util.Set;
import java.util.StringJoiner;

@Entity
public class CustomerFetch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customerFetch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PhoneFetch> phones;

    public CustomerFetch(String name, Set<PhoneFetch> phones) {
        this.name = name;
        this.phones = phones;
    }

    public CustomerFetch(Long id, String name, Set<PhoneFetch> phones) {
        this.id = id;
        this.name = name;
        this.phones = phones;
    }

    public CustomerFetch(String name) {
        this.name = name;
    }

    public CustomerFetch() {
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

    public Set<PhoneFetch> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneFetch> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerFetch.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("id=" + id)
                .toString();
    }

}
