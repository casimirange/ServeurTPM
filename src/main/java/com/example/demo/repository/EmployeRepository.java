package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employé;

@Repository
public interface EmployeRepository extends JpaRepository<Employé, Long> {

}
