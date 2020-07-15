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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

    LocalDate date1, date2, date ;
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
    
    
    @GetMapping("/techWT")
    public List<JSONObject> teschStatsMonth(){
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
            
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = dashboardRepository.TechniciensStats(mts);
        List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         Map<String, Object> response2 = new HashMap<>();
         
         System.out.println("liste de tech:\n" + test);
         
        Map<String, Integer> result = test.stream().collect(
            Collectors.groupingBy(e -> e.get("tec").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("tec", date.getKey());
            response2.put("nbre", date.getValue());
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        Map<String, Double> tdtd = test.stream().collect(
            Collectors.groupingBy(f -> f.get("tec").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("WT")).doubleValue()))); 
        
        tdtd.entrySet().stream()
            .forEach(dates -> {
                System.out.println("test de date2 " + dates.getKey() + " = " + dates.getValue()); 
            response2.put("tec", dates.getKey());
            response2.put("WT", dates.getValue());
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        
        
        nbre.forEach(nb->{
            tdth.forEach(td->{
            test.forEach(tt->{
                        String h = String.valueOf(nb.get("tec"));
                        String m = String.valueOf(td.get("tec"));
                        String x = String.valueOf(tt.get("tec"));
                        String f = String.valueOf(tt.get("fonction")).substring(0, 4);
                        String fx = Normalizer.normalize(f, Normalizer.Form.NFD) ;
                        
                        if(h.equals(m) && h.equals(x)){
                            response2.put("tec", h);
                            response2.put("nbre", nb.get("nbre"));
                            response2.put("WT", td.get("WT"));
                            response2.put("Matricule", tt.get("matricule"));
                            response2.put("Fonction", fx.replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
                            json2 = new JSONObject(response2);
                        }
                        
            });
            });
            dash.add(json2);
        });
                        System.out.println("finalité \n" + dash);    
        return dash;
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
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = dashboardRepository.test(date2, date1);
        List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         Map<String,String> response2 = new HashMap<>();
         
         
        Map<String, Integer> result = test.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", date.getKey());
            response2.put("nbre", String.valueOf(date.getValue()));
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        Map<String, Double> tdtd = test.stream().collect(
            Collectors.groupingBy(f -> f.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("dt")).doubleValue()))); 
        
        tdtd.entrySet().stream()
            .forEach(dates -> {
                System.out.println("test de date " + dates.getKey() + " = " + dates.getValue()); 
            response2.put("date", dates.getKey());
            response2.put("dt", String.valueOf(dates.getValue()));
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        
        
        nbre.forEach(nb->{
            tdth.forEach(td->{
                        String h = String.valueOf(nb.get("date"));
                        String m = String.valueOf(td.get("date"));
                        
                        if(h.equals(m)){
                            response2.put("date", h);
                            response2.put("nbre", String.valueOf(nb.get("nbre")));
                            response2.put("dt", String.valueOf(td.get("dt")));
                            json2 = new JSONObject(response2);
                        }
                        
            });
            dash.add(json2);
        });
                        System.out.println("finalité \n" + dash);
        return dash;
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
        
        System.out.println("heures :\n" + hby);
        System.out.println("pannes :\n" + pby);
        System.out.println("arrets :\n" + aby);
        
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         List<Map<String,String>> reponse = new ArrayList<>();
         
         List<JSONObject> test = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>(); 
         List<JSONObject> test3 = new ArrayList<>(); 
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         LinkedHashSet<JSONObject> test7 = new LinkedHashSet<>();
         List<JSONObject> test6 = new ArrayList<>();
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", date.getKey());
            response2.put("nbre", String.valueOf(date.getValue()));
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        Map<String, Double> tdtd = pby.stream().collect(
            Collectors.groupingBy(f -> f.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("TDT")).doubleValue()))); 
        
        tdtd.entrySet().stream()
            .forEach(dates -> {
                System.out.println("test de date " + dates.getKey() + " = " + dates.getValue()); 
            response2.put("date", dates.getKey());
            response2.put("TDT", String.valueOf(dates.getValue()));
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        
        Map<String, Double> wt1d = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((BigDecimal)t.get("WT")).doubleValue()))); 
        
        wt1d.entrySet().stream()
            .forEach(datr -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", datr.getKey());
            response2.put("WT", String.valueOf(datr.getValue()));
            json2 = new JSONObject(response2);
            wth.add(json2);            
            });
        
        Map<String, Double> ttrs = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((BigDecimal)t.get("TTR")).doubleValue()))); 
        
        ttrs.entrySet().stream()
            .forEach(datee -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", datee.getKey());
            response2.put("TTR", String.valueOf(datee.getValue()));
            json2 = new JSONObject(response2);
            ttrh.add(json2);            
            });
        
        nbre.forEach(nb->{
            tdth.forEach(td->{
                wth.forEach(wts->{
                    ttrh.forEach(tt->{
                        String h = String.valueOf(nb.get("date"));
                        String m = String.valueOf(td.get("date"));
                        String x = String.valueOf(wts.get("date"));
                        String y = String.valueOf(tt.get("date"));
                        
                        if(h.equals(m) && h.equals(x) && h.equals(y)){
                            response2.put("date", h);
                            response2.put("nbre", String.valueOf(nb.get("nbre")));
                            response2.put("TDT", String.valueOf(td.get("TDT")));
                            response2.put("WT", String.valueOf(wts.get("WT")));
                            response2.put("TTR", String.valueOf(tt.get("TTR")));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
            for(int i = 0; i< hby.size(); i++){
                response2.put("date", String.valueOf(hby.get(i).get("date")));
                response2.put("HT", String.valueOf(hby.get(i).get("HT")));
                response2.put("nbre", "0");
                response2.put("TDT", "0");                    
                response2.put("WT", "0");
                response2.put("TTR", "0");
                json2 = new JSONObject(response2);
            test3.add(json2);
        } 
         
        hby.forEach(y->{
            MTBF.forEach(t-> {
                aby.forEach(a->{                   
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));                    
                   String ar = String.valueOf(a.get("date"));                   
                   //si date l'heure de fct = date panne
                 if(h.equals(m) && h.equals(ar) ) {
                     response.put("date", String.valueOf(y.get("date")));
                     response.put("nbre", String.valueOf(t.get("nbre")));
                     response.put("TDT", String.valueOf(t.get("TDT")));
                     response.put("WT", String.valueOf(t.get("WT")));
                     response.put("TTR", String.valueOf(t.get("TTR")));
                     response.put("HT", String.valueOf(y.get("HT"))); 
                     response.put("AT", String.valueOf(a.get("AT")));
                     json = new JSONObject(response);
                 }                
               });              
            });      
                  test.add(json);        
        });          
        return test;
//        return hby;
    }
    
    @GetMapping("/mtbfThisYear")
    public List<JSONObject> MTBFThisYearAlpi(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = dashboardRepository.PThisYear(mt);
        List<JSONObject> hty = dashboardRepository.hourThisYear(mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         List<Map<String,String>> reponse = new ArrayList<>();
         
         List<JSONObject> test = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>(); 
         List<JSONObject> test3 = new ArrayList<>(); 
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();         
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", date.getKey());
            response2.put("nbre", String.valueOf(date.getValue()));
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        Map<String, Double> tdtd = pty.stream().collect(
            Collectors.groupingBy(f -> f.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("TDT")).doubleValue()))); 
        
        tdtd.entrySet().stream()
            .forEach(dates -> {
                System.out.println("test de date " + dates.getKey() + " = " + dates.getValue()); 
            response2.put("date", dates.getKey());
            response2.put("TDT", String.valueOf(dates.getValue()));
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        
        Map<String, Double> wt1d = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((BigDecimal)t.get("WT")).doubleValue()))); 
        
        wt1d.entrySet().stream()
            .forEach(datr -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", datr.getKey());
            response2.put("WT", String.valueOf(datr.getValue()));
            json2 = new JSONObject(response2);
            wth.add(json2);            
            });
        
        Map<String, Double> ttrs = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((BigDecimal)t.get("TTR")).doubleValue()))); 
        
        ttrs.entrySet().stream()
            .forEach(datee -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", datee.getKey());
            response2.put("TTR", String.valueOf(datee.getValue()));
            json2 = new JSONObject(response2);
            ttrh.add(json2);            
            });
        
        nbre.forEach(nb->{
            tdth.forEach(td->{
                wth.forEach(wts->{
                    ttrh.forEach(tt->{
                        String h = String.valueOf(nb.get("date"));
                        String m = String.valueOf(td.get("date"));
                        String x = String.valueOf(wts.get("date"));
                        String y = String.valueOf(tt.get("date"));
                        
                        if(h.equals(m) && h.equals(x) && h.equals(y)){
                            response2.put("date", h);
                            response2.put("nbre", String.valueOf(nb.get("nbre")));
                            response2.put("TDT", String.valueOf(td.get("TDT")));
                            response2.put("WT", String.valueOf(wts.get("WT")));
                            response2.put("TTR", String.valueOf(tt.get("TTR")));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
        

        hty.forEach(y->{
            MTBF.forEach(t-> {
                aty.forEach(a->{                   
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));                    
                   String ar = String.valueOf(a.get("date"));                   
                   //si date l'heure de fct = date panne
                 if(h.equals(m) && h.equals(ar) ) {
                     response.put("date", String.valueOf(y.get("date")));
                     response.put("nbre", String.valueOf(t.get("nbre")));
                     response.put("TDT", String.valueOf(t.get("TDT")));
                     response.put("WT", String.valueOf(t.get("WT")));
                     response.put("TTR", String.valueOf(t.get("TTR")));
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
