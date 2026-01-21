package com.jsharper.dyndns.server.entities.cascade;

import jakarta.persistence.*;

import java.util.Set;
import java.util.StringJoiner;

@Entity
public class ProgrammerCascadeInsert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "programmers_projects_cascade_insert",
            joinColumns = {@JoinColumn(name = "programmer_cascade_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_cascade_id", referencedColumnName = "id")}

    )
    private Set<ProjectCascadeInsert> projectCascadeInserts;

    public ProgrammerCascadeInsert(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ProgrammerCascadeInsert(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ProgrammerCascadeInsert(String firstName, String lastName, Set<ProjectCascadeInsert> projectCascadeInserts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.projectCascadeInserts = projectCascadeInserts;
    }

    public ProgrammerCascadeInsert() {
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

    public Set<ProjectCascadeInsert> getProjectCascadeInserts() {
        return projectCascadeInserts;
    }

    public void setProjectCascadeInserts(Set<ProjectCascadeInsert> projectCascadeInserts) {
        this.projectCascadeInserts = projectCascadeInserts;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProgrammerCascadeInsert.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .toString();
    }
}
