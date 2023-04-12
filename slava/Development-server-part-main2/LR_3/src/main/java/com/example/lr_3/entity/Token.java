package com.example.lr_3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "token")
@Table(name = "tokens")
public class Token implements Serializable {
    @Column(nullable = false, unique = true)
    private String value;
    @JsonIgnore
    private boolean killed;
    @Id
    @Column(name = "user_id")
    private UUID userId;
}
