/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

import com.example.demo.entity.Arrets;
import com.example.demo.model.ArretModel;
import com.example.demo.reponses.ArretsReponse;
import com.example.demo.repository.ArretRepository;
import com.example.demo.service.ArretService;
import com.example.demo.service.inter.IArretService;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/api/arrets")
public class ArretController {
    String mts;
    @Autowired
    private ArretRepository arretRepository ;
    
    @Autowired
    private ArretService arretService;

    public ArretRepository getArretRepository() {
        return arretRepository;
    }

    public void setArretRepository(ArretRepository arretRepository) {
        this.arretRepository = arretRepository;
    }

    public ArretService getArretService() {
        return arretService;
    }

    public void setArretService(ArretService arretService) {
        this.arretService = arretService;
    }
    
    
    
    @GetMapping
    public List<Arrets> getPannes(){
        return arretRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @GetMapping("/all")
    public List<ArretsReponse> ToutesLesArrets(){
        return arretService.toutesPannes();
    }
    
    @PostMapping
    public void creationPanne(@RequestBody ArretModel arretModel) {
            Arrets arret = new Arrets(
                    arretModel.getDate(), 
                    arretModel.getDebutArret(), 
                    arretModel.getFinArret(), 
                    arretModel.getCause(),
                    arretModel.getNumero());
            arretService.addArret(arret, arretModel.getIdMachine());
    }
    
    @PutMapping("/{numero}")
    public void updatePanne(@RequestBody ArretModel arretModel, @PathVariable String numero) {
            Arrets arret = new Arrets(
                    arretModel.getDate(), 
                    arretModel.getDebutArret(), 
                    arretModel.getFinArret(), 
                    arretModel.getCause(),
                    arretModel.getNumero());
            arretService.updateArret(arret, arretModel.getIdMachine());
    }
        	
    @GetMapping("/{id}")
    public Arrets showArret(@PathVariable Long id) {
            return arretService.showArret(id);
    }
            	
    @GetMapping("/thisMonth")
    public List<JSONObject> showThisMonthArret() {
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month < 10){
                mts = String.valueOf(year)+"/0"+ String.valueOf(month+1);
            System.out.println("ce mois: "+ mts);
            }else{
                mts = String.valueOf(year)+"/"+ String.valueOf(month+1);
            }
            return arretRepository.ArretThisMonth(mts);
    }
            	
    @GetMapping("/today")
    public List<JSONObject> showTodayArret() {
        LocalDate date = LocalDate.now();
        return arretRepository.ArretToday(date);
    }
        	
    @DeleteMapping("/{id}")
    public void deleTechnicien(@PathVariable Long id) {
            arretService.deleteArret(id);
    }
    
}
