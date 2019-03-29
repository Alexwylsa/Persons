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

//    @Autowired
//    private MailSender mailSender;
    @PostConstruct
    public void init(){
        if(userRepo.count()==0){
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

    public User addUser(User user)
            {
                Set<Role> roles = new HashSet<>();
                roles.add(Role.ADMIN);

                user.setPassword(encoder.encode(user.getPassword()));
                user.setRoles(roles);

//                Optional<User> userFromDb = userRepo.findByUsername(user.getUsername());
//
//                if (userFromDb != null) {
//                    return false;
//                }
//
//                user.setActive(true);
//                user.setRoles(Collections.singleton(Role.USER));
//                user.setActivationCode(UUID.randomUUID().toString());
//
//                userRepo.save(user);
//
//                if (!StringUtils.isEmpty(user.getEmail())) {
//                    String message = String.format(
//                            "Hello, %s! \n" +
//                                    "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
//                            user.getUsername(),
//                            user.getActivationCode()
//                    );
//
//                    mailSender.send(user.getEmail(), "Activation code", message);
//                }
//
//                return true;
//    }
//
//    public boolean activateUser(String code) {
//        User user = userRepo.findByActivationCode(code);
//
//        if (user == null) {
//            return false;
//        }
//
//        user.setActivationCode(null);
//
//        userRepo.save(user);
//
//        return true;
                return userRepo.save(user);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

//    public String userEditForm(Model model) {
//        model.addAttribute("user", user);
//        model.addAttribute("roles", Role.values());
//
//        return "userEdit";
//    }



}
