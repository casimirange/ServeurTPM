package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Operateurs;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface OperateurRepository extends JpaRepository<Operateurs, Long>{
    
    String operateurs = "SELECT o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, o.id_operateur as idOP "
            + "FROM operateurs o "
            + "order by o.nom";
    
    @Query(value = operateurs, nativeQuery = true)
    List<JSONObject> operateurs();
    
}
