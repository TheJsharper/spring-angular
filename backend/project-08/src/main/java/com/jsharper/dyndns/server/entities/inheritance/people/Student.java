package com.jsharper.dyndns.server.entities.inheritance.people;

import com.jsharper.dyndns.server.entities.inheritance.Person;
import jakarta.persistence.Entity;

@Entity
public class Student extends Person {

    private double score;


    public Student(double score, String firstName, String lastName) {
        this.score = score;
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Student() {
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "score=" + score +
                "} " + super.toString();
    }
}
