package dbs;

import models.User;
import org.springframework.beans.factory.config.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TentantBean {


    private List<User> users;


    private final String name;

    /*public TentantBean() {
    }*/

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public TentantBean(String name) {
        this.name = name;
        this.users = getSeedUsers();
    }

    private List<User> getSeedUsers() {
        return new ArrayList<>(Arrays.asList(
                new User(getNextId(), "Web-User FirstName  I", "Web_User- LastName I"),
                new User(getNextId(), "Web-User FirstName  II", "Web_User- LastName II"),
                new User(getNextId(), "Web-User FirstName", "Web_User- LastName"),
                new User(getNextId(), "Web-User FirstName ", "Web_User- LastName"),
                new User(getNextId(), "Web-User FirstName", "Web_User- LastName")
        ));

    }

    public Long getNextId() {
        System.out.println("CAAALLLLED CONSTRUCTTOR SERVICES");
        var random = new Random();
        long leftLimit = 1L;
        long rightLimit = 100000000000000L;
        return random.nextLong(leftLimit, rightLimit);
    }

    public void sayHello() {
        System.out.println(
                String.format("Hello from %s of type %s",
                        this.name,
                        this.getClass().getName()));
    }


}
