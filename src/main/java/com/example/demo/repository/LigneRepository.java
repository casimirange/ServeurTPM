package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Lignes;

@org.springframework.stereotype.Repository
public interface LigneRepository extends Repository<Lignes, Long> {

}
