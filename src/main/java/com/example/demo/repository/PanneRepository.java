package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Pannes;

@Repository
public interface PanneRepository extends JpaRepository<Pannes, Long> {

}
