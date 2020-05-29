package com.example.demo.service.inter;

import com.example.demo.entity.Arrets;
import com.example.demo.reponses.ArretsReponse;
import java.util.List;

public interface IArretService {
    
    public List<Arrets> allArrets();
    
    public List<ArretsReponse> toutesPannes();
    
    public Arrets findOne(Long numero);
    
    void addArret(Arrets arret, Long code_machine);

    void updateArret(Arrets arret, Long code_machine);

    void deleteArret(Long id);

    Arrets showArret(Long id);
}
