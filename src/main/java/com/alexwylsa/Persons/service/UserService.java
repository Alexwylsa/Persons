package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.domain.UserInDto;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.UserRepo;
import com.alexwylsa.Persons.validators.UserValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Log4j2
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordEncoder encoder;
    @Value("${upload.path}")
    private String storagePath;

    @PostConstruct
    public void init() {
        if(userRepo.count()==0) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ADMIN);
            userRepo.save(new User(null, "admin", encoder.encode("123"), true, roles));
        }
    }

    public List<User> qetAllUser(Optional<String> username, Integer page, Integer size, String byColumn,
                                 Integer ascending) {
        log.debug("qetAllUser: username = {}, page = {}, size = {}, byColumn = {}, ascending = {}", username,
                page, size, byColumn, ascending);
        PageRequest pagination = userValidator.validatePagingAndThrowAndReturn(page, size, byColumn, ascending);
        return username.map(u->userRepo.findAllByUsernameContainingIgnoreCase(u, pagination).getContent())
                .orElseGet(()->userRepo.findAll(pagination).getContent());
    }

    public Long getUsersCount(Optional<String> username) {
        log.debug("getUsersCount: username = {}", username);
        return username.map(s -> userRepo.countByUsernameContains(s))
                .orElseGet(() -> userRepo.count());
    }

    public User getOneUser(Long id) {
        log.debug("getOneUser: id = {} ", id);
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public User addUser(UserInDto userInData) {
        log.debug("addUser: userInData = {} ", userInData);
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setPassword(encoder.encode(userInData.getPassword()));
        user.setUsername(userInData.getUsername());
        user.setRoles(roles);
        user.setActive(true);
        return userRepo.save(user);
    }

    public User updateUser(Long id, UserInDto userInData) {
        log.debug("addUser: id = {}, userInData = {} ", id, userInData);
       User userFromDb  = userRepo.findById(id).orElseThrow(()->new NotFoundException());
       userFromDb.setUsername(userInData.getUsername());
       userFromDb.setPassword(encoder.encode(userInData.getPassword()));
       return userRepo.save(userFromDb);
    }

    public void deleteUser(Long id, User user) {
        log.debug("deleteUser: id = {}, user = {}", id, user);
        //userValidator.validateDeleteUser(id, user);
        userRepo.delete(user);
    }

    public List<Role> getAllUserRoles() {
        log.debug("getAllUserRoles: ");
        return new ArrayList<>(EnumSet.allOf(Role.class));
    }
}
