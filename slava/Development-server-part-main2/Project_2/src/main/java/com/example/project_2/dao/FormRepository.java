package com.example.project_2.dao;

import com.example.project_2.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form,String> {
}
