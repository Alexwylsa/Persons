package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.domain.UserInDto;
import com.alexwylsa.Persons.exceptions.*;
import com.alexwylsa.Persons.repo.UserRepo;
import com.alexwylsa.Persons.parameters.SortParameters;
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
    private SortParameters sortParameters;
    @Autowired
    private PasswordEncoder encoder;
    @Value("${upload.path}")
    private String storagePath;
    //if there is no user, then created a new one
    @PostConstruct
    public void init() {
        if(userRepo.count()==0) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ADMIN);
            userRepo.save(new User(null, "admin", encoder.encode("123"), true, roles));
        }
    }
    //get all departments with parameters
    public List<User> qetAllUser(Optional<String> username, Integer page, Integer size, String byColumn,
                                 Integer ascending) {
        log.debug("qetAllUser: username = {}, page = {}, size = {}, byColumn = {}, ascending = {}", username,
                page, size, byColumn, ascending);
        PageRequest pagination = sortParameters.parameters(page, size, byColumn, ascending);
        return username.map(u->userRepo.findAllByUsernameContainingIgnoreCase(u, pagination).getContent())
                .orElseGet(()->userRepo.findAll(pagination).getContent());
    }
    //count users
    public Long getUsersCount(Optional<String> username) {
        log.debug("getUsersCount: username = {}", username);
        return username.map(s -> userRepo.countByUsernameContains(s))
                .orElseGet(() -> userRepo.count());
    }
    //get one user
    public User getOneUser(Long id) {
        log.debug("getOneUser: id = {} ", id);
        return userRepo.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_FOUND));
    }
    //add new user
    public User addUser(UserInDto userInData) {
        log.debug("addUser: userInData = {} ", userInData);
        if (userRepo.existsByUsername(userInData.getUsername())) {
            throw new RestException(ErrorCodes.USER_ALREADY_EXIST);
        }
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setPassword(encoder.encode(userInData.getPassword()));
        user.setUsername(userInData.getUsername());
        user.setRoles(roles);
        user.setActive(true);
        return userRepo.save(user);
    }
    //update user
    public User updateUser(Long id, UserInDto userInData) {
        log.debug("addUser: id = {}, userInData = {} ", id, userInData);
       User userFromDb  = userRepo.findById(id)
               .orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_FOUND));
       userFromDb.setUsername(userInData.getUsername());
       userFromDb.setPassword(encoder.encode(userInData.getPassword()));
       return userRepo.save(userFromDb);
    }
    //delete user
    public void deleteUser(Long id, User user) {
        log.debug("deleteUser: id = {}, user = {}", id, user);
        sortParameters.deleteUserСheck(id, user);
        userRepo.deleteById(id);
    }
    //get all user roles
    public List<Role> getAllUserRoles() {
        log.debug("getAllUserRoles: ");
        return new ArrayList<>(EnumSet.allOf(Role.class));
    }
}
