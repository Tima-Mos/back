package com.psuti.tima.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "names")
public class MoneyName implements Serializable {
    @Id
    @Column(name = "name", nullable = false)
    private String name;
}
