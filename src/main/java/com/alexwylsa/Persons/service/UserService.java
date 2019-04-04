package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        if(userRepo.count()==0) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ADMIN);
            userRepo.save(new User(null, "admin", encoder.encode("123"), true, roles));
        }
    }
    public List<User> qetAllUser() {
        return userRepo.findAll();
    }

    public User getOneUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public User addUser(User user) {
                Set<Role> roles = new HashSet<>();
                roles.add(Role.USER);

                user.setPassword(encoder.encode(user.getPassword()));
                user.setRoles(roles);
                user.setActive(true);

                return userRepo.save(user);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }
}
