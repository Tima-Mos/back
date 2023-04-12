package com.psuti.Moiseev.controller;

import com.psuti.Moiseev.dao.UserRepository;
import com.psuti.Moiseev.entities.User;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserRestController {
    private final UserRepository userRepository;
    @Autowired
    public UserRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> getAll(){
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") UUID id){
        return userRepository.findById(id).get();
    }
    @PutMapping
    public User update(@RequestBody User user){
        if(userRepository.existsById(user.getId())){
            return userRepository.save(user);
        }
        throw new EntityExistsException("User with id:'"+ user.getId() +"' doesn't exists");
    }
    @PostMapping
    public User create(@RequestBody User user){
        UUID id = user.getId();
        if(id !=null){
            if(userRepository.existsById(user.getId())){
                throw new EntityExistsException("User already exists");
            }
        }
        return userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") UUID id){
        userRepository.deleteById(id);
    }
}
