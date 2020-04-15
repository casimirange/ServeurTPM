package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Lignes;
import com.example.demo.reponses.LignesReponse;



@Repository
public interface LigneRepository extends JpaRepository<Lignes, Long> {
	
	//String quer = "SELECT new com.example.demo.reponses.LignesReponse(nom) FROM lignes as l INNER JOIN departement as d on l.idDepartement = d.idDepartement";
	
	String quer1 = "SELECT new com.example.demo.reponses.LignesReponse(l.nomLigne , d.nom) FROM Departement d JOIN d.lignes l";
  
        //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
	@Query( value=quer1)
	public List<LignesReponse> ToutesLesLignes();
}
