package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Techniciens;

@Repository
public interface TechnicienRepository extends JpaRepository<Techniciens, Long> {

}
