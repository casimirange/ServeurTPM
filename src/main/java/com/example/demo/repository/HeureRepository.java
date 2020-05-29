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

    String test = "SELECT h.date, h.heure, m.nom as machine, m.code, m.id_machine "
            + "FROM Heures h join machines m on h.id_machine = m.id_machine order by date asc";
  
    @Query(value=test, nativeQuery = true)
    public List<JSONObject> Heures();
}
