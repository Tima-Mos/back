package com.example.project_2.controller;

import com.example.project_2.dao.AnimalRepository;
import com.example.project_2.entity.Animal;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/animals/")
@RestController
public class RestAnimals {
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public List<Animal> getAll(){
        return animalRepository.findAll();
    }

    @GetMapping("{id}")
    public Animal getById(@PathVariable("id") UUID id){
        return animalRepository.findById(id).get();
    }
    @PutMapping
    public Animal update(@RequestBody Animal animal){
        if(animalRepository.existsById(animal.getId())){
            return animalRepository.save(animal);
        }
        throw new EntityExistsException("User with id:'"+ animal.getId() +"' doesn't exists");
    }
    @PostMapping
    public Animal create(@RequestBody Animal animal){
        UUID id = animal.getId();
        if(id !=null){
            if(animalRepository.existsById(animal.getId())){
                throw new EntityExistsException("User already exists");
            }
        }
        return animalRepository.save(animal);
    }
    @DeleteMapping("{id}")
    public String remove(@PathVariable("id") UUID id){
        animalRepository.findById(id).get().setForm(null);
        animalRepository.deleteById(id);
        return "OK";
    }
}
