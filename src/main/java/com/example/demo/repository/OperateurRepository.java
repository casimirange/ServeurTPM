package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Operateurs;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface OperateurRepository extends JpaRepository<Operateurs, Long>{
    
    Operateurs findByMatricule(Long matricule);
    
    String operateurs = "SELECT etat, nom as nomOP, prenom as prenomOP, matricule as matOP, id_operateur as idOP "
            + "FROM operateurs where localisation = 'bonaberi' "
            + "order by nom asc";
    
    @Query(value = operateurs, nativeQuery = true)
    List<JSONObject> operateurs();
    
    String active = "SELECT etat, nom as nomOP, prenom as prenomOP, matricule as matOP, id_operateur as idOP FROM operateurs where etat = 1 and localisation = 'bonaberi' order by nom asc";
    
    String desactive = "SELECT etat, nom as nomOP, prenom as prenomOP, matricule as matOP, id_operateur as idOP FROM operateurs where etat = 0 and localisation = 'bonaberi' order by nom asc";
      
    @Query(nativeQuery = true, value=active)
    public List<JSONObject> ActivatedOp();
    
    @Query(nativeQuery = true, value=desactive)
    public List<JSONObject> DesactivatedOp();
    
}
