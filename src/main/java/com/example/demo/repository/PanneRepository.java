package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Pannes;

@org.springframework.stereotype.Repository
public interface PanneRepository extends Repository<Pannes, Long> {

}
