package com.jsharper.dyndns.server.entities.fetch;

import jakarta.persistence.*;

import java.util.Set;
import java.util.StringJoiner;

@Entity
public class ProgrammerFetch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(name = "salary")
    private int sal;

    public ProgrammerFetch(String firstName, String lastName, int sal) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sal = sal;
    }

    public ProgrammerFetch(Long id, String firstName, String lastName, int sal) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sal = sal;
    }

    public ProgrammerFetch(String firstName, String lastName, int sal, Set<ProjectFetch> projects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sal = sal;
        this.projects = projects;
    }

    public ProgrammerFetch() {
    }

    @ManyToMany(cascade = CascadeType.ALL)

    @JoinTable(name = "programmers_projects",
            joinColumns = {@JoinColumn(name = "programmer_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")}

    )


    private Set<ProjectFetch> projects;

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

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    public Set<ProjectFetch> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectFetch> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProgrammerFetch.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("sal=" + sal)
                .toString();
    }
}
