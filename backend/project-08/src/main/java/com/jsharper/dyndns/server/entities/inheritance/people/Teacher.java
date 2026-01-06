package com.jsharper.dyndns.server.entities.inheritance.people;

import com.jsharper.dyndns.server.entities.inheritance.Person;
import jakarta.persistence.Entity;

@Entity
public class Teacher extends Person {
    private String subject;

    public Teacher(String subject, String firstName, String lastName) {
        this.subject = subject;
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Teacher() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "subject='" + subject + '\'' +
                "} " + super.toString();
    }
}
