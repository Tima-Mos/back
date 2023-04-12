package com.psuti.tima.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "money")
public class Money implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "money_name")
    private MoneyName name;
    @Column(nullable = false, length = 25,name = "quantity")
    private int quantity;
    @Column(nullable = false, length = 25, name = "value")
    private int value;
    @Column(nullable = false, length = 25, name = "price")
    private int price;
    @Column(nullable = false, length = 25, name = "date")
    private String date;


}

