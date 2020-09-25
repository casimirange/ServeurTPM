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
    
    String typePanneMonth = "SELECT distinct t.fonction, COUNT(DISTINCT p.numero) as nbre "
            + "FROM pannes p JOIN techniciens t on p.id_technicien = t.id_technicien "
            + "WHERE date_format(p.date, '%Y/%m') = ?1 GROUP by t.fonction";
    
    @Query(value=typePanneMonth, nativeQuery = true)
    public List<JSONObject> typePanneMonth(String date);
    
    String typePanneRange = "SELECT distinct t.fonction, COUNT(DISTINCT p.numero) as nbre "
            + "FROM pannes p JOIN techniciens t on p.id_technicien = t.id_technicien "
            + "WHERE p.date between ?1 and ?2 GROUP by t.fonction";
    
    @Query(value=typePanneRange, nativeQuery = true)
    public List<JSONObject> typePanneRange(LocalDate date, LocalDate date2);
    
    String alpi = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "WHERE p.date BETWEEN ?1 and ?2\n" +
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = alpi, nativeQuery = true)
    List<JSONObject> statsAlpi(LocalDate date, LocalDate date2);
    
    String placage = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "join departement d on l.id_departement = d.id_departement " +
        "WHERE d.nom = 'placage' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = placage, nativeQuery = true)
    List<JSONObject> statsPlacage(LocalDate date, LocalDate date2);
    
    String Brazil = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "join departement d on l.id_departement = d.id_departement " +
        "WHERE d.nom = 'brazil' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = Brazil, nativeQuery = true)
    List<JSONObject> statsBrazil(LocalDate date, LocalDate date2);
    
    String CP = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "join departement d on l.id_departement = d.id_departement " +
        "WHERE d.nom = 'contreplaqué' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
        
    @Query(value = CP, nativeQuery = true)
    List<JSONObject> statsCP(LocalDate date, LocalDate date2);
    
    String scierie = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "join departement d on l.id_departement = d.id_departement " +
        "WHERE d.nom = 'scierie' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = scierie, nativeQuery = true)
    List<JSONObject> statsScierie(LocalDate date, LocalDate date2);
    
    String ligne1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'ligne 1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = ligne1, nativeQuery = true)
    List<JSONObject> statsLigne1(LocalDate date, LocalDate date2);
    
    String ligne2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'ligne 2' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = ligne2, nativeQuery = true)
    List<JSONObject> statsLigne2(LocalDate date, LocalDate date2);
    
    String ligne3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'ligne 3' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = ligne3, nativeQuery = true)
    List<JSONObject> statsLigne3(LocalDate date, LocalDate date2);
    
    String sechoir = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'sechoirs' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sechoir, nativeQuery = true)
    List<JSONObject> statsSechoirs(LocalDate date, LocalDate date2);
    
    String jointage = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'jointage' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = jointage, nativeQuery = true)
    List<JSONObject> statsJointage(LocalDate date, LocalDate date2);
    
    String ecorçage = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'ecorcage' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = ecorçage, nativeQuery = true)
    List<JSONObject> statsEcorçage(LocalDate date, LocalDate date2);
    
    String tapisDéchets = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE m.nom like 'tapis%' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = tapisDéchets, nativeQuery = true)
    List<JSONObject> statsTapisDéchets(LocalDate date, LocalDate date2);
    
    String encollage = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'encollage Brazil' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = encollage, nativeQuery = true)
    List<JSONObject> statsEncollageBazil(LocalDate date, LocalDate date2);
    
    String tranchage = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "join lignes l on l.id_ligne = m.id_ligne " +
        "WHERE l.nom_ligne = 'tranchage' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = tranchage, nativeQuery = true)
    List<JSONObject> statsTranchage(LocalDate date, LocalDate date2);
    
    String D1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'derouleuse 1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = D1, nativeQuery = true)
    List<JSONObject> statsD1(LocalDate date, LocalDate date2);
    
    String D2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'derouleuse 2' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = D2, nativeQuery = true)
    List<JSONObject> statsD2(LocalDate date, LocalDate date2);
    
    String D3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'derouleuse 3' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = D3, nativeQuery = true)
    List<JSONObject> statsD3(LocalDate date, LocalDate date2);
    
    String B3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'bobineuse 3' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = B3, nativeQuery = true)
    List<JSONObject> statsB3(LocalDate date, LocalDate date2);
    
    String B2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'bobineuse 2' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = B2, nativeQuery = true)
    List<JSONObject> statsB2(LocalDate date, LocalDate date2);
    
    String B1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'bobineuse 1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = B1, nativeQuery = true)
    List<JSONObject> statsB1(LocalDate date, LocalDate date2);
    
    String mB1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'magasin bobines 1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mB1, nativeQuery = true)
    List<JSONObject> statsMB1(LocalDate date, LocalDate date2);
    
    String mB2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'magasin bobines 2' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mB2, nativeQuery = true)
    List<JSONObject> statsMB2(LocalDate date, LocalDate date2);
    
    String mB3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'magasin bobines 3' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mB3, nativeQuery = true)
    List<JSONObject> statsMB3(LocalDate date, LocalDate date2);
    
    String mEZ1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'massicot EZ1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mEZ1, nativeQuery = true)
    List<JSONObject> statsMEZ1(LocalDate date, LocalDate date2);
    
    String mEZ3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'massicot EZ3' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mEZ3, nativeQuery = true)
    List<JSONObject> statsMEZ3(LocalDate date, LocalDate date2);
    
    String mEZ4 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'massicot EZ4' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mEZ4, nativeQuery = true)
    List<JSONObject> statsMEZ4(LocalDate date, LocalDate date2);
    
    String mEN = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'massicot EN' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = mEN, nativeQuery = true)
    List<JSONObject> statsMassEN(LocalDate date, LocalDate date2);
    
    String massA = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'massicot A' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = massA, nativeQuery = true)
    List<JSONObject> statsMassA(LocalDate date, LocalDate date2);
    
    String massB = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'massicot B' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = massB, nativeQuery = true)
    List<JSONObject> statsMassB(LocalDate date, LocalDate date2);
    
    String sEZ1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'sechoir EZ1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sEZ1, nativeQuery = true)
    List<JSONObject> statsSeEZ1(LocalDate date, LocalDate date2);
    
    String sEZ2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'sechoir EZ2' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sEZ2, nativeQuery = true)
    List<JSONObject> statsSeEZ2(LocalDate date, LocalDate date2);
    
    String sEZ3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'sechoir EZ3' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sEZ3, nativeQuery = true)
    List<JSONObject> statsSeEZ3(LocalDate date, LocalDate date2);
    
    String sEZ4 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'sechoir EZ4' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sEZ4, nativeQuery = true)
    List<JSONObject> statsSeEZ4(LocalDate date, LocalDate date2);
    
    String sEN = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'sechoir EN' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sEN, nativeQuery = true)
    List<JSONObject> statsSeEN(LocalDate date, LocalDate date2);
    
    String sER24 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'sechoir ER24' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = sER24, nativeQuery = true)
    List<JSONObject> statsSeER24(LocalDate date, LocalDate date2);
    
    String en1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'encolleuse 1 brazil' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = en1, nativeQuery = true)
    List<JSONObject> statsEnc1b(LocalDate date, LocalDate date2);
    
    String en2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'encolleuse 2 brazil' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = en2, nativeQuery = true)
    List<JSONObject> statsEnc2b(LocalDate date, LocalDate date2);
    
    String en3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'encolleuse 3 brazil' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = en3, nativeQuery = true)
    List<JSONObject> statsEnc3b(LocalDate date, LocalDate date2);
    
    
    String enc1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'encolleuse 1 pagnoni' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = enc1, nativeQuery = true)
    List<JSONObject> statsEN1cp(LocalDate date, LocalDate date2);
    
    
    String enc2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'encolleuse 2 pagnoni' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = enc2, nativeQuery = true)
    List<JSONObject> statsEN2cp(LocalDate date, LocalDate date2);    
    
    String enc3 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'encolleuse 3 simi' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = enc3, nativeQuery = true)
    List<JSONObject> statsEN3cp(LocalDate date, LocalDate date2);
    
    String scieB = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom like 'scie Bong%' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = scieB, nativeQuery = true)
    List<JSONObject> statsSBong(LocalDate date, LocalDate date2);
    
    String pressTete = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'presse de tete' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = pressTete, nativeQuery = true)
    List<JSONObject> statsPressTete(LocalDate date, LocalDate date2);
    
    String Tran1 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'TRANCHEUSE CREMONA TN 4600  T1' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = Tran1, nativeQuery = true)
    List<JSONObject> statsTrancheuse1(LocalDate date, LocalDate date2);
    
    String Tran2 = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'TRANCHEUSE CREMONA TN 4600  T2' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = Tran2, nativeQuery = true)
    List<JSONObject> statsTrancheuse2(LocalDate date, LocalDate date2);
    
    String PSimi = "SELECT date_format(p.date, '%b %Y') as date,\n" +
        "COUNT(DISTINCT p.numero) as nbre,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "FROM pannes p\n" +
        "join machines m on p.id_machine = m.id_machine " +
        "WHERE m.nom = 'presse simi' and p.date BETWEEN ?1 and ?2\n"+
        "GROUP BY date_format(p.date, '%b'), p.numero\n" +
        "ORDER by p.date";
    
    @Query(value = PSimi, nativeQuery = true)
    List<JSONObject> statsPresseSimi(LocalDate date, LocalDate date2);
    
    String paretoRange = "SELECT m.nom, \n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from pannes p \n" +
            "join machines m on m.id_machine = p.id_machine\n" +
            "WHERE p.date between ?1 and ?2 \n" +
            "GROUP by m.nom, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = paretoRange, nativeQuery = true)
    public List<JSONObject> ParetoRange(LocalDate date, LocalDate date2);
    
    String paretoThisMonth = "SELECT m.nom, \n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from pannes p \n" +
            "join machines m on m.id_machine = p.id_machine\n" +
            "WHERE date_format(p.date, '%Y/%m') = ?1 \n" +
            "GROUP by m.nom, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = paretoThisMonth, nativeQuery = true)
    public List<JSONObject> ParetoThisMonth(String date);
    
    String recapPanne = "SELECT date_format(h.date, '%Y %M') as date, \n" +
        "COUNT(DISTINCT p.numero) as nbre, \n" +            
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)), 0) as WT, \n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)), 0) as TTR,\n" +
        "coalesce(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)), 0) as TDT \n" +
        "from heures h\n" +
        "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
//        "JOIN machines m on p.id_machine = m.id_machine\n" +
//        "WHERE p.dt > 15 AND m.label = 'correctif' "+    
        "GROUP by date_format(h.date, '%y%M'), p.numero \n" +
        "order by h.date desc \n" ;
    
    @Query(value = recapPanne, nativeQuery = true)
    public List<JSONObject> RecapPanne();
    
    
}
