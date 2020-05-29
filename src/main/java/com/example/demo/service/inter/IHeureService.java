package com.example.demo.service.inter;

import com.example.demo.entity.Heures;
import java.util.List;
import net.minidev.json.JSONObject;

public interface IHeureService {

    public List<Heures> allHeures();
    
    public List<JSONObject> toutesHeures();
    
    public Heures findOne(Long id);
    
    void addHeures(Heures heure, Long code_machine);

    void updateHeures(Heures heure, Long code_machine);

    void deleteHeures(Long id);
}
