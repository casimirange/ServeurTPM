package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Techniciens;

@org.springframework.stereotype.Repository
public interface TechnicienRepository extends Repository<Techniciens, Long> {

}
