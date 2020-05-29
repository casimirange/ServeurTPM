/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

import com.example.demo.entity.Heures;
import com.example.demo.model.HeureModel;
import com.example.demo.repository.HeureRepository;
import com.example.demo.service.HeuresServices;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casimir
 */
@RestController 
@CrossOrigin
@RequestMapping(value = "/api/heures")
public class HeureController {
    @Autowired
    private HeureRepository heureRepository ;
    
    @Autowired
    private HeuresServices heuresServices;
    
    @GetMapping
    public List<Heures> getHeures(){
        return heuresServices.allHeures();
    }

    @GetMapping("/all")
    public List<JSONObject> ToutesLesHeures(){
        return heuresServices.toutesHeures();
    }
    
    @PostMapping
    public void creationPanne(@RequestBody HeureModel heureModel) {
            Heures heure = new Heures(
                    heureModel.getHeure(), 
                    heureModel.getDate());
            heuresServices.addHeures(heure, heureModel.getIdMachine());
    }
    
//    @PutMapping("/{numero}")
//    public void updatePanne(@RequestBody ArretModel arretModel, @PathVariable String numero) {
//            Arrets arret = new Arrets(
//                    arretModel.getDate(), 
//                    arretModel.getDebutArret(), 
//                    arretModel.getFinArret(), 
//                    arretModel.getCause(),
//                    arretModel.isEtat(),
//                    arretModel.getNumero());
//            arretService.updateArret(arret, arretModel.getIdMachine());
//    }
}
