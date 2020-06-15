package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Departement;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long>{

    String pannes = "SELECT DISTINCT "
            + "m.nom as machine, m.code, m.id_machine, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as wt, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as ttr, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)) as dt, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction "
            + "FROM pannes p "
            + "JOIN Techniciens t on p.id_technicien = t.id_technicien "
            + "JOIN operateurs o on p.id_operateur = o.id_operateur "
            + "JOIN machines m on p.id_machine = m.id_machine "
            + "JOIN lignes l on m.id_ligne = l.id_ligne "
            + "JOIN departement d on d.id_departement = l.id_departement "
            + "WHERE d.id_departement = ?1 "
            + "GROUP by p.numero "
            + "order By p.date desc, p.fin_inter desc";
    
    @Query(value = pannes, nativeQuery = true)
    List<JSONObject> panneDep(Long x);
    
    String countDepPanne = "SELECT COUNT(DISTINCT p.numero) as nbre, "
            + "SUM(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, "
            + "AVG(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as MDT "
            + "FROM pannes p "
            + "join machines m on m.id_machine = p.id_machine "
            + "JOIN lignes l on l.id_ligne = m.id_ligne "
            + "join departement d on d.id_departement = l.id_departement "
            + "WHERE date_format(p.date, '%Y/%m') = ?1 and d.id_departement = ?2 "
            + "GROUP BY date_format(p.date, '%b%Y'), d.nom";
    
    @Query(value = countDepPanne, nativeQuery = true)
    JSONObject CountDepPanne(String date, Long x); 
    
    String hourDep = "SELECT SUM(h.heure) as heure, d.nom "
            + "FROM heures h "
            + "join machines m on m.id_machine = h.id_machine "
            + "JOIN lignes l on l.id_ligne = m.id_ligne "
            + "join departement d on d.id_departement = l.id_departement "
            + "WHERE date_format(h.date, '%Y/%m') = ?1 and d.id_departement = ?2 "
            + "GROUP BY date_format(h.date, '%b%Y'), d.nom";
    
    @Query(value = hourDep, nativeQuery = true)
    JSONObject HourDep(String date, Long x); 
    
    
    String hByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "SUM(h.heure) as HT, d.nom \n" +
            "from heures h \n" +
            "join machines m on h.id_machine = m.id_machine \n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "WHERE d.id_departement = ?1 \n" +
            "GROUP by date_format(h.date, '%Y'), d.nom";
    
    @Query(value = hByYear, nativeQuery = true)
    public List<JSONObject> HByYear(Long id);
    
    String hThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "SUM(h.heure) as HT, d.nom \n" +
            "from heures h \n" +
            "join machines m on h.id_machine = m.id_machine \n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 \n" +
            "GROUP by date_format(h.date, '%b%Y'), d.nom order by h.date";
    
    @Query(value = hThisYear, nativeQuery = true)
    public List<JSONObject> HThisYear(Long id, String date);
    
    String pByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "SUM(h.heure) as heure,\n" +
            "d.nom,\n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%Y') = date_format(p.date, '%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1" +
            "\n" +
            "GROUP by date_format(h.date, '%Y')";
    
    @Query(value = pByYear, nativeQuery = true)
    public List<JSONObject> PByYear(Long id);
    
    String pThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = pThisYear, nativeQuery = true)
    public List<JSONObject> PThisYear(Long id, String date);
    
}
