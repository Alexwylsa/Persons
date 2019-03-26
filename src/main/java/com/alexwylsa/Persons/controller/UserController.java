package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/users")//перенаправление на этот контроллер всех обращений, начинающихся со слова "staff"
public class UserController {
    @Autowired
    private UserService userService;




    @GetMapping
    public List<User> getAllUser()
    {
        return userService.qetAllUser();
    }

    @GetMapping ("{id}")
        public User getOneUser(@PathVariable Long id) //Открытие по "id"
    {
        return userService.getOneUser(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

//    @PutMapping("{id}/image")
//    public User uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        return userService.uploadUser(id, file);
//    }


    @PutMapping("{id}") //обновление записей
    public User updateUser(@PathVariable Long id, @RequestBody User user)//обновление записей
    {
        return userService.updateUser(user);

    }

    @DeleteMapping("{id}") //удаление записи
    public void delete(@PathVariable Long id, User user){
        userService.deleteUser(user);
    }










}
