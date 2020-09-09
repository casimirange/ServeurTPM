/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repository;

import com.example.demo.entity.Pannes;
import com.example.demo.reponses.CountPannesResponse;
import com.example.demo.reponses.DashboardResponse;
import com.example.demo.reponses.StatsRepose;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Casimir
 */
public interface DashboardRepository extends JpaRepository<Pannes, Long> {
    
    String countAllDepPanne = "SELECT DISTINCT new com.example.demo.reponses.DashboardResponse("
            + "m.nom as machine, m.code, m.idMachine, count(DISTINCT p.numero) as nbre, "
            + "sum(p.DT) as DT, "            
//            + "sum(TIME_TO_SEC(TIMEDIFF(p.debut_inter, p.heure_arret))/60) as WT, "
//            + "sum(TIME_TO_SEC(TIMEDIFF(p.fin_inter, p.debut_inter))/60) as TTR, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret as ph, p.debut_inter, p.fin_inter as pf, p.etat, "
//            + "sum(TO_SECONDS(p.fin_inter) - TO_SECONDS(p.heure_arret))/60 as DT, "
            + "d.nom as dep, d.idDepartement) "
            + "FROM Pannes p JOIN p.machines m "
            + "JOIN m.lignes l "
            + "JOIN l.departement d "
            + "where p.date between ?1 and ?2 "
            + "GROUP by p.date "
            + "order by p.date asc";
  
    @Query(value=countAllDepPanne)
    public List<DashboardResponse> countPerDay(LocalDate date, LocalDate date2);
    
    String countAll = "SELECT "
            + "count(DISTINCT p.numero) as nbre, d.nom as dep, d.id_departement, "
            + "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT "
            + "FROM pannes p "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "JOIN lignes l on l.id_ligne = m.id_ligne "
            + "JOIN departement d on d.id_departement = l.id_departement "
            + "where DATE_FORMAT(p.date, '%Y/%m') = ?1 "
//            + "and p.dt > 15 "
//            + "and m.label = 'correctif' "
            + "GROUP by d.nom "
            + "order by nbre desc";
  
    @Query( value=countAll, nativeQuery = true)
    public List<JSONObject> TotalLPannes(String date);
    
    String countThisYear = "SELECT count(DISTINCT p.numero) as nbre "
            + "FROM Pannes p "
            + "where DATE_FORMAT(p.date, '%Y') = ?1 ";
  
    @Query( value=countThisYear, nativeQuery = true)
    public List<JSONObject> countThisYear(String date);
    
    String countLast30Day = "SELECT date, count(distinct numero)as nbre, "
            + "COALESCE(sum(distinct timestampdiff(Minute, heure_arret, fin_inter)),0) as dt "
            + "FROM Pannes where date between ?1 and ?2  GROUP by date, numero order by date asc";

  
    @Query(value=countLast30Day, nativeQuery = true)
    public List<JSONObject> test(LocalDate date, LocalDate date2);
    
    String countMonthPanne = "SELECT date, count(distinct numero)as nbre, "
            + "COALESCE(sum(distinct timestampdiff(Minute, heure_arret, fin_inter)),0) as dt "
            + "FROM Pannes where DATE_FORMAT(date, '%Y/%m') = ?1 GROUP by date, numero order by date asc";

  
    @Query(value=countMonthPanne, nativeQuery = true)
    public List<JSONObject> countMonthPanne(String date);
    
    
    String mdtyear = "SELECT date_format(p.date, '%Y') as date, "
            + "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, "
            + "AVG(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as MDT, "
            + "AVG(DISTINCT timestampdiff(minute, p.heure_arret, p.debut_inter)) as WT, "
            + "AVG(DISTINCT timestampdiff(minute, p.debut_inter, p.fin_inter)) as MTTR, "
            + "COUNT(DISTINCT p.numero) as nbre  "
            + "FROM pannes p GROUP BY date_format(p.date, '%Y'), p.numero order by p.date asc limit 1,3";
    
    @Query(value = mdtyear, nativeQuery = true)
    public List<JSONObject> mdtByYear();
    
    String mdtthisyear = "SELECT date_format(p.date, '%b') as date, "
            + "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, "
            + "AVG(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as MDT, "
            + "AVG(DISTINCT timestampdiff(minute, p.heure_arret, p.debut_inter)) as WT, "
            + "AVG(DISTINCT timestampdiff(minute, p.debut_inter, p.fin_inter)) as MTTR, "
            + "COUNT(DISTINCT p.numero) as nbre  "
            + "FROM pannes p "
            + "WHERE date_format(p.date, '%Y') = ?1 "
            + "GROUP BY date_format(p.date, '%M') order by p.date asc";
    
    @Query(value = mdtthisyear, nativeQuery = true)
    public List<JSONObject> mdtThisYear(String date);
    
    String hourthisyear = "SELECT date_format(h.date, '%b') as date, "
            + "sum(h.heure) as HT "
            + "FROM heures h WHERE date_format(h.date, '%Y') = ?1  "
            + "GROUP by date_format(h.date, '%M%Y') order by h.date asc";
    
    @Query(value = hourthisyear, nativeQuery = true)
    public List<JSONObject> hourThisYear(String date);
    
    String hourByYear = "SELECT date_format(h.date, '%Y') as date, "
            + "sum(h.heure) as HT "
            + "FROM heures h "
            + "GROUP by date_format(h.date, '%Y') order by h.date asc";
    
    @Query(value = hourByYear, nativeQuery = true)
    public List<JSONObject> hourByYear();
    
    String arretthisyear = "SELECT date_format(a.date, '%b') as date, "
            + "sum(timestampdiff(minute, a.debut_arret, a.fin_arret)) as AT "
            + "FROM arrets a WHERE date_format(a.date, '%Y') = ?1  "
            + "GROUP by date_format(a.date, '%M%Y') order by a.date asc";
    
    @Query(value = arretthisyear, nativeQuery = true)
    public List<JSONObject> arretThisYear(String date);
    
    String arretByYear = "SELECT date_format(a.date, '%Y') as date, "
            + "sum(timestampdiff(minute, a.debut_arret, a.fin_arret)) as AT "
            + "FROM arrets a "
            + "GROUP by date_format(a.date, '%Y') order by a.date asc";
    
    @Query(value = arretByYear, nativeQuery = true)
    public List<JSONObject> arretByYear();
    
    
    
    ////////////je me suis faché
    String pByYear = "SELECT date_format(h.date, '%Y') as date,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT, \n"+
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.debut_inter)), 0) as WT, \n"+
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.debut_inter, p.fin_inter)), 0) as TTR, \n"
            + "count(DISTINCT p.numero) as nbre " +
            "from  heures h LEFT OUTER JOIN pannes p on (date_format(p.date, '%Y') = date_format(h.date, '%Y')) \n" +
            "GROUP by date_format(h.date, '%Y'), p.numero";
    
    @Query(value = pByYear, nativeQuery = true)
    public List<JSONObject> PByYear();
    
    String aByYear = "SELECT date_format(h.date, '%Y') as date,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
            "\n" +
            "from  heures h LEFT OUTER JOIN arrets a on (date_format(a.date, '%Y') = date_format(h.date, '%Y')) \n" +
            "GROUP by date_format(h.date, '%Y')";
    
    @Query(value = aByYear, nativeQuery = true)
    public List<JSONObject> AByYear();
    
    String aThisYear = "SELECT date_format(h.date, '%b') as date, "
            + "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT " +
"            from  heures h LEFT OUTER JOIN arrets a on (date_format(a.date, '%b%Y') = date_format(h.date, '%b%Y')) " +
"            WHERE date_format(h.date, '%Y') = ?1 " +
"            GROUP by date_format(h.date, '%b%Y') ORDER BY h.date";
    
    @Query(value = aThisYear, nativeQuery = true)
    public List<JSONObject> AThisYear(String date);
    
    String pThisYear = "SELECT date_format(h.date, '%b') as date, " +
"            COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT, " +
"            COALESCE(sum(DISTINCT timestampdiff(minute, p.debut_inter, p.fin_inter)), 0) as TTR, " +
"            COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.debut_inter)), 0) as WT, " +
"            COUNT(DISTINCT p.numero) as nbre " +
"            from  heures h LEFT OUTER JOIN pannes p on (date_format(p.date, '%b%Y') = date_format(h.date, '%b%Y'))  " +
"            WHERE date_format(h.date, '%Y') = ?1 " +
"            GROUP by date_format(h.date, '%b%Y'), p.numero ORDER BY h.date";
    
    @Query(value = pThisYear, nativeQuery = true)
    public List<JSONObject> PThisYear(String date);
    
    String techStats = "SELECT date_format(p.date, '%b %Y') as date, t.nom as tec, t.matricule, t.fonction,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT \n" +
        "FROM pannes p\n" +
        "join techniciens t on t.id_technicien = p.id_technicien\n" +
        "WHERE date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP BY t.nom, p.numero order by coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) desc";
  
    @Query( value=techStats, nativeQuery = true)
    public List<JSONObject> TechniciensStats(String date);
    
    String techStatsrange = "SELECT date_format(p.date, '%b %Y') as date, t.nom as tec, t.matricule, t.fonction,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT \n" +
        "FROM pannes p\n" +
        "join techniciens t on t.id_technicien = p.id_technicien\n" +
        "WHERE p.date between ?1 and ?2\n" +
        "GROUP BY t.nom, p.numero order by coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) desc";
  
    @Query( value=techStatsrange, nativeQuery = true)
    public List<JSONObject> TechniciensStatsRange(LocalDate date1, LocalDate date2);
    
}
