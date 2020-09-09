package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Machines;
import java.time.LocalDate;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface MachineRepository extends JpaRepository<Machines, Long> {

    String machines = "SELECT d.nom as dep, m.nom as machine, m.code, m.id_machine as idM "
            + "FROM machines m "
            + "JOIN lignes l on m.id_ligne = l.id_ligne "
            + "JOIN departement d on d.id_departement = l.id_departement "
            + "WHERE d.id_departement = ?1";
    
    @Query(value = machines, nativeQuery = true)
    List<JSONObject> machDep(Long x);
    
    String historiquePannes = "SELECT DISTINCT "
            + "m.nom as machine, m.code, m.id_machine as idM, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, "
            + "p.cont, p.quart, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM pannes p "
            + "JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "WHERE m.id_machine = ?1 "
            + "GROUP by p.numero "
            + "order By p.date desc, p.heure_arret desc";
    
    @Query(value = historiquePannes, nativeQuery = true)
    List<JSONObject> historiquePannes(Long x);
    
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
            + "where p.date = ?1 and m.id_machine = ?2 "
            + "GROUP by p.numero ";
      
      
    @Query( value=quer6, nativeQuery = true)
    public List<JSONObject> ToDayPannes(LocalDate date, Long id);
    
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
            + "where p.date between ?1 and ?2  and m.id_machine = ?3 "
            + "GROUP by p.numero "
            + "order by p.date desc";
      
      
    @Query( value=quer8, nativeQuery = true)
    public List<JSONObject> WeekPannes(LocalDate date, LocalDate date2, Long id);
    
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
            + "where DATE_FORMAT(p.date, '%Y/%m') = ?1 and m.id_machine = ?2 "
            + "GROUP by p.numero "
            + "order by p.date desc";
      
      
    @Query( value=quer9, nativeQuery = true)
    public List<JSONObject> MonthPannes(String date, Long id);
    
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
    + "where DATE_FORMAT(p.date, '%Y') = ?1 and m.id_machine = ?2 "
    + "GROUP by p.numero "
    + "order by p.date desc";
      
      
    @Query( value=quer10, nativeQuery = true)
    public List<JSONObject> YearPannes(String date, Long id);
    
    String hourThisMonthMachine = "SELECT SUM(h.heure) as heure, m.nom "
            + "FROM heures h "
            + "join machines m on m.id_machine = h.id_machine "
            + "WHERE date_format(h.date, '%Y/%m') = ?1 and m.id_machine = ?2 "
            + "GROUP BY date_format(h.date, '%b%Y')";
    
    @Query(value = hourThisMonthMachine, nativeQuery = true)
    JSONObject HourThisMonthMachine(String date, Long x); 
    
    
    String hByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "SUM(h.heure) as HT, m.nom \n" +
            "from heures h \n" +
            "join machines m on h.id_machine = m.id_machine \n" +
            "WHERE m.id_machine = ?1 \n" +
            "GROUP by date_format(h.date, '%Y'), m.nom";
    
    @Query(value = hByYear, nativeQuery = true)
    public List<JSONObject> HByYear(Long id);
    
    String hThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "SUM(h.heure) as HT, m.nom \n" +
            "from heures h \n" +
            "join machines m on h.id_machine = m.id_machine \n" +
            "WHERE m.id_machine = ?1 and date_format(h.date, '%Y') = ?2 \n" +
            "GROUP by date_format(h.date, '%b%Y'), m.nom order by h.date";
    
    @Query(value = hThisYear, nativeQuery = true)
    public List<JSONObject> HThisYear(Long id, String date);
    
    String pByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "SUM(h.heure) as heure,\n" +
            "m.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%Y') = date_format(p.date, '%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "WHERE m.id_machine = ?1" +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero";
    
    @Query(value = pByYear, nativeQuery = true)
    public List<JSONObject> PByYear(Long id);
    
    String pThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "m.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "WHERE m.id_machine = ?1 and date_format(h.date, '%Y') = ?2" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = pThisYear, nativeQuery = true)
    public List<JSONObject> PThisYear(Long id, String date);
}
