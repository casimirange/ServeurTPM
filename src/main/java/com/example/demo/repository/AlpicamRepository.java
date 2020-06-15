/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repository;

import com.example.demo.entity.Pannes;
import com.example.demo.reponses.DashboardResponse;
import static com.example.demo.repository.DashboardRepository.countAllDepPanne;
import java.time.LocalDate;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Casimir
 */
@Repository
public interface AlpicamRepository extends JpaRepository<Pannes, Long>{
    
    String query = "SELECT distinct t.fonction, COUNT(DISTINCT p.numero) as nbre "
            + "FROM pannes p JOIN techniciens t on p.id_technicien = t.id_technicien "
            + "WHERE date_format(p.date, '%Y') = ?1 GROUP by t.fonction";
    
    @Query(value=query, nativeQuery = true)
    public List<JSONObject> typePanne(String date);
}
