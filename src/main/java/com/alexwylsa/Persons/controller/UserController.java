package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(@RequestParam(required = false) Optional<String> username, @RequestParam Integer page, @RequestParam Integer size) {
        return userService.qetAllUser(username, page, size);
    }

    @GetMapping ("{id}")
        public User getOneUser(@PathVariable Long id) {
        return userService.getOneUser(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, User user) {
        userService.deleteUser(user);
    }



}
