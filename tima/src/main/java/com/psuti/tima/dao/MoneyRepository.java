package com.psuti.tima.dao;
import com.psuti.tima.entities.Money;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoneyRepository extends JpaRepository<Money, UUID> {
}
