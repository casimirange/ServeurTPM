package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Arrets;
import com.example.demo.reponses.ArretsReponse;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ArretRepository extends JpaRepository<Arrets, Long>{
//    ArretsReponse findByNumero(String numero);
    Arrets findByNumero(String numero);
    
    String quer1 = "SELECT new com.example.demo.reponses.ArretsReponse("
            + "m.nom as machine, m.code, m.idMachine, a.idArret, a.created_at, "
            + "a.date, a.numero, a.cause, a.debut_arret, a.fin_arret, a.etat) "
            + "FROM Arrets a "
            + "JOIN a.machines m "
            + "GROUP by a.numero "
            + "order By a.date desc";
  
    @Query( value=quer1)
    public List<ArretsReponse> TousLesArrets();
}
