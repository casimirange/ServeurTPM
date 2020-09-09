package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Pannes;
import com.example.demo.entity.Techniciens;
//import com.example.demo.reponses.CountPannesResponse;
import com.example.demo.reponses.PSR;
import com.example.demo.reponses.PannesNonAcheveesReponse;
import com.example.demo.reponses.PannesReponse;
import com.example.demo.reponses.PannesHeureReponse;
import com.example.demo.reponses.PannesTechReponse;
import java.time.LocalDate;
import java.util.List;
import net.bytebuddy.asm.Advice;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PanneRepository extends JpaRepository<Pannes, Long> {
    
    String quer1 = "SELECT "
            + "m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, o.id_operateur as idOperateur, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction, t.id_technicien "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "GROUP by p.numero "
            + "order By p.date desc, p.heure_arret desc";
  
    @Query( value=quer1, nativeQuery = true)
    public List<JSONObject> ToutesLesPannes();
        
    String quer2 = "SELECT new com.example.demo.reponses.PannesTechReponse("
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction, p.quart) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer2)
    public List<PannesTechReponse> Techs(String numero);
        
    String operateur = "SELECT distinct "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, p.quart "
            + "FROM Pannes p JOIN operateurs o on p.id_operateur = o.id_operateur  "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=operateur, nativeQuery = true)
    public List<JSONObject> Ops(String numero);
    
    String quer3 = "SELECT "
            + "m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "where p.etat = 0 "
            + "GROUP by p.numero "
            + "order By p.id_panne desc";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer3, nativeQuery = true)
    public List<JSONObject> PannesNonAchevees();
    
    
    String quer4 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "where p.numero = ?1 "
            + "GROUP by p.numero";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer4, nativeQuery = true)
    JSONObject findPanne(String numero);
    
    List<Pannes> findByNumero(String numero);
    
    String quer5 = "SELECT distinct "
            + "p.heure_arret, p.debut_inter, p.fin_inter, p.numero "
            + "FROM Pannes p "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer5, nativeQuery = true)
    public List<JSONObject> Heures(String numero);
    
    String cause = "SELECT distinct "
            + "p.cause, p.description, p.details, p.quart "
            + "FROM Pannes p "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=cause, nativeQuery = true)
    public List<JSONObject> Details(String numero);
    
    String outils = "SELECT distinct "
            + "p.outil, p.ref, p.qte, p.quart "
            + "FROM Pannes p "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=outils, nativeQuery = true)
    public List<JSONObject> Outils(String numero);
    
        String quer7 = "SELECT new com.example.demo.reponses.PannesHeureReponse("
            + "p.heure_arret, p.debut_inter, p.fin_inter, p.numero) "
            + "FROM Pannes p "
            + "group by p.numero order by p.fin_inter asc";
  
    @Query( value=quer7)
    public List<PannesHeureReponse> Heure();
    
    
    String quer6 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat,"
            + " p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "where p.date = ?1 "
            + "GROUP by p.numero ";
      
      
    @Query( value=quer6, nativeQuery = true)
    public List<JSONObject> ToDayPannes(LocalDate date);
    
        String quer8 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "where p.date between ?1 and ?2 "
            + "GROUP by p.numero "
            + "order by p.date desc";
      
      
    @Query( value=quer8, nativeQuery = true)
    public List<JSONObject> WeekPannes(LocalDate date, LocalDate date2);
    
        String quer9 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "where DATE_FORMAT(p.date, '%Y/%m') = ?1 "
            + "GROUP by p.numero "
            + "order by p.date desc";
      
      
    @Query( value=quer9, nativeQuery = true)
    public List<JSONObject> MonthPannes(String date);
    
    String quer10 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
    + "where DATE_FORMAT(p.date, '%Y') = ?1 "
    + "GROUP by p.numero "
    + "order by p.date desc";
      
      
    @Query( value=quer10, nativeQuery = true)
    public List<JSONObject> YearPannes(String date);
    
    String countAll = "SELECT distinct "
            + "m.nom as machine, m.code, count(DISTINCT p.numero) as nbre "
            + "FROM Pannes p JOIN machines m on p.id_machine = m.id_machine "            
            + "WHERE date_format(p.date, '%Y/%m') = ?1 "
            + "GROUP by m.nom "
            + "order by nbre desc";
  
    @Query( value=countAll, nativeQuery = true)
    public List<JSONObject> TotalLPannes(String date);
    
    String countAllRange = "SELECT distinct "
            + "m.nom as machine, m.code, count(DISTINCT p.numero) as nbre "
            + "FROM Pannes p JOIN machines m on p.id_machine = m.id_machine "            
            + "WHERE p.date between ?1 and ?2 "
            + "GROUP by m.nom "
            + "order by nbre desc";
  
    @Query( value=countAllRange, nativeQuery = true)
    public List<JSONObject> TotalRangePannes(LocalDate date1, LocalDate date2);
    
    String countAllDepPanne = "SELECT distinct "
            + "m.nom as machine, m.code, m.id_machine as idM, count(DISTINCT p.numero) as nbre, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction, d.nom as dep, d.id_departement "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "JOIN lignes l on m.id_ligne = l.id_ligne "
            + "JOIN departement d on l.id_departement = d.id_departement "
            + "GROUP by d.nom "
            + "order by nbre";
  
    @Query( value=countAllDepPanne, nativeQuery = true)
    public List<JSONObject> TotallDepPannes();
    
    String countDay = "SELECT distinct "
            + "m.nom as machine, m.code, count(DISTINCT p.numero) as nbre "
            + "FROM Pannes p JOIN machines m on p.id_machine = m.id_machine "
            + "where p.date = ?1 "
            + "GROUP by m.nom "
            + "order by nbre desc";
  
    @Query( value=countDay, nativeQuery = true)
    public List<JSONObject> TotalLtodayPannes(LocalDate date);
}
