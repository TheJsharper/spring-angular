package services;

import dbs.TentantBean;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    // private static  List<User> users;
    @Autowired
    @Qualifier("bar")
    private TentantBean tentantBean;

    public UserService() {
        //  this.users = getSeedUsers();
    }

    public List<User> getAll() {
        return this.tentantBean.getUsers();
    }

    public User getUserById(Long id) {
        var result = this.tentantBean.getUsers().stream().filter((u) -> u.getId().equals(id)).findFirst();
        return result.orElse(new User());
    }

    public User createUser(User user) {

        var nextId = this.tentantBean.getNextId();

        var newUser = new User(nextId, user.getFirstName(), user.getLastName());

        var list = this.tentantBean.getUsers().stream();

        this.tentantBean.setUsers(Stream.concat(list, Stream.of(newUser)).toList());

        return newUser;
    }

    public User modifyUser(Long id, User user) {
        //final List<User> list = this.tentantBean.getUsers();
        if (id.equals(user.getId()) && this.tentantBean.getUsers().stream().anyMatch((u) -> u.getId().equals(id))) {

            var firstUser = this.tentantBean.getUsers().stream().filter((uu) -> uu.getId().equals(id)).findFirst();

            firstUser.ifPresent((result) -> {

                var updateList = this.tentantBean.getUsers().stream().map((u) -> {
                    if (u.getId().equals(result.getId())) {
                        return new User(user.getId(), user.getFirstName(), user.getLastName());
                    } else {
                        return u;
                    }
                }).toList();
                this.tentantBean.setUsers(updateList);
            });
            //noinspection OptionalGetWithoutIsPresent
            return this.tentantBean.getUsers().stream().filter(u -> u.getId().equals(id)).toList().get(0);
        } else {
            return user;
        }
    }

    public User deleteUser(Long id) {

        var result = this.tentantBean.getUsers().stream().filter(user -> user.getId().equals(id)).findFirst();
       // return this.tentantBean.getUsers().removeIf((u) -> u.getId().equals(id)) ? result.get() : null;

        var ignoredList = this.tentantBean
                .getUsers().stream().filter((u)-> !u.getId().equals(id)).toList();
        this.tentantBean.setUsers(ignoredList);
        return result.orElse( new User());
    }

    private static List<User> getSeedUsers() {
        return new ArrayList<>(Arrays.asList(
                new User(getNextId(), "Web-User FirstName  I", "Web_User- LastName I"),
                new User(getNextId(), "Web-User FirstName  II", "Web_User- LastName II"),
                new User(getNextId(), "Web-User FirstName", "Web_User- LastName"),
                new User(getNextId(), "Web-User FirstName ", "Web_User- LastName"),
                new User(getNextId(), "Web-User FirstName", "Web_User- LastName")
        ));
    }

    private static Long getNextId() {
        System.out.println("CAAALLLLED CONSTRUCTTOR SERVICES");
        var random = new Random();
        long leftLimit = 1L;
        long rightLimit = 1000000000000000000L;
        return random.nextLong(leftLimit, rightLimit);
    }
}
