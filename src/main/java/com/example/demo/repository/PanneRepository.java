package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Pannes;
import com.example.demo.entity.Techniciens;
import com.example.demo.reponses.CountPannesResponse;
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
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM Pannes p JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "GROUP by p.numero "
            + "order By p.date desc, p.fin_inter desc";
  
    @Query( value=quer1, nativeQuery = true)
    public List<JSONObject> ToutesLesPannes();
        
    String quer2 = "SELECT distinct new com.example.demo.reponses.PannesTechReponse("
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer2)
    public List<PannesTechReponse> Techs(String numero);
    
    String quer3 = "SELECT new com.example.demo.reponses.PannesNonAcheveesReponse("
            + "m.nom as machine, m.code, m.idMachine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "JOIN p.operateurs o "
            + "JOIN p.machines m "
            + "where p.etat = 0 "
            + "GROUP by p.numero "
            + "order By p.idPanne desc";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer3)
    public List<PannesNonAcheveesReponse> PannesNonAchevees();
    
    
    String quer4 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
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
    
    String quer5 = "SELECT distinct new com.example.demo.reponses.PannesHeureReponse("
            + "p.heure_arret, p.debut_inter, p.fin_inter, p.numero) "
            + "FROM Pannes p "
            + "where p.numero = ?1";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer5)
    public List<PannesHeureReponse> Heures(String numero);
    
        String quer7 = "SELECT new com.example.demo.reponses.PannesHeureReponse("
            + "p.heure_arret, p.debut_inter, p.fin_inter, p.numero) "
            + "FROM Pannes p "
            + "group by p.numero order by p.fin_inter asc";
  
    @Query( value=quer7)
    public List<PannesHeureReponse> Heure();
    
    
    String quer6 = "SELECT m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
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
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
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
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
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
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
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
    
    String countAll = "SELECT distinct new com.example.demo.reponses.CountPannesResponse("
            + "m.nom as machine, m.code, m.idMachine as idM, count(DISTINCT p.numero) as nbre, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction, d.nom as dep, d.idDepartement) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "JOIN p.operateurs o "
            + "JOIN p.machines m "
            + "JOIN m.lignes l "
            + "JOIN l.departement d "
            + "WHERE date_format(p.date, '%Y/%m') = ?1 "
            + "GROUP by m.nom "
            + "order by nbre desc";
  
    @Query( value=countAll)
    public List<CountPannesResponse> TotalLPannes(String date);
    
    String countAllDepPanne = "SELECT distinct new com.example.demo.reponses.CountPannesResponse("
            + "m.nom as machine, m.code, m.idMachine as idM, count(DISTINCT p.numero) as nbre, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction, d.nom as dep, d.idDepartement) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "JOIN p.operateurs o "
            + "JOIN p.machines m "
            + "JOIN m.lignes l "
            + "JOIN l.departement d "
            + "GROUP by d.nom "
            + "order by nbre";
  
    @Query( value=countAllDepPanne)
    public List<CountPannesResponse> TotallDepPannes();
    
    String countDay = "SELECT distinct new com.example.demo.reponses.CountPannesResponse("
            + "m.nom as machine, m.code, m.idMachine as idM, count(DISTINCT p.numero) as nbre, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction, d.nom as dep, d.idDepartement) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "JOIN p.operateurs o "
            + "JOIN p.machines m "
            + "JOIN m.lignes l "
            + "JOIN l.departement d "
            + "where p.date = ?1 "
            + "GROUP by m.nom "
            + "order by nbre desc";
  
    @Query( value=countDay)
    public List<CountPannesResponse> TotalLtodayPannes(LocalDate date);
}
