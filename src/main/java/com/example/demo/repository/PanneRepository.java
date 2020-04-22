package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Pannes;
import com.example.demo.reponses.PannesReponse;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PanneRepository extends JpaRepository<Pannes, Long> {
    
    String quer1 = "SELECT new com.example.demo.reponses.PannesReponse("
            + "m.nom, m.code, "
            + "p.cause, p.date, p.debut_inter, p.description, p.détails, p.etat, p.fin_inter, p.heure_arret, p.numero,"
            + "o.nom as nomOp, o.prenom as preOp, o.matricule as matOp, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule as matTec, t.fonction)"
            + " FROM Pannes p JOIN p.techniciens t "
            + "JOIN p.operateurs o "
            + "JOIN p.machines m";
    
    String quer2 = "SELECT new com.example.demo.reponses.PannesReponse("
            + "m.nom as machine, m.code, "
            + "p.date, p.numero, p.cause, p.description, p.details, p.heure_arret, p.debut_inter, p.fin_inter, p.etat, p.outil, p.ref, p.qte, "
            + "o.nom as nomOP, o.prenom as prenomOP, o.matricule as matOP, "
            + "t.nom as nomTec, t.prenom as preTec, t.matricule, t.fonction) "
            + "FROM Pannes p JOIN p.techniciens t "
            + "JOIN p.operateurs o "
            + "JOIN p.machines m "
            + "order By p.idPanne desc";
  
    //String quer2 = "SELECT new LignesReponse(nomLigne) FROM lignes";
    @Query( value=quer2)
    public List<PannesReponse> ToutesLesPannes();
}
