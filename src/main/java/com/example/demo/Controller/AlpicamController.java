/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

import com.example.demo.repository.AlpicamRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casimir
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin/alpicam")
public class AlpicamController {
    @Autowired
    private AlpicamRepository alpicamRepository;
    
    String mts;
    JSONObject json, json2, json3, json4;
    LocalDate date1, date2 ;
    
    @GetMapping("/typePanneThisYear")
    public List<JSONObject> countThisYear(){
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
                mts = String.valueOf(year);
        return alpicamRepository.typePanne(mts);
    }
    
    @GetMapping("/alpiThisYear")
    public List<JSONObject> alpiThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsAlpi(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/alpiLastYear")
    public List<JSONObject> alpiLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsAlpi(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/placageThisYear")
    public List<JSONObject> placageThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsPlacage(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/placageLastYear")
    public List<JSONObject> placageLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsPlacage(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/brazilThisYear")
    public List<JSONObject> brazilThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsBrazil(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/brazilLastYear")
    public List<JSONObject> brazilLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsBrazil(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/CPThisYear")
    public List<JSONObject> CPThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsCP(date1, date2);
        System.out.println("date1:\n" + date1 + "autre "+ date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/CPLastYear")
    public List<JSONObject> CPLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsCP(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/scierieThisYear")
    public List<JSONObject> scierieThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsScierie(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("date", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("date", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/scierieLastYear")
    public List<JSONObject> scierieLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsScierie(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ligne1ThisYear")
    public List<JSONObject> ligne1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsLigne1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ligne1LastYear")
    public List<JSONObject> ligne1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsLigne1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ligne2ThisYear")
    public List<JSONObject> ligne2ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsLigne2(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ligne2LastYear")
    public List<JSONObject> ligne2LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsLigne2(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ligne3ThisYear")
    public List<JSONObject> ligne3ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsLigne3(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ligne3LastYear")
    public List<JSONObject> ligne3LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsLigne3(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechoirsThisYear")
    public List<JSONObject> sechoirsThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSechoirs(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechoirsLastYear")
    public List<JSONObject> sechoirsLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSechoirs(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/jointageThisYear")
    public List<JSONObject> jointageThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsJointage(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/jointageLastYear")
    public List<JSONObject> jointageLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsJointage(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/tapisDechetsThisYear")
    public List<JSONObject> tapisDechetsThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTapisDéchets(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/tapisDechetsLastYear")
    public List<JSONObject> tapisDechetsLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTapisDéchets(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ecorcageThisYear")
    public List<JSONObject> ecorçageThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEcorçage(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/ecorcageLastYear")
    public List<JSONObject> ecorçageLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEcorçage(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encollageBrazilThisYear")
    public List<JSONObject> EncollageBrazilThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEncollageBazil(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encollageBrazilLastYear")
    public List<JSONObject> EncollageBrazilLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEncollageBazil(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/tranchageThisYear")
    public List<JSONObject> tranchageThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTranchage(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/tranchageLastYear")
    public List<JSONObject> tranchageLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTranchage(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/derouleuse1ThisYear")
    public List<JSONObject> derouleuse1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsD1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/derouleuse1LastYear")
    public List<JSONObject> derouleuse1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsD1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/derouleuse2ThisYear")
    public List<JSONObject> derouleuse2ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsD2(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/derouleuse2LastYear")
    public List<JSONObject> derouleuse2LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsD2(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/derouleuse3ThisYear")
    public List<JSONObject> derouleuse3ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsD3(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/derouleuse3LastYear")
    public List<JSONObject> derouleuse3LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsD3(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/bobineuse1ThisYear")
    public List<JSONObject> bobineuse1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsB1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/bobineuse1LastYear")
    public List<JSONObject> bobineuse1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsB1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/bobineuse2ThisYear")
    public List<JSONObject> bobineuse2ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsB2(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/bobineuse2LastYear")
    public List<JSONObject> bobineuse2LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsB2(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/bobineuse3ThisYear")
    public List<JSONObject> bobineuse3ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsB3(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/bobineuse3LastYear")
    public List<JSONObject> bobineuse3LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsB3(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/magbob1ThisYear")
    public List<JSONObject> magBob1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMB1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/magbob1LastYear")
    public List<JSONObject> magBob1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMB1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/magbob2ThisYear")
    public List<JSONObject> magBob2ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMB2(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/magbob2LastYear")
    public List<JSONObject> magBob2LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMB2(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/magbob3ThisYear")
    public List<JSONObject> magBob3ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMB3(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/magbob3LastYear")
    public List<JSONObject> magBob3LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMB3(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massEZ1ThisYear")
    public List<JSONObject> massEZ1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMEZ1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massEZ1LastYear")
    public List<JSONObject> massEZ1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMEZ1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massENThisYear")
    public List<JSONObject> massENThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMassEN(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massENLastYear")
    public List<JSONObject> massENLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMassEN(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massEZ3ThisYear")
    public List<JSONObject> massEZ3ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMEZ3(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massEZ3LastYear")
    public List<JSONObject> massEZ3LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMEZ3(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massEZ4ThisYear")
    public List<JSONObject> massEZ4ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMEZ4(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massEZ4LastYear")
    public List<JSONObject> massEZ4LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMEZ4(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massAThisYear")
    public List<JSONObject> massAThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMassA(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massALastYear")
    public List<JSONObject> massALastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMassA(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massBThisYear")
    public List<JSONObject> massBThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMassB(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/massBLastYear")
    public List<JSONObject> massBLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsMassB(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ1ThisYear")
    public List<JSONObject> sechEZ1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ1LastYear")
    public List<JSONObject> sechEZ1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ2ThisYear")
    public List<JSONObject> sechEZ2ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ2(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ2LastYear")
    public List<JSONObject> sechEZ2LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ2(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ3ThisYear")
    public List<JSONObject> sechEZ3ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ3(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ3LastYear")
    public List<JSONObject> sechEZ3LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ3(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ4ThisYear")
    public List<JSONObject> sechEZ4ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ4(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechEZ4LastYear")
    public List<JSONObject> sechEZ4LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEZ4(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechENThisYear")
    public List<JSONObject> sechENThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEN(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechENLastYear")
    public List<JSONObject> sechENLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeEN(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechER24ThisYear")
    public List<JSONObject> sechER24ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeER24(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/sechER24LastYear")
    public List<JSONObject> sechER24LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSeER24(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/scieBongThisYear")
    public List<JSONObject> scieBongThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSBong(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/scieBongLastYear")
    public List<JSONObject> scieBongLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsSBong(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1BrazilThisYear")
    public List<JSONObject> encolleuse1BrazilThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEnc1b(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1BrazilLastYear")
    public List<JSONObject> encolleuse1BrazilLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEnc1b(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2BrazilThisYear")
    public List<JSONObject> encolleuse2BrazilThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEnc2b(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2BrazilLastYear")
    public List<JSONObject> encolleuse2BrazilLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEnc2b(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3BrazilThisYear")
    public List<JSONObject> encolleuse3BrazilThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEnc3b(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3BrazilLastYear")
    public List<JSONObject> encolleuse3BrazilLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEnc3b(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1CPThisYear")
    public List<JSONObject> encolleuse1CPThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEN1cp(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1CPLastYear")
    public List<JSONObject> encolleuse1CPLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEN1cp(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2CPThisYear")
    public List<JSONObject> encolleuse2CPThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEN2cp(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2CPLastYear")
    public List<JSONObject> encolleuse2CPLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEN2cp(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3CPThisYear")
    public List<JSONObject> encolleuse3CPThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEN3cp(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3CPLastYear")
    public List<JSONObject> encolleuse3CPLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsEN3cp(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/presseTeteThisYear")
    public List<JSONObject> presseTeteThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsPressTete(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/presseTeteLastYear")
    public List<JSONObject> presseTeteLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsPressTete(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/trancheuse1ThisYear")
    public List<JSONObject> trancheuse1ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTrancheuse1(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/trancheuse1LastYear")
    public List<JSONObject> trancheuse1LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTrancheuse1(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/trancheuse2ThisYear")
    public List<JSONObject> trancheuse2ThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTrancheuse2(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/trancheuse2LastYear")
    public List<JSONObject> trancheuse2LastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsTrancheuse2(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/presseSimiThisYear")
    public List<JSONObject> presseSimiThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now();
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsPresseSimi(date1, date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
    @GetMapping("/presseSimiLastYear")
    public List<JSONObject> presseSimiLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        date1 = LocalDate.of(year - 1, Month.JANUARY, 01);
        System.out.println("date1:\n" + date1);
        date2 = LocalDate.now().minusYears(1);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> pby = alpicamRepository.statsPresseSimi(date1, date2);
        System.out.println("date1:\n" + date1);
        System.out.println("date2:\n" + date2);
        System.out.println("cette année:\n" + pby);
         Map<String, Object> response2 = new HashMap<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         
         if(!pby.isEmpty()){
             
         
         
        Map<String, Integer> result = pby.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
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
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            response2.put("TDT", String.valueOf(td.get("TDT").toString()));
                            response2.put("WT", String.valueOf(wts.get("WT").toString()));
                            response2.put("TTR", String.valueOf(tt.get("TTR").toString()));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
            
            int n = 0;
            double w = 0;
            double dt = 0;
            double tr = 0;
            String date = "";
            
            for(int i = 0; i<MTBF.size(); i++){
                date = MTBF.get(i).get("date").toString();
                n += Integer.parseInt(MTBF.get(i).get("nbre").toString());
                w += Double.parseDouble(MTBF.get(i).get("WT").toString());
                dt += Double.parseDouble(MTBF.get(i).get("TDT").toString());
                tr += Double.parseDouble(MTBF.get(i).get("TTR").toString());
            }
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", n);
            response2.put("TDT", dt);
            response2.put("WT", w);
            response2.put("TTR", tr);
            json = new JSONObject(response2);
            MTBF2.add(json);
            
            }else{
            response2.put("dates", date2.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json = new JSONObject(response2);
            MTBF2.add(json);
         }
            
        return MTBF2;
    }
    
 
    @GetMapping("/paretoAlpiYear/{dep}")
    public List<JSONObject> ParetoThisYear(@PathVariable Long dep){

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        mts = String.valueOf(year);                      

        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> pty = alpicamRepository.ParetoYear(dep, mts);

        Map<String,Object> response = new HashMap<>();
     Map<String,String> response2 = new HashMap<>();

     List<JSONObject> nbre = new ArrayList<>();
     List<JSONObject> tdth = new ArrayList<>();

    Map<String, Integer> result = pty.stream().collect(
        Collectors.groupingBy(e -> e.get("nom").toString(),
        LinkedHashMap::new,
        Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 

    result.entrySet().stream()
        .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
        response2.put("nom", date.getKey());
        response2.put("nbre", String.valueOf(date.getValue()));
        json2 = new JSONObject(response2);
        nbre.add(json2);            
        });

    System.out.println("nbre\n " + nbre);

    Map<String, Double> tdtd = pty.stream().collect(
        Collectors.groupingBy(f -> f.get("nom").toString(),
        LinkedHashMap::new,
        Collectors.summingDouble(g -> ((BigDecimal)g.get("TDT")).doubleValue()))); 

    tdtd.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .forEach(dates -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
        response2.put("nom", dates.getKey());
        response2.put("TDT", String.valueOf(dates.getValue()));
        json2 = new JSONObject(response2);
        tdth.add(json2);            
        });
    System.out.println("trdt\n " + tdth);

    tdth.forEach(nb->{
        nbre.forEach(td->{
                    String h = String.valueOf(nb.get("nom"));
                    String m = String.valueOf(td.get("nom"));

                    if(h.equals(m) ){
                        response.put("nom", h);
                        response.put("nbre", String.valueOf(td.get("nbre")));
                        response.put("TDT", String.valueOf(nb.get("TDT")));
                        json2 = new JSONObject(response);
                    }
        });
        MTBF.add(json2);
    });
                    System.out.println("final \n" + MTBF);

        return MTBF;
    }

    @GetMapping("/paretoAlpiThisMonth/{dep}")
    public List<JSONObject> ParetoThisMonth(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month < 10){
                mts = String.valueOf(year)+"/0"+ String.valueOf(month+1);
            System.out.println("this month: "+ mts);
            }else{
                mts = String.valueOf(year)+"/"+ String.valueOf(month+1);
            }                     

            List<JSONObject> pty = alpicamRepository.ParetoThisMonth(dep, mts);
            List<JSONObject> MTBF = new ArrayList<>();
            
            Map<String,Object> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         
         List<JSONObject> test3 = new ArrayList<>();
         LinkedHashSet<JSONObject> test0 = new LinkedHashSet<>();
         List<JSONObject> test4 = new ArrayList<>(); 
         LinkedHashSet<JSONObject> test1 = new LinkedHashSet<>(); 
         List<JSONObject> test5 = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>();
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         LinkedHashSet<JSONObject> test7 = new LinkedHashSet<>();
         List<JSONObject> test6 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", String.valueOf(date.getValue()));
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        System.out.println("nbre\n " + nbre);
        
        Map<String, Double> tdtd = pty.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((BigDecimal)g.get("TDT")).doubleValue()))); 
        
        tdtd.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates.getKey());
            response2.put("TDT", String.valueOf(dates.getValue()));
            json2 = new JSONObject(response2);
            tdth.add(json2);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("TDT", String.valueOf(nb.get("TDT")));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                    
            return MTBF;            
        }
}