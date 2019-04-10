package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.domain.UserInDto;
import com.alexwylsa.Persons.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //get all users
    @GetMapping
    public List<User> getAllUser(@RequestParam(required = false) Optional<String> username,
                                 @RequestParam Integer page,
                                 @RequestParam Integer size,
                                 @RequestParam(value = "orderBy", required = false) String byColumn,
                                 @RequestParam Integer ascending) {
        log.info("getAllUser: username = {}, page = {}, size = {}, byColumn = {}, ascending = {}", username, page,
                size, byColumn, ascending);
        return userService.qetAllUser(username, page, size, byColumn, ascending);
    }
    //Count all users in DB
    @GetMapping("/count")
    public Long getUsersCount(@RequestParam(required = false) Optional<String> username) {
        log.info("getUsersCount: username = {}", username);
        return userService.getUsersCount(username);
    }
    //get one user
    @GetMapping ("{id}")
        public User getOneUser(@PathVariable Long id) {
        log.info("getOneUser: id = {} ", id);
        return userService.getOneUser(id);
    }
    //add new user
    @PostMapping()
    public User addUser(@RequestBody UserInDto user) {
        log.info("addUser: user = {} ", user);
        return userService.addUser(user);
    }
    //update user info
    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserInDto user) {
        log.info("updateUser: id = {}, user = {}", id, user);
        return userService.updateUser(id, user);
    }
    //delete user
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id, User user) {
        log.info("deleteUser: id = {}, user = {}", id, user);
        userService.deleteUser(id, user);
    }

    //get all roles
    @GetMapping("/roles")
    public List<Role> getAllUserRoles(@AuthenticationPrincipal User user) {
        log.info("getAllUserRoles: ");
        return userService.getAllUserRoles();
    }
}
