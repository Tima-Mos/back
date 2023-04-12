package com.example.lr_3.controller;

import com.example.lr_3.dao.UserRepository;
import com.example.lr_3.entity.User;
import com.example.lr_3.service.UserMeService;
import com.example.lr_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService, UserRepository userRepository, UserMeService userMeService){
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") UUID id){
        return userService.getById(id);
    }
    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable("id") UUID id){
            return userService.update(user, id);
    }
    @PostMapping
    public User create(@RequestBody User user){
            return userService.create(user);
    }
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") UUID id){
            userService.removeById(id);
    }
}
