package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Utilisateurs;
import com.example.demo.reponses.LignesReponse;
import com.example.demo.reponses.UserReponsse;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateurs, Long> {

	
	//pour définir les méthodes
	
	Utilisateurs findByUsername(String username);
	
	Utilisateurs findByMatricule(int matricule);
        
        String quer1 = "SELECT new com.example.demo.reponses.UserReponsse("
                + "username) FROM Utilisateurs ";
  
        //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
	@Query(value=quer1)
	public List<UserReponsse> ToutesLesUsers();
}
