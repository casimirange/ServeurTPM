package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Lignes;



@Repository
public interface LigneRepository extends JpaRepository<Lignes, Long> {
	
	String quer = "SELECT * from lignes as l INNER JOIN departement as d on l.id_departement = d.id_departement";
	
	String quer1 = "SELECT * from lignes";
  
	@Query( nativeQuery = true, value=quer)
	List<Lignes> ToutesLesLignes();
}
