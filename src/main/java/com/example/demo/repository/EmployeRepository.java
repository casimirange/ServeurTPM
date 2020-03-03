package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Employé;

@org.springframework.stereotype.Repository
public interface EmployeRepository extends Repository<Employé, Long> {

}
