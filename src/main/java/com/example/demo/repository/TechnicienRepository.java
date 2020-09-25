package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Techniciens;
import com.example.demo.reponses.LignesReponse;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface TechnicienRepository extends JpaRepository<Techniciens, Long> {
    
    Techniciens findByMatricule(Long matricule);
    
    String active = "SELECT etat, fonction, matricule, nom, prenom, id_technicien as idTechnicien FROM Techniciens where etat = 1 and localisation = 'bonaberi' order by nom asc";
    
    String desactive = "SELECT etat, fonction, matricule, nom, prenom, id_technicien as idTechnicien FROM Techniciens where etat = 0 and localisation = 'bonaberi' order by nom asc";
    
    String all = "SELECT etat, fonction, matricule, nom, prenom, id_technicien as idTechnicien FROM Techniciens where localisation = 'bonaberi' order by nom asc";
  
    @Query(nativeQuery = true, value=active)
    public List<JSONObject> ActivatedTech();
    
    @Query(nativeQuery = true, value=desactive)
    public List<JSONObject> DesactivatedTech();
    
    @Query(nativeQuery = true, value=all)
    public List<JSONObject> AllTech();

}
