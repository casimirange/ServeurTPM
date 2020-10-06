package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Arrets;
import com.example.demo.reponses.ArretsReponse;
import java.time.LocalDate;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ArretRepository extends JpaRepository<Arrets, Long>{
//    ArretsReponse findByNumero(String numero);
    Arrets findByNumero(String numero);
    
    String quer1 = "SELECT "
            + "m.nom as machine, m.code, m.id_machine, a.id_arret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret, "
            + "coalesce(sum(distinct timestampdiff(Minute, a.debut_arret, a.fin_arret)), 0) as DT \n" 
            + "FROM Arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=quer1, nativeQuery = true)
    public List<JSONObject> TousLesArrets();
    
    String arretThisMonth = "SELECT "
            + "m.nom as machine, m.code, m.id_machine, a.id_arret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret "
            + "FROM Arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "
            + "where date_format(a.date, '%Y/%m') = ?1 "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=arretThisMonth, nativeQuery = true)
    public List<JSONObject> ArretThisMonth(String date);
    
    String arretToday = "SELECT "
            + "m.nom as machine, m.code, m.id_machine, a.id_arret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret "
            + "FROM Arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "
            + "where a.date = ?1 "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=arretToday, nativeQuery = true)
    public List<JSONObject> ArretToday(LocalDate date);
    
    String arretYear = "SELECT "
            + "count(a.numero) as nbre "
            + "FROM Arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "
            + "where date_format(a.date, '%Y') = ?1 ";
  
    @Query( value=arretYear, nativeQuery = true)
    public List<JSONObject> ArretYear(int date);
    
    String arretRange = "SELECT "
            + "m.nom as machine, m.code, m.id_machine, a.id_arret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret "
            + "FROM Arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "
            + "where a.date between ?1 and ?2 "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=arretRange, nativeQuery = true)
    public List<JSONObject> ArretRange(LocalDate date, LocalDate date2);
    
    String arretTypeMonth = "SELECT \n" +
            "date_format(a.date, '%y %b') as date, \n" +
            "a.cause, \n" +
            "COUNT(a.numero) as nbre, \n" +
            "coalesce(sum(distinct timestampdiff(Minute, a.debut_arret, a.fin_arret)), 0) as TDT \n" +
            "FROM arrets a \n" +
            "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "+
            "where date_format(a.date, '%Y/%m') = ?1 \n" +
            "group by a.cause, a.numero";
  
    @Query( value=arretTypeMonth, nativeQuery = true)
    public List<JSONObject> ArretTypeMonth(String date);
    
    String arretTypeRange = "SELECT \n" +
            "date_format(a.date, '%y %b') as date, \n" +
            "a.cause, \n" +
            "COUNT(a.numero) as nbre, \n" +
            "coalesce(sum(distinct timestampdiff(Minute, a.debut_arret, a.fin_arret)), 0) as TDT \n" +
            "FROM arrets a \n" +
            "JOIN machines m on (a.id_machine = m.id_machine and m.label like 'corr%' and m.localisation like 'bonaber%') "+
            "where a.date between ?1 and ?2 \n" +
            "group by a.cause, a.numero";
  
    @Query( value=arretTypeRange, nativeQuery = true)
    public List<JSONObject> ArretTypeRange(LocalDate date, LocalDate date2);
    
    
    String countLast30Day = "SELECT date, count(distinct numero)as nbre, "
            + "COALESCE(sum(distinct timestampdiff(Minute, debut_arret, fin_arret)),0) as dt "
            + "FROM arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.localisation like 'bona%') "
            + "where date between ?1 and ?2  "
            + "GROUP by date, numero order by date asc";

  
    @Query(value=countLast30Day, nativeQuery = true)
    public List<JSONObject> ArretLast30Days(LocalDate date, LocalDate date2);
    
    String countMonthPanne = "SELECT date, count(distinct numero)as nbre, "
            + "COALESCE(sum(distinct timestampdiff(Minute, debut_arret, fin_arret)),0) as dt "
            + "FROM arrets a "
            + "JOIN machines m on (a.id_machine = m.id_machine and m.localisation like 'bona%') "
            + "where DATE_FORMAT(date, '%Y/%m') = ?1 "
            + "GROUP by date, numero order by date asc";

  
    @Query(value=countMonthPanne, nativeQuery = true)
    public List<JSONObject> countMonthPanne(String date);
    
        String recapArret = "SELECT date_format(h.date, '%Y %M') as date, \n" +
        "COUNT(DISTINCT a.numero) as nbre \n" +  
        "from heures h\n" +
        "left outer JOIN arrets a on date_format(h.date, '%b%Y') = date_format(a.date, '%b%Y')\n" +
        "JOIN machines m on a.id_machine = m.id_machine\n" +
        "WHERE m.localisation like 'bonab%' "+    
        "GROUP by date_format(h.date, '%y%M'), a.numero \n" +
        "order by h.date desc \n" ;
    
    @Query(value = recapArret, nativeQuery = true)
    public List<JSONObject> RecapPanne();
    
    
    String paretoRange = "SELECT m.nom, \n" +
            "sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)) as TDT, \n" +
            "COUNT(DISTINCT a.numero) as nbre \n" +
            "from arrets a \n" +
            "join machines m on m.id_machine = a.id_machine\n" +
            "WHERE a.date between ?1 and ?2 and m.localisation like 'bonab%' and m.label like 'corr%'\n" +
            "GROUP by m.nom, a.numero ORDER by sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)) desc";
    
    @Query(value = paretoRange, nativeQuery = true)
    public List<JSONObject> ParetoRange(LocalDate date, LocalDate date2);
    
    String paretoThisMonth = "SELECT m.nom, \n" +
            "sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)) as TDT, \n" +
            "COUNT(DISTINCT a.numero) as nbre \n" +
            "from arrets a \n" +
            "join machines m on m.id_machine = a.id_machine\n" +
            "WHERE date_format(a.date, '%Y/%m') = ?1 and m.localisation like 'bonab%' and m.label like 'corr%'\n" +
            "GROUP by m.nom, a.numero ORDER by sum(DISTINCT timestampdiff(minute, a.debut_arret, a.fin_arret)) desc";
    
    @Query(value = paretoThisMonth, nativeQuery = true)
    public List<JSONObject> ParetoThisMonth(String date);
}
