package com.psuti.Moiseev.dao;

import com.psuti.Moiseev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
