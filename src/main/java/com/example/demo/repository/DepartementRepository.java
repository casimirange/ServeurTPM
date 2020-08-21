package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Departement;
import java.time.LocalDate;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long>{

    String pannes = "SELECT DISTINCT "
            + "m.nom as machine, m.code, m.id_machine as idM, "
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
    
    String dashboard = "SELECT p.date, count(distinct p.numero)as nbre, "
        + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.fin_inter)),0) as dt "
        + "FROM Pannes p JOIN machines m on p.id_machine = m.id_machine "
        + "JOIN lignes l on m.id_ligne = l.id_ligne "
        + "JOIN departement d on d.id_departement = l.id_departement "
        + "where p.date between ?1 and ?2 and d.id_departement = ?3 GROUP by date, numero order by p.date asc";
  
    @Query(value=dashboard, nativeQuery = true)
    public List<JSONObject> dashboard(LocalDate date, LocalDate date2, Long x);
    
    String countDepPanne = "SELECT date_format(p.date, '%Y/%m') as date, COUNT(DISTINCT p.numero) as nbre, "
            + "SUM(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, "
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as WT, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as TTR, "
            + "AVG(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as MDT "
            + "FROM pannes p "
            + "join machines m on m.id_machine = p.id_machine "
            + "JOIN lignes l on l.id_ligne = m.id_ligne "
            + "join departement d on d.id_departement = l.id_departement "
            + "WHERE date_format(p.date, '%Y/%m') = ?1 and d.id_departement = ?2 "
            + "GROUP BY date_format(p.date, '%b%Y'), d.nom, p.numero";
    
    @Query(value = countDepPanne, nativeQuery = true)
    List<JSONObject> CountDepPanne(String date, Long x); 
    
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
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" 
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as WT, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%Y') = date_format(p.date, '%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1" +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero";
    
    @Query(value = pByYear, nativeQuery = true)
    public List<JSONObject> PByYear(Long id);
    
    String pThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" 
            + "sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)) as WT, "
            + "sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = pThisYear, nativeQuery = true)
    public List<JSONObject> PThisYear(Long id, String date);
    
    String paretoYear = "SELECT m.nom, \n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from pannes p \n" +
            "join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "WHERE date_format(p.date, '%Y') = ?2  and d.id_departement = ?1 \n" +
            "GROUP by m.nom, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = paretoYear, nativeQuery = true)
    public List<JSONObject> ParetoYear(Long id, String date);
    
    String paretoThisMonth = "SELECT m.nom, \n" +
            "sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) as TDT, \n" +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from pannes p \n" +
            "join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "WHERE date_format(p.date, '%Y/%m') = ?2  and d.id_departement = ?1 \n" +
            "GROUP by m.nom, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = paretoThisMonth, nativeQuery = true)
    public List<JSONObject> ParetoThisMonth(Long id, String date);
    
//    Ligne 1
    String Ligne1DTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'ligne 1'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = Ligne1DTThisYear, nativeQuery = true)
    public List<JSONObject> Ligne1DTThisYear(Long id, String date);
    
    String Ligne1DTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 1' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = Ligne1DTByYear, nativeQuery = true)
    public List<JSONObject> Ligne1DTByYear(Long id);
    
    String Ligne1HTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 1'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = Ligne1HTByYear, nativeQuery = true)
    public List<JSONObject> Ligne1HTByYear(Long id);
    
    String Ligne1HTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 1' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = Ligne1HTThisYear, nativeQuery = true) 
    public List<JSONObject> Ligne1HTThisYear(Long id, String date);
    
    
//    Ligne 2
    String Ligne2DTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'ligne 2'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = Ligne2DTThisYear, nativeQuery = true)
    public List<JSONObject> Ligne2DTThisYear(Long id, String date);
    
    String Ligne2DTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 2' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = Ligne2DTByYear, nativeQuery = true)
    public List<JSONObject> Ligne2DTByYear(Long id);
    
    String Ligne2HTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 2'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = Ligne2HTByYear, nativeQuery = true)
    public List<JSONObject> Ligne2HTByYear(Long id);
    
    String Ligne2HTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 2' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = Ligne2HTThisYear, nativeQuery = true) 
    public List<JSONObject> Ligne2HTThisYear(Long id, String date);
    
    
//    Ligne 3
    String Ligne3DTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'ligne 3'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = Ligne3DTThisYear, nativeQuery = true)
    public List<JSONObject> Ligne3DTThisYear(Long id, String date);
    
    String Ligne3DTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 3' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = Ligne3DTByYear, nativeQuery = true)
    public List<JSONObject> Ligne3DTByYear(Long id);
    
    String Ligne3HTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 3'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = Ligne3HTByYear, nativeQuery = true)
    public List<JSONObject> Ligne3HTByYear(Long id);
    
    String Ligne3HTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ligne 3' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = Ligne3HTThisYear, nativeQuery = true) 
    public List<JSONObject> Ligne3HTThisYear(Long id, String date);
    
    //    sechoir
    String sechoirDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'sechoirs'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = sechoirDTThisYear, nativeQuery = true)
    public List<JSONObject> SechoirDTThisYear(Long id, String date);
    
    String sechoirDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'sechoirs' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = sechoirDTByYear, nativeQuery = true)
    public List<JSONObject> SechoirDTByYear(Long id);
    
    String sechoirHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'sechoirs'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = sechoirHTByYear, nativeQuery = true)
    public List<JSONObject> SechoirHTByYear(Long id);
    
    String sechoirHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'sechoirs' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = sechoirHTThisYear, nativeQuery = true) 
    public List<JSONObject> SechoirHTThisYear(Long id, String date);
    
    //    ecorçage
    String ecorçageDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'ecorcage'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = ecorçageDTThisYear, nativeQuery = true)
    public List<JSONObject> EcorçageDTThisYear(Long id, String date);
    
    String ecorçageDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'ecorcage' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = ecorçageDTByYear, nativeQuery = true)
    public List<JSONObject> EcorçageDTByYear(Long id);
    
    String ecorçageHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ecorcage'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = ecorçageHTByYear, nativeQuery = true)
    public List<JSONObject> EcorçageHTByYear(Long id);
    
    String ecorçageHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'ecorcage' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = ecorçageHTThisYear, nativeQuery = true) 
    public List<JSONObject> EcorçageHTThisYear(Long id, String date);
    
    //    ligne encollage Brazil
    String encollageBrazilDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'encollage Brazil'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = encollageBrazilDTThisYear, nativeQuery = true)
    public List<JSONObject> EncollageBrazilDTThisYear(Long id, String date);
    
    String encollageBrazilDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'encollage Brazil' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = encollageBrazilDTByYear, nativeQuery = true)
    public List<JSONObject> EncollageBrazilDTByYear(Long id);
    
    String encollageBrazilHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'encollage Brazil'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = encollageBrazilHTByYear, nativeQuery = true)
    public List<JSONObject> EncollageBrazilHTByYear(Long id);
    
    String encollageBrazilHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'encollage Brazil' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = encollageBrazilHTThisYear, nativeQuery = true) 
    public List<JSONObject> EncollageBrazilHTThisYear(Long id, String date);
    
    //    teinture
    String teintureDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'teinture'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = teintureDTThisYear, nativeQuery = true)
    public List<JSONObject> TeintureDTThisYear(Long id, String date);
    
    String teintureDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'teinture' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = teintureDTByYear, nativeQuery = true)
    public List<JSONObject> TeintureDTByYear(Long id);
    
    String teintureHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'teinture'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = teintureHTByYear, nativeQuery = true)
    public List<JSONObject> TeintureHTByYear(Long id);
    
    String teintureHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'teinture' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = teintureHTThisYear, nativeQuery = true) 
    public List<JSONObject> TeintureHTThisYear(Long id, String date);
    
    //    tranchage
    String tranchageDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'tranchage'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = tranchageDTThisYear, nativeQuery = true)
    public List<JSONObject> TranchageDTThisYear(Long id, String date);
    
    String tranchageDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'tranchage' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = tranchageDTByYear, nativeQuery = true)
    public List<JSONObject> TranchageDTByYear(Long id);
    
    String tranchageHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'tranchage'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = tranchageHTByYear, nativeQuery = true)
    public List<JSONObject> TranchageHTByYear(Long id);
    
    String tranchageHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'tranchage' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = tranchageHTThisYear, nativeQuery = true) 
    public List<JSONObject> TranchageHTThisYear(Long id, String date);
    
    //    ponçage
    String ponçageDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'poncage'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = ponçageDTThisYear, nativeQuery = true)
    public List<JSONObject> PonçageDTThisYear(Long id, String date);
    
    String ponçageDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'poncage' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = ponçageDTByYear, nativeQuery = true)
    public List<JSONObject> PonçageDTByYear(Long id);
    
    String ponçageHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'poncage'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = ponçageHTByYear, nativeQuery = true)
    public List<JSONObject> PonçageHTByYear(Long id);
    
    String ponçageHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'poncage' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = ponçageHTThisYear, nativeQuery = true) 
    public List<JSONObject> PonçageHTThisYear(Long id, String date);
    
    //    encollageCP
    String encollageCPDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'encollage CP'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = encollageCPDTThisYear, nativeQuery = true)
    public List<JSONObject> EncollageCPDTThisYear(Long id, String date);
    
    String encollageCPDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'encollage CP' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = encollageCPDTByYear, nativeQuery = true)
    public List<JSONObject> EncollageCPDTByYear(Long id);
    
    String encollageCPHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'encollage CP'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = encollageCPHTByYear, nativeQuery = true)
    public List<JSONObject> EncollageCPHTByYear(Long id);
    
    String encollageCPHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'encollage CP' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = encollageCPHTThisYear, nativeQuery = true) 
    public List<JSONObject> EncollageCPHTThisYear(Long id, String date);
    
    //    pressage
    String pressageDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'pressage'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = pressageDTThisYear, nativeQuery = true)
    public List<JSONObject> PressageDTThisYear(Long id, String date);
    
    String pressageDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'pressage' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = pressageDTByYear, nativeQuery = true)
    public List<JSONObject> PressageDTByYear(Long id);
    
    String pressageHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'pressage'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = pressageHTByYear, nativeQuery = true)
    public List<JSONObject> PressageHTByYear(Long id);
    
    String pressageHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'pressage' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = pressageHTThisYear, nativeQuery = true) 
    public List<JSONObject> PressageHTThisYear(Long id, String date);
    
    //    jointage
    String jointageDTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and date_format(h.date, '%Y') = ?2 and l.nom_ligne = 'jointage'" +
            "\n" +
            "GROUP by date_format(h.date, '%b%Y'), p.numero order by h.date";
    
    @Query(value = jointageDTThisYear, nativeQuery = true)
    public List<JSONObject> JointageDTThisYear(Long id, String date);
    
    String jointageDTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
            "d.nom,\n" +
            "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)),0) as TDT, \n" 
            + "COALESCE(sum(distinct timestampdiff(Minute, p.heure_arret, p.debut_inter)),0) as WT, "
            + "COALESCE(sum(distinct timestampdiff(Minute, p.debut_inter, p.fin_inter)),0) as TTR, " +
            "COUNT(DISTINCT p.numero) as nbre \n" +
            "from heures h \n" +
            "left outer JOIN pannes p on date_format(h.date, '%b%Y') = date_format(p.date, '%b%Y')\n" +
            "left outer join machines m on m.id_machine = p.id_machine\n" +
            "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
            "join departement d on d.id_departement = l.id_departement \n" +
            "\n" +
            "WHERE d.id_departement = ?1 and l.nom_ligne = 'jointage' " +
            "\n" +
            "GROUP by date_format(h.date, '%Y'), p.numero order by h.date";
    
    @Query(value = jointageDTByYear, nativeQuery = true)
    public List<JSONObject> JointageDTByYear(Long id);
    
    String jointageHTByYear = "SELECT date_format(h.date, '%Y') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'jointage'" +
        "\n" +
        "GROUP by date_format(h.date, '%Y') order by h.date";
    
    @Query(value = jointageHTByYear, nativeQuery = true)
    public List<JSONObject> JointageHTByYear(Long id);
    
    String jointageHTThisYear = "SELECT date_format(h.date, '%b') as date, \n" +
        "sum(h.heure) as HT \n" +
        "from heures h \n" +
        "left outer join machines m on m.id_machine = h.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n" +
        "\n" +
        "WHERE d.id_departement = ?1 and l.nom_ligne = 'jointage' and date_format(h.date, '%Y') = ?2" +
        "\n" +
        "GROUP by date_format(h.date, '%b%Y') order by h.date";
    
    @Query(value = jointageHTThisYear, nativeQuery = true) 
    public List<JSONObject> JointageHTThisYear(Long id, String date);
    
    String DerouleuseThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'der%' and date_format(p.date, '%Y') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = DerouleuseThisYear, nativeQuery = true) 
    public List<JSONObject> DerParetoThisYear(String date);
    
    String DerouleuseThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'der%' and date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = DerouleuseThisMonth, nativeQuery = true) 
    public List<JSONObject> DerParetoThisMonth(String date);
    
    String BobineuseThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'bobi%' and date_format(p.date, '%Y') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = BobineuseThisYear, nativeQuery = true) 
    public List<JSONObject> BobParetoThisYear(String date);
    
    String BobineuseThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'bobi%' and date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = BobineuseThisMonth, nativeQuery = true) 
    public List<JSONObject> BobParetoThisMonth(String date);
    
    String MagasinBobineThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'magasin b%' and date_format(p.date, '%Y') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = MagasinBobineThisYear, nativeQuery = true) 
    public List<JSONObject> MagBobParetoThisYear(String date);
    
    String MagasinBobineThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'magasin b%' and date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = MagasinBobineThisMonth, nativeQuery = true) 
    public List<JSONObject> MagBobParetoThisMonth(String date);
    
    String MassicotThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'massicot%' and date_format(p.date, '%Y') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = MassicotThisYear, nativeQuery = true) 
    public List<JSONObject> MassicotParetoThisYear(String date);
    
    String MassicotThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'massicot%' and date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = MassicotThisMonth, nativeQuery = true) 
    public List<JSONObject> MassicotParetoThisMonth(String date);
    
    String SechoirThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'sechoir%' and date_format(p.date, '%Y') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = SechoirThisYear, nativeQuery = true) 
    public List<JSONObject> SechoirParetoThisYear(String date);
    
    String SechoirThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'sechoir%' and date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = SechoirThisMonth, nativeQuery = true) 
    public List<JSONObject> SechoirParetoThisMonth(String date);
    
    String TrancheuseThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'trancheuse%' and date_format(p.date, '%Y') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = TrancheuseThisYear, nativeQuery = true) 
    public List<JSONObject> TrancheuseParetoThisYear(String date);
    
    String TrancheuseThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "WHERE m.nom LIKE 'trancheuse%' and date_format(p.date, '%Y/%m') = ?1\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = TrancheuseThisMonth, nativeQuery = true) 
    public List<JSONObject> TrancheuseParetoThisMonth(String date);
    
    String EncolleuseThisYear = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n"+ 
        "WHERE m.nom LIKE 'encolleuse%' and date_format(p.date, '%Y') = ?1 and d.id_departement = ?2\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = EncolleuseThisYear, nativeQuery = true) 
    public List<JSONObject> EncolleuseParetoThisYear(String date, Long dep);
    
    String EncolleuseThisMonth = "SELECT \n" +
        "date_format(p.date, '%b') as date, \n" +
        "m.nom,\n" +
        "COALESCE(sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)), 0) as TDT,\n" +
        "count(DISTINCT p.numero) as nbre\n" +
        "FROM pannes p JOIN machines m on m.id_machine = p.id_machine\n" +
        "JOIN lignes l on l.id_ligne = m.id_ligne \n" +
        "join departement d on d.id_departement = l.id_departement \n"+    
        "WHERE m.nom LIKE 'encolleuse%' and date_format(p.date, '%Y/%m') = ?1 and d.id_departement = ?2\n" +
        "GROUP by p.date, p.numero ORDER by sum(DISTINCT timestampdiff(minute, p.heure_arret, p.fin_inter)) desc";
    
    @Query(value = EncolleuseThisMonth, nativeQuery = true) 
    public List<JSONObject> EncolleuseParetoThisMonth(String date, Long dep);
}
