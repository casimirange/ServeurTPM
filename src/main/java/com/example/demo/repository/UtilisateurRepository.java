package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Utilisateurs;

@org.springframework.stereotype.Repository
public interface UtilisateurRepository extends Repository<Utilisateurs, Long> {

}
