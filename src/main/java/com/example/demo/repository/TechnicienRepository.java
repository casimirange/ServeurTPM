package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Techniciens;
import com.example.demo.reponses.LignesReponse;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface TechnicienRepository extends JpaRepository<Techniciens, Long> {
    
    Techniciens findByMatricule(Long matricule);
    
    String active = "SELECT * FROM Techniciens where etat = 1 order by nom";
    
    String desactive = "SELECT * FROM Techniciens where etat = 0 order by nom";
  
    @Query(nativeQuery = true, value=active)
    public List<Techniciens> ActivatedTech();
    
    @Query(nativeQuery = true, value=desactive)
    public List<Techniciens> DesactivatedTech();

}
