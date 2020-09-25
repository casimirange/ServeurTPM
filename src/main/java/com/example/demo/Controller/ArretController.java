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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    LocalDate date1, date2, date ;
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
            	
    @GetMapping("/lastMonth")
    public List<JSONObject> showLAstMonthArret() {
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
            return arretRepository.ArretThisMonth(mts);
    }
            	
    @GetMapping("/thisYear")
    public List<JSONObject> showYearArret() {
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            return arretRepository.ArretYear(year);
    }
            	
    @GetMapping("/RangeArret")
    public List<JSONObject> showRangeArret(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2) {
            return arretRepository.ArretRange(date1, date2);
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
            	
    @GetMapping("/typeRange")
    public List<JSONObject> TypeRangeArret(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2) {
    
            List<JSONObject> Allpannes = arretRepository.ArretTypeRange(date1, date2);
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
            	
    @GetMapping("/hier")
    public List<JSONObject> showHierArret() {
        LocalDate date = LocalDate.now().minusDays(1);
        return arretRepository.ArretToday(date);
    }
        	
    @DeleteMapping("/{id}")
    public void deleArret(@PathVariable Long id) {
            arretService.deleteArret(id);
    }
    
    @GetMapping("/date_range")
    public List<JSONObject> test(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){
        
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = arretRepository.ArretLast30Days(date1, date2);
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
    
    @GetMapping("/dashLastMonth")
    public List<JSONObject> dashLastMonth(){
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
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = arretRepository.countMonthPanne(mts);
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
    
    @GetMapping("/dashThisMonth")
    public List<JSONObject> dashThisMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        if(month < 10){
            mts = String.valueOf(year)+"/0"+ String.valueOf(month+1);
        System.out.println("last month: "+ mts);
        }else{
            mts = String.valueOf(year)+"/"+ String.valueOf(month+1);
        }
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = arretRepository.countMonthPanne(mts);
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
    
    @GetMapping("/dashLAst30Day")
    public List<JSONObject> test(){
        date1 = LocalDate.now();
        date2 = date1.minusMonths(1);
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = arretRepository.ArretLast30Days(date2, date1);
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
    
    @GetMapping("/recapArret")
    public List<JSONObject> Recap(){
        List<JSONObject> pty = arretRepository.RecapPanne();
        List<JSONObject> MTBF = new ArrayList<>();            
        List<JSONObject> MTBF2 = new ArrayList<>();            
        Map<String,Object> response = new HashMap<>();
        
        Map<String, Object> response2 = new HashMap<>();
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
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", date.getKey());
            response2.put("nbre", String.valueOf(date.getValue()));
            json2 = new JSONObject(response2);
            nbre.add(json2);            
            });
        
        
        nbre.forEach(nb->{
                        String h = String.valueOf(nb.get("date"));
                        
//                        if(h.equals(m)){
                            response2.put("date", h);
                            response2.put("nbre", String.valueOf(nb.get("nbre").toString()));
                            json2 = new JSONObject(response2);
//                        }                        
            
            MTBF.add(json2);
        });
            System.out.println("final \n" + MTBF);
          
        
        for (int i = 0; i < MTBF.size()-1; i++){
            double x = (Integer.parseInt(MTBF.get(i).get("nbre").toString()));
            double y = (Integer.parseInt(MTBF.get(i+1).get("nbre").toString()));
            
//            response.put("annee", pty.get(i).get("annee"));
            response.put("date", MTBF.get(i).get("date"));
            response.put("nbre", MTBF.get(i).get("nbre"));
            if(x==0 && y==0)
            response.put("taux", 0);
            if(x==0 && y!=0)
            response.put("taux", -100);
            if(x!=0 && y==0)
            response.put("taux", 100);
            if(x!=0 && y!=0)
            response.put("taux", ((x/y)-1)*100);
            
            json = new JSONObject(response);
            MTBF2.add(json);
            
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        
        return MTBF2.subList(0, 2);
    }
    
    @GetMapping("/paretoAlpiRange")
    public List<JSONObject> ParetoThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

        List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = arretRepository.ParetoRange(date1, date2);

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

    @GetMapping("/paretoAlpiThisMonth")
    public List<JSONObject> ParetoThisMonth(){

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
            
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = arretRepository.ParetoThisMonth(mts);

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

    @GetMapping("/paretoAlpiLastMonth")
    public List<JSONObject> ParetoLastMonth(){

            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month < 10){
                mts = String.valueOf(year)+"/0"+ String.valueOf(month);
            System.out.println("this month: "+ mts);
            }else{
                mts = String.valueOf(year)+"/"+ String.valueOf(month);
            }         
            
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = arretRepository.ParetoThisMonth(mts);

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
}
