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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    String mts, type;
    JSONObject json, json2;
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
    public List<JSONObject> ToutesLesArrets(){
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
            	
    @GetMapping("/typeThisMonth")
    public List<JSONObject> TypeMonthArret() {
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
            List<JSONObject> Allpannes = arretRepository.ArretTypeMonth(mts);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> nbre = new ArrayList<>();
            List<JSONObject> tdth = new ArrayList<>();
            
            Allpannes.forEach(x -> {
                String t = x.get("cause").toString();
                if(!t.equals("délestage") && !t.equals("Bourrage") && !t.equals("Manque de Bobine") 
                        && !t.equals("Manque de Personnel") && !t.equals("Manque de Matériel")){
                    type = "Divers";
                }else {
                    type = t;
                }
                response2.put("date", x.get("date"));
                response2.put("type", type);
                response2.put("nbre", x.get("nbre"));
                response2.put("TDT", x.get("TDT"));
                json = new JSONObject(response2);
                MTBF2.add(json);
            });
            
            Map<String, Integer> result = MTBF2.stream().collect(
            Collectors.groupingBy(e -> e.get("type").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("type", date.getKey());
            response2.put("nbre", date.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        Map<String, Double> tdtd = MTBF2.stream().collect(
            Collectors.groupingBy(f -> f.get("type").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("TDT")).doubleValue()))); 
        
        tdtd.entrySet().stream()
            .forEach(dates -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("type", dates.getKey());
            response2.put("TDT", dates.getValue().doubleValue());
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        
        nbre.forEach(n->{
            tdth.forEach(td->{
                if(n.get("type").toString().equals(td.get("type").toString())){
                    response2.put("type", n.get("type").toString());
                    response2.put("nbre", n.get("nbre").toString());
                    response2.put("TDT", td.get("TDT").toString());
                    json2 = new JSONObject(response2);
                    MTBF.add(json2);
                }
            });
        });
            
            return MTBF;
    }
            	
    @GetMapping("/typeLastMonth")
    public List<JSONObject> TypeLastMonthArret() {
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month < 10){
                mts = String.valueOf(year)+"/0"+ String.valueOf(month);
            System.out.println("ce mois: "+ mts);
            }else{
                mts = String.valueOf(year)+"/"+ String.valueOf(month);
            }
            List<JSONObject> Allpannes = arretRepository.ArretTypeMonth(mts);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> nbre = new ArrayList<>();
            List<JSONObject> tdth = new ArrayList<>();
            
            Allpannes.forEach(x -> {
                String t = x.get("cause").toString();
                if(!t.equals("Délestage") && !t.equals("Bourrage") && !t.equals("Manque de Bobine") 
                        && !t.equals("Manque de Personnel") && !t.equals("Manque de Matériel")){
                    type = "Divers";
                }else {
                    type = t;
                }
                response2.put("date", x.get("date"));
                response2.put("type", type);
                response2.put("nbre", x.get("nbre"));
                response2.put("TDT", x.get("TDT"));
                json = new JSONObject(response2);
                MTBF2.add(json);
            });
            
            Map<String, Integer> result = MTBF2.stream().collect(
            Collectors.groupingBy(e -> e.get("type").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("type", date.getKey());
            response2.put("nbre", date.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        Map<String, Double> tdtd = MTBF2.stream().collect(
            Collectors.groupingBy(f -> f.get("type").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("TDT")).doubleValue()))); 
        
        tdtd.entrySet().stream()
            .forEach(dates -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("type", dates.getKey());
            response2.put("TDT", dates.getValue().doubleValue());
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        
        nbre.forEach(n->{
            tdth.forEach(td->{
                if(n.get("type").toString().equals(td.get("type").toString())){
                    response2.put("type", n.get("type").toString());
                    response2.put("nbre", n.get("nbre").toString());
                    response2.put("TDT", td.get("TDT").toString());
                    json2 = new JSONObject(response2);
                    MTBF.add(json2);
                }
            });
        });
            
            return MTBF;
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
