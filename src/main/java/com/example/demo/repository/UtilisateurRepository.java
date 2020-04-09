package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Utilisateurs;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateurs, Long> {

	
	//pour définir les méthodes
	
	Utilisateurs findByUsername(String username);
	
	Utilisateurs findByMatricule(int matricule);
}
