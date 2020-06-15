package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Machines;
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
    
    
}
