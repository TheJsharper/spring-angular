package com.jsharper.dyndns.server.entities;

import com.jsharper.dyndns.server.entities.gen.CustomIdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EmployeeGenericGenerator {
    @Id
    @CustomIdGenerator(name = "THE_SEQUENCE_NAME")
    private Long id;

    private String firstName;

    private String lastName;

    public EmployeeGenericGenerator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public EmployeeGenericGenerator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "EmployeeGenericGenerator{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
