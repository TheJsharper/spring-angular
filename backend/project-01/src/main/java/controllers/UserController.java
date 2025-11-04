package controllers;

import models.User;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User modifyUser(@PathVariable Long id, @RequestBody User user) {
        return this.userService.modifyUser(id, user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id) {
        return this.userService.deleteUser(id);
    }
}
