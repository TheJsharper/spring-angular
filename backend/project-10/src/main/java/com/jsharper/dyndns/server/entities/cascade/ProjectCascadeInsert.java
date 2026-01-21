package com.jsharper.dyndns.server.entities.cascade;

import jakarta.persistence.*;

import java.util.Set;
import java.util.StringJoiner;

@Entity
public class ProjectCascadeInsert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "projectCascadeInserts")
    private Set<ProgrammerCascadeInsert> programmerCascadeInsert;

    public ProjectCascadeInsert(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectCascadeInsert(String name) {
        this.name = name;
    }

    public ProjectCascadeInsert(Long id, String name, Set<ProgrammerCascadeInsert> programmerCascadeInsert) {
        this.id = id;
        this.name = name;
        this.programmerCascadeInsert = programmerCascadeInsert;
    }

    public ProjectCascadeInsert(String name, Set<ProgrammerCascadeInsert> programmerCascadeInsert) {
        this.name = name;
        this.programmerCascadeInsert = programmerCascadeInsert;
    }

    public ProjectCascadeInsert() {
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

    public Set<ProgrammerCascadeInsert> getProgrammerCascadeInsert() {
        return programmerCascadeInsert;
    }

    public void setProgrammerCascadeInsert(Set<ProgrammerCascadeInsert> programmerCascadeInsert) {
        this.programmerCascadeInsert = programmerCascadeInsert;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProjectCascadeInsert.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
