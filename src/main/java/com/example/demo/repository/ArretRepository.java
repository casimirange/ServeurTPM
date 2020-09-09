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
            + "JOIN machines m on a.id_machine = m.id_machine "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=quer1, nativeQuery = true)
    public List<JSONObject> TousLesArrets();
    
    String arretThisMonth = "SELECT "
            + "m.nom as machine, m.code, m.id_machine, a.id_arret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret "
            + "FROM Arrets a "
            + "JOIN machines m on a.id_machine = m.id_machine "
            + "where date_format(a.date, '%Y/%m') = ?1 "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=arretThisMonth, nativeQuery = true)
    public List<JSONObject> ArretThisMonth(String date);
    
    String arretToday = "SELECT "
            + "m.nom as machine, m.code, m.id_machine, a.id_arret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret "
            + "FROM Arrets a "
            + "JOIN machines m on a.id_machine = m.id_machine "
            + "where a.date = ?1 "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=arretToday, nativeQuery = true)
    public List<JSONObject> ArretToday(LocalDate date);
    
    String arretTypeMonth = "SELECT \n" +
            "date_format(a.date, '%y %b') as date, \n" +
            "a.cause, \n" +
            "COUNT(a.numero) as nbre, \n" +
            "coalesce(sum(distinct timestampdiff(Minute, a.debut_arret, a.fin_arret)), 0) as TDT \n" +
            "FROM arrets a \n" +
            "where date_format(a.date, '%Y/%m') = ?1 \n" +
            "group by a.cause, a.numero";
  
    @Query( value=arretTypeMonth, nativeQuery = true)
    public List<JSONObject> ArretTypeMonth(String date);
}
