package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Heures;
import java.time.LocalDate;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface HeureRepository extends JpaRepository<Heures, Long>{

    String hourMonth = "SELECT h.date as date, m.nom as machine, m.code, h.heure,\n" +
        "count(a.numero) as nbre,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
        "from heures h LEFT OUTER JOIN arrets a on (a.date = h.date AND a.id_machine = h.id_machine)\n" +
        "join machines m on (m.id_machine = h.id_machine and m.localisation like 'bonab%') \n" +
        "where date_format(h.date, '%Y/%m') = ?1 "+    
        "GROUP by h.date, m.nom ORDER BY h.date DESC";
  
    @Query(value=hourMonth, nativeQuery = true)
    public List<JSONObject> HeuresMonth(String month);

    String hourRange = "SELECT h.date as date, m.nom as machine, m.code, h.heure,\n" +
        "count(a.numero) as nbre,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
        "from heures h LEFT OUTER JOIN arrets a on (a.date = h.date AND a.id_machine = h.id_machine)\n" +
        "join machines m on (m.id_machine = h.id_machine and m.localisation like 'bonab%')\n" +
        "where h.date between ?1 and ?2 "+      
        "GROUP by h.date, m.nom ORDER BY h.date DESC";
  
    @Query(value=hourRange, nativeQuery = true)
    public List<JSONObject> HeuresRange(LocalDate date1, LocalDate date2);

    String hourbydepMachine = "SELECT h.date as date, m.nom, m.code, sum(h.heure) as heure, d.nom as dep, count(DISTINCT m.nom) as nombre_machine,\n" +
        "count(a.numero) as nbre,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
        "from  heures h LEFT OUTER JOIN arrets a on (a.date = h.date AND a.id_machine = h.id_machine)\n" +
        "join machines m on (m.id_machine = h.id_machine and m.localisation like 'bonab%')\n" +
        "join lignes l on l.id_ligne = m.id_ligne\n" +
        "join departement d on d.id_departement = l.id_departement\n" +
        "where h.date = ?1 and d.nom = ?2\n" +
        "GROUP by m.nom, h.date ORDER BY m.nom";
  
    @Query(value=hourbydepMachine, nativeQuery = true)
    public List<JSONObject> HeuresByDepMachine(LocalDate date, String dep);

    String hourbydepMachineMonth = "SELECT h.date as date, m.nom, m.code, sum(h.heure) as heure, d.nom as dep, count(DISTINCT m.nom) as nombre_machine,\n" +
        "count(a.numero) as nbre,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
        "from  heures h LEFT OUTER JOIN arrets a on (a.date = h.date AND a.id_machine = h.id_machine)\n" +
        "join machines m on (m.id_machine = h.id_machine and m.localisation like 'bonab%')\n" +
        "join lignes l on l.id_ligne = m.id_ligne\n" +
        "join departement d on d.id_departement = l.id_departement\n" +
        "where date_format(h.date, '%Y-%m') = ?1 and d.nom = ?2\n" +
        "GROUP by m.nom, h.date ORDER BY m.nom";
  
    @Query(value=hourbydepMachineMonth, nativeQuery = true)
    public List<JSONObject> HeuresByDepMachineMonth(String date, String dep);

    String hourbydep = "SELECT h.date as date, m.nom, m.code, sum(h.heure) as heure, d.nom as dep, count(DISTINCT m.nom) as nombre_machine,\n" +
        "count(a.numero) as nbre,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
        "from  heures h LEFT OUTER JOIN arrets a on (a.date = h.date AND a.id_machine = h.id_machine)\n" +
        "join machines m on (m.id_machine = h.id_machine and m.localisation like 'bonab%')\n" +
        "join lignes l on l.id_ligne = m.id_ligne\n" +
        "join departement d on d.id_departement = l.id_departement\n"
            + "where h.date = ?1 " +
        "GROUP by d.nom, h.date ORDER BY m.nom";
  
    @Query(value=hourbydep, nativeQuery = true)
    public List<JSONObject> HeuresByDep(LocalDate date);

    String hourbydepMonth = "SELECT h.date as date, m.nom, sum(h.heure) as heure, d.nom as dep, count(DISTINCT m.nom) as nombre_machine,\n" +
        "count(a.numero) as nbre,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)),0) as AT\n" +
        "from  heures h LEFT OUTER JOIN arrets a on (a.date = h.date AND a.id_machine = h.id_machine)\n" +
        "join machines m on (m.id_machine = h.id_machine and m.localisation like 'bonab%')\n" +
        "join lignes l on l.id_ligne = m.id_ligne\n" +
        "join departement d on d.id_departement = l.id_departement\n"
        + "where date_format(h.date, '%Y-%m') = ?1 " +
        "GROUP by d.nom, date_format(h.date, '%Y-%m') ORDER BY h.date DESC";
  
    @Query(value=hourbydepMonth, nativeQuery = true)
    public List<JSONObject> HeuresByDepMonth(String date);

    String machbydep = "SELECT d.nom, count(DISTINCT m.nom) as mach \n" +
        "FROM machines m \n" +
        "join lignes l on m.id_ligne = l.id_ligne\n" +
        "join departement d on d.id_departement = l.id_departement\n" +
        "where m.localisation like 'bonabe%' "+    
        "GROUP by d.nom";
  
    @Query(value=machbydep, nativeQuery = true)
    public List<JSONObject> MachinesByDep();

    String machinebydep = "SELECT d.nom, m.nom as mach, m.code \n" +
        "FROM machines m \n" +
        "join lignes l on m.id_ligne = l.id_ligne\n" +
        "join departement d on d.id_departement = l.id_departement\n" +
        "where m.localisation like 'bonabe%' and d.nom = ?1 "
            + "order by m.nom asc";
  
    @Query(value=machinebydep, nativeQuery = true)
    public List<JSONObject> MachByDep(String dep);
}
