package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Lignes;
import com.example.demo.reponses.LignesReponse;
import net.minidev.json.JSONObject;



@Repository
public interface LigneRepository extends JpaRepository<Lignes, Long> {
	
	//String quer = "SELECT new com.example.demo.reponses.LignesReponse(nom) FROM lignes as l INNER JOIN departement as d on l.idDepartement = d.idDepartement";
	
	String quer1 = "SELECT l.nom_ligne as nomLigne , d.nom as nomDep, d.id_departement as idDepartement"
                + ", l.id_ligne as idLigne FROM departement d "
                + "JOIN lignes l on d.id_departement = l.id_departement order by l.nom_ligne ";
  
        //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
	@Query( value=quer1, nativeQuery = true)
	public List<JSONObject> ToutesLesLignes();
}
