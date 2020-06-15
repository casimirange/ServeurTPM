/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

import com.example.demo.entity.Pannes;
import com.example.demo.reponses.CountPannesResponse;
import com.example.demo.reponses.DashboardResponse;
import com.example.demo.reponses.StatsRepose;
import com.example.demo.repository.DashboardRepository;
import com.example.demo.repository.PanneRepository;
import com.example.demo.service.PannesService;
import com.example.demo.service.inter.IPanneService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casimir
 */
@RestController 
@CrossOrigin
@RequestMapping(value = "/api/dashboard")
public class DashboardController {
    
    @Autowired
    private IPanneService panneService;
    @Autowired
    private PannesService ps;
    @Autowired
    private DashboardRepository dashboardRepository;

    LocalDate date1, date2 ;
    String mts, j, l, n  ;
    JSONObject json, json2;

    @GetMapping("/countPerDay")
    public List<DashboardResponse> countPerDay(){
        date1 = LocalDate.now();
        date2 = date1.minusMonths(1);
//        Calendar cal = Calendar.getInstance();
//            cal.setFirstDayOfWeek(0);
//            int month = cal.get(Calendar.MONTH);
//            int year = cal.get(Calendar.YEAR);
//            if(month < 10){
//                mts = String.valueOf(year)+"/0"+ String.valueOf(month);
//            System.out.println("ce mois: "+ mts);
//            }else{
//                mts = String.valueOf(year)+"/"+ String.valueOf(month);
//            }
        return dashboardRepository.countPerDay(date2, date1);
    }
    
    @GetMapping("/count")
    public List<CountPannesResponse> countAll(){
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
        return dashboardRepository.TotalLPannes(mts);
    }
    
    @GetMapping("/countPassMonth")
    public List<CountPannesResponse> countPassMonth(){
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month < 10){
                mts = String.valueOf(year)+"/0"+ String.valueOf(month);
            System.out.println("last month: "+ mts);
            }else{
                mts = String.valueOf(year)+"/"+ String.valueOf(month);
            }
        return dashboardRepository.TotalLPannes(mts);
    }  
    
    
    @GetMapping("/countThisYear")
    public List<JSONObject> countThisYear(){
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
                mts = String.valueOf(year);
        return dashboardRepository.countThisYear(mts);
    }
    
    @GetMapping
    public List<JSONObject> test(){
        date1 = LocalDate.now();
        date2 = date1.minusMonths(1);
        return dashboardRepository.test(date2, date1);
    }
    
    @GetMapping("/mdtByYearAlpi")
    public List<JSONObject> mdtByYearsAlpi(){
        return dashboardRepository.mdtByYear();
    }
    
    @GetMapping("/mdtThisYearAlpi")
    public List<JSONObject> mdtThisYearAlpi(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        return dashboardRepository.mdtThisYear(mt);
    }
    
    
    
    @GetMapping("/hourByYearAlpi")
    public List<JSONObject> hourByYearsAlpi(){
        return dashboardRepository.hourByYear();
    }
    
    @GetMapping("/hourThisYearAlpi")
    public List<JSONObject> hourThisYearAlpi(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        return dashboardRepository.hourThisYear(mt);
    }   
    
    
    @GetMapping("/arretByYearAlpi")
    public List<JSONObject> arretByYearsAlpi(){
        return dashboardRepository.arretByYear();
    }
    
    @GetMapping("/arretThisYearAlpi")
    public List<JSONObject> arretThisYearAlpi(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        return dashboardRepository.arretThisYear(mt);
    }
    
        @GetMapping("/p1")
    public List<JSONObject> test1(){
        return dashboardRepository.PByYear();
    }
    
    @GetMapping("/p2")
    public List<JSONObject> test2(){
        return dashboardRepository.hourByYear();
    }
    
    
//    @GetMapping("/mdtByYearAlpi")
//    public List<JSONObject> mdtByYearsAlpi(){
//        return dashboardRepository.mdtByYear();
//    }
    
    @GetMapping("/mtbfByYear")
    public List<JSONObject> MTBFTByYearAlpi(){        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> hby = dashboardRepository.hourByYear();
        List<JSONObject> pby = dashboardRepository.PByYear();
        List<JSONObject> aby = dashboardRepository.AByYear();
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         List<Map<String,String>> reponse = new ArrayList<>();
         
         List<JSONObject> test = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>();    
         
        hby.forEach(y->{
            pby.forEach(t-> {
                aby.forEach(a->{                   
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));                    
                   String ar = String.valueOf(a.get("date"));                   
                   //si date l'heure de fct = date panne
                 if(h.equals(m) && h.equals(ar) ) {
                     response.put("date", String.valueOf(y.get("date")));
                     response.put("nbre", String.valueOf(t.get("nbre")));
                     response.put("TDT", String.valueOf(t.get("TDT")));
                     response.put("HT", String.valueOf(y.get("HT"))); 
                     response.put("AT", String.valueOf(a.get("AT")));
                     json = new JSONObject(response);
                 }                
               });              
            });      
                  test.add(json);        
        });          
        return test;
    }
    
    @GetMapping("/mtbfThisYear")
    public List<JSONObject> MTBFThisYearAlpi(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = dashboardRepository.PThisYear(mt);
        List<JSONObject> hty = dashboardRepository.hourThisYear(mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         List<Map<String,String>> reponse = new ArrayList<>();
         
         List<JSONObject> test = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>();       
         

        hty.forEach(y->{
            mdtty.forEach(t-> {
                aty.forEach(a->{                   
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));                    
                   String ar = String.valueOf(a.get("date"));                   
                   //si date l'heure de fct = date panne
                 if(h.equals(m) && h.equals(ar) ) {
                     response.put("date", String.valueOf(y.get("date")));
                     response.put("nbre", String.valueOf(t.get("nbre")));
                     response.put("TDT", String.valueOf(t.get("TDT")));
                     response.put("HT", String.valueOf(y.get("HT"))); 
                     response.put("AT", String.valueOf(a.get("AT")));
                     json = new JSONObject(response);
                 }                
               });              
            });      
                  test.add(json);        
        });           
        
        return test;
    }
    
    @GetMapping("/heuresByYear")
    public List<JSONObject> HeureByYear(){
        return dashboardRepository.hourByYear();
    }
    
    @GetMapping("/heuresThisYear")
    public List<JSONObject> HeureThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        return dashboardRepository.hourThisYear(mt);
    }
    
}
