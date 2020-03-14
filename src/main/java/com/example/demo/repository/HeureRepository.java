package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Heures;

@Repository
public interface HeureRepository extends JpaRepository<Heures, Long>{

}
