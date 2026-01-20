package com.jsharper.dyndns.server.entities.fetch;

import jakarta.persistence.*;

import java.util.Set;
import java.util.StringJoiner;

@Entity
public class ProjectFetch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<ProgrammerFetch> programmerFetches;

    public ProjectFetch(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectFetch(String name) {
        this.name = name;
    }

    public ProjectFetch() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProgrammerFetch> getProgrammers() {
        return programmerFetches;
    }

    public void setProgrammers(Set<ProgrammerFetch> programmerFetches) {
        this.programmerFetches = programmerFetches;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProjectFetch.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
