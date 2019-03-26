package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.exeptions.NotFoundException;
import com.alexwylsa.Persons.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;


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


        return userRepo.save(user);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }


//    public User uploadUser(Long id, MultipartFile file) {
//
//        if (file == null) {
//            throw new EmptyFileException();
//        }
//
//
//        File uploadDir = new File(uploadPath);
//
//        if (!uploadDir.exists()) { //if not found
//            uploadDir.mkdir(); //then create directory
//        }
//
//        String uuidFile = UUID.randomUUID().toString();
//        String resultFilename = uuidFile + "_" + file.getOriginalFilename();
//
//
//        try {
//            file.transferTo(new File(uploadPath + "/" + resultFilename));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        userRepo.findById(id);
//        Optional<User>user;
//        return resultFilename;
//
//    }
}
