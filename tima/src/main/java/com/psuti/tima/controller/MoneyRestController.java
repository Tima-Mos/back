package com.psuti.tima.controller;

import com.psuti.tima.dao.MoneyRepository;
import com.psuti.tima.entities.Money;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("/money")
@RestController
public class MoneyRestController {
    private final MoneyRepository moneyRepository;
    @Autowired
    public MoneyRestController(MoneyRepository userRepository){
        this.moneyRepository = userRepository;
    }
    @GetMapping
    public List<Money> getAll(){
        return moneyRepository.findAll();
    }
    @GetMapping("/{id}")
    public Money getById(@PathVariable("id") UUID id){
        return moneyRepository.findById(id).get();
    }
    @GetMapping("/past/{id}")
    public ResponseEntity<String> back(@PathVariable("id") UUID id){
        Money money = moneyRepository.findById(id).get();
        double finalPrice = money.getPrice();
        double finalValue = money.getValue();
        for (int i = 0; i<8; i++){
            finalPrice = (finalPrice/100)*95;
            finalValue = (finalValue/100)*95;
        }
        return ResponseEntity.ok().body("8 месяцев назад цена была равна " + finalValue+ " " + money.getName().getName() + ", а стоимость " + finalPrice+ " " + money.getName().getName() + ".\nв данный момент цена и стоимость равны:"+money.getValue() + " " + money.getName().getName()+" и "+money.getPrice() + " " + money.getName().getName()+".");
    }
    @GetMapping("/future/{id}")
    public ResponseEntity<String> future(@PathVariable("id") UUID id){
        Money money = moneyRepository.findById(id).get();
        double finalPrice = money.getPrice();
        double finalValue = money.getValue();
        for (int i = 0; i<20; i++){
            finalPrice = (finalPrice/100)*105;
            finalValue = (finalValue/100)*105;
        }
        return ResponseEntity.ok().body("через 20 месяцев цена будет " + finalValue+ " " + money.getName().getName()+ ", а стоимость " + finalPrice+ " " + money.getName().getName() + ".\nв данный момент цена и стоимость равны:"+money.getValue()+ " " + money.getName().getName() +" и "+money.getPrice()+ " " + money.getName().getName() + ".");
    }
    @PutMapping
    public Money update(@RequestBody Money money){
        if(moneyRepository.existsById(money.getId())){
            return moneyRepository.save(money);
        }
        throw new EntityExistsException("Money with id:'"+ money.getId() +"' doesn't exists");
    }
    @PostMapping
    public Money create(@RequestBody Money money){
        UUID id = money.getId();
        if(id !=null){
            if(moneyRepository.existsById(money.getId())){
                throw new EntityExistsException("Money already exists");
            }
        }
        Date date = new Date();
        money.setDate(date.toString());
        return moneyRepository.save(money);
    }
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") UUID id){
        moneyRepository.deleteById(id);
    }
}
