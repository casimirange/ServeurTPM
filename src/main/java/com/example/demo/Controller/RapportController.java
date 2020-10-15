/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casimir
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin/rapport")
public class RapportController {
    JSONObject json;
    
    @Autowired
    AlpicamController alpi = new AlpicamController();   
    
    
    
    @GetMapping("/alpicam")
    public List<JSONObject> Alpi(){
        List<JSONObject> alty = alpi.alpiThisYear();
    List<JSONObject> ally = alpi.alpiLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Alpicam");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/alpicamRange")
    public List<JSONObject> AlpiRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.alpiThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.alpiLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Alpicam");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/brazil")
    public List<JSONObject> Brazil(){
        List<JSONObject> alty = alpi.brazilThisYear();
    List<JSONObject> ally = alpi.brazilLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Brazil");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/brazilRange")
    public List<JSONObject> BrazilRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.brazilThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.brazilLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Brazil");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/contreplaque")
    public List<JSONObject> Contreplaque(){
        List<JSONObject> alty = alpi.CPThisYear();
    List<JSONObject> ally = alpi.CPLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Contreplaqué");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/contreplaqueRange")
    public List<JSONObject> ContreplaqueRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.CPThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.CPLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Contreplaqué");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/placage")
    public List<JSONObject> Placage(){
        List<JSONObject> alty = alpi.placageThisYear();
    List<JSONObject> ally = alpi.placageLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Placage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/placageRange")
    public List<JSONObject> PlacageRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.placageThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.placageLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Placage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/scierie")
    public List<JSONObject> Scierie(){
        List<JSONObject> alty = alpi.scierieThisYear();
    List<JSONObject> ally = alpi.scierieLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Scierie");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/scierieRange")
    public List<JSONObject> ScierieRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.scierieThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.scierieLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Scierie");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ridotto")
    public List<JSONObject> Ridotto(){
        List<JSONObject> ridotto = new ArrayList<>();
        ridotto.addAll(Alpi());
        ridotto.addAll(Brazil());
        ridotto.addAll(Contreplaque());
        ridotto.addAll(Placage());
        ridotto.addAll(Scierie());
        return ridotto;
    }    
    
    @GetMapping("/ridottoRange")
    public List<JSONObject> RidottoRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> ridotto = new ArrayList<>();
        ridotto.addAll(AlpiRange(date1, date2, date3, date4));
        ridotto.addAll(BrazilRange(date1, date2, date3, date4));
        ridotto.addAll(ContreplaqueRange(date1, date2, date3, date4));
        ridotto.addAll(PlacageRange(date1, date2, date3, date4));
        ridotto.addAll(ScierieRange(date1, date2, date3, date4));
        return ridotto;
    }    
    
    @GetMapping("/ligne1")
    public List<JSONObject> Ligne1(){
        List<JSONObject> alty = alpi.ligne1ThisYear();
    List<JSONObject> ally = alpi.ligne1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ligne1Range")
    public List<JSONObject> Ligne1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.ligne1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.ligne1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ligne2")
    public List<JSONObject> Ligne2(){
        List<JSONObject> alty = alpi.ligne2ThisYear();
    List<JSONObject> ally = alpi.ligne2LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ligne2Range")
    public List<JSONObject> Ligne2Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
    List<JSONObject> alty = alpi.ligne2ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.ligne2LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ligne3")
    public List<JSONObject> Ligne3(){
        List<JSONObject> alty = alpi.ligne3ThisYear();
    List<JSONObject> ally = alpi.ligne3LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ligne3Range")
    public List<JSONObject> Ligne3Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.ligne3ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.ligne3LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirs")
    public List<JSONObject> Sechoirs(){
        List<JSONObject> alty = alpi.sechoirsThisYear();
    List<JSONObject> ally = alpi.sechoirsLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoirs");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirsRange")
    public List<JSONObject> SechoirsRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechoirsThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechoirsLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoirs");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ecorcage")
    public List<JSONObject> Ecorçage(){
        List<JSONObject> alty = alpi.ecorçageThisYear();
    List<JSONObject> ally = alpi.ecorçageLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ecorçage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ecorcageRange")
    public List<JSONObject> EcorçageRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.ecorçageThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.ecorçageLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ecorçage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/tapisDechets")
    public List<JSONObject> TapisDechets(){
        List<JSONObject> alty = alpi.tapisDechetsThisYear();
    List<JSONObject> ally = alpi.tapisDechetsLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Tapis Déchets");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/tapisDechetsRange")
    public List<JSONObject> TapisDechetsRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.tapisDechetsThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.tapisDechetsLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Tapis Déchets");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/derouleuse1")
    public List<JSONObject> Derouleuse1(){
        List<JSONObject> alty = alpi.derouleuse1ThisYear();
    List<JSONObject> ally = alpi.derouleuse1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Dérouleuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/derouleuse1Range")
    public List<JSONObject> Derouleuse1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.derouleuse1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.derouleuse1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Dérouleuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/derouleuse2")
    public List<JSONObject> Derouleuse2(){
        List<JSONObject> alty = alpi.derouleuse2ThisYear();
    List<JSONObject> ally = alpi.derouleuse2LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Dérouleuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/derouleuse2Range")
    public List<JSONObject> Derouleuse2Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.derouleuse2ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.derouleuse2LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Dérouleuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/derouleuse3")
    public List<JSONObject> Derouleuse3(){
        List<JSONObject> alty = alpi.derouleuse3ThisYear();
    List<JSONObject> ally = alpi.derouleuse3LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Dérouleuse 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/derouleuse3Range")
    public List<JSONObject> Derouleuse3Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.derouleuse3ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.derouleuse3LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Dérouleuse 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/bobineuse3")
    public List<JSONObject> Bobineuse3(){
        List<JSONObject> alty = alpi.bobineuse3ThisYear();
    List<JSONObject> ally = alpi.bobineuse3LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Bobineuse 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/bobineuse3Range")
    public List<JSONObject> Bobineuse3Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.bobineuse3ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.bobineuse3LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Bobineuse 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/bobineuse2")
    public List<JSONObject> Bobineuse2(){
        List<JSONObject> alty = alpi.bobineuse2ThisYear();
    List<JSONObject> ally = alpi.bobineuse2LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Bobineuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/bobineuse2Range")
    public List<JSONObject> Bobineuse2Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.bobineuse2ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.bobineuse2LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Bobineuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/bobineuse1")
    public List<JSONObject> Bobineuse1(){
        List<JSONObject> alty = alpi.bobineuse1ThisYear();
    List<JSONObject> ally = alpi.bobineuse1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Bobineuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/bobineuse1Range")
    public List<JSONObject> Bobineuse1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.bobineuse1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.bobineuse1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Bobineuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/MagBob1")
    public List<JSONObject> MagBob1(){
        List<JSONObject> alty = alpi.magBob1ThisYear();
    List<JSONObject> ally = alpi.magBob1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Mag. Bob. 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/MagBob1Range")
    public List<JSONObject> MagBob1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.magBob1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.magBob1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Mag. Bob. 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/MagBob2")
    public List<JSONObject> MagBob2(){
        List<JSONObject> alty = alpi.magBob2ThisYear();
    List<JSONObject> ally = alpi.magBob2LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Mag. Bob. 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/MagBob2Range")
    public List<JSONObject> MagBob2Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.magBob2ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.magBob2LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Mag. Bob. 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/MagBob3")
    public List<JSONObject> MagBob3(){
        List<JSONObject> alty = alpi.magBob3ThisYear();
    List<JSONObject> ally = alpi.magBob3LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Mag. Bob. 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/MagBob3Range")
    public List<JSONObject> MagBob3Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.magBob3ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.magBob3LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Mag. Bob. 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotA")
    public List<JSONObject> MassicotA(){
        List<JSONObject> alty = alpi.massAThisYear();
    List<JSONObject> ally = alpi.massALastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot A");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotARange")
    public List<JSONObject> MassicotARange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.massAThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.massALastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot A");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotB")
    public List<JSONObject> MassicotB(){
        List<JSONObject> alty = alpi.massBThisYear();
    List<JSONObject> ally = alpi.massBLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot B");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotBRange")
    public List<JSONObject> MassicotBRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.massBThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.massBLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot B");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEN")
    public List<JSONObject> MassicotEN(){
        List<JSONObject> alty = alpi.massENThisYear();
    List<JSONObject> ally = alpi.massENLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EN");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotENRange")
    public List<JSONObject> MassicotENRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.massENThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.massENLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EN");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEZ1")
    public List<JSONObject> MassicotEZ1(){
        List<JSONObject> alty = alpi.massEZ1ThisYear();
    List<JSONObject> ally = alpi.massEZ1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EZ1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEZ1Range")
    public List<JSONObject> MassicotEZ1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.massEZ1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.massEZ1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EZ1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEZ3")
    public List<JSONObject> MassicotEZ3(){
        List<JSONObject> alty = alpi.massEZ3ThisYear();
    List<JSONObject> ally = alpi.massEZ3LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EZ3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEZ3Range")
    public List<JSONObject> MassicotEZ3Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.massEZ3ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.massEZ3LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EZ3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEZ4")
    public List<JSONObject> MassicotEZ4(){
        List<JSONObject> alty = alpi.massEZ4ThisYear();
    List<JSONObject> ally = alpi.massEZ4LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EZ4");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/massicotEZ4Range")
    public List<JSONObject> MassicotEZ4Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.massEZ4ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.massEZ4LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Massicot EZ4");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ1")
    public List<JSONObject> SechoirEZ1(){
        List<JSONObject> alty = alpi.sechEZ1ThisYear();
    List<JSONObject> ally = alpi.sechEZ1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ1Range")
    public List<JSONObject> SechoirEZ1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechEZ1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechEZ1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ2")
    public List<JSONObject> SechoirEZ2(){
        List<JSONObject> alty = alpi.sechEZ2ThisYear();
    List<JSONObject> ally = alpi.sechEZ2LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ2Range")
    public List<JSONObject> SechoirEZ2Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechEZ2ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechEZ2LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ3")
    public List<JSONObject> SechoirEZ3(){
        List<JSONObject> alty = alpi.sechEZ3ThisYear();
    List<JSONObject> ally = alpi.sechEZ3LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ3Range")
    public List<JSONObject> SechoirEZ3Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechEZ3ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechEZ3LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ4")
    public List<JSONObject> SechoirEZ4(){
        List<JSONObject> alty = alpi.sechEZ4ThisYear();
    List<JSONObject> ally = alpi.sechEZ4LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ4");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEZ4Range")
    public List<JSONObject> SechoirEZ4Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechEZ4ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechEZ4LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EZ4");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirEN")
    public List<JSONObject> SechoirEN(){
        List<JSONObject> alty = alpi.sechENThisYear();
    List<JSONObject> ally = alpi.sechENLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EN");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirENRange")
    public List<JSONObject> SechoirENRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechENThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechENLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir EN");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirER24")
    public List<JSONObject> SechoirER24(){
        List<JSONObject> alty = alpi.sechER24ThisYear();
    List<JSONObject> ally = alpi.sechER24LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir ER24");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/sechoirER24Range")
    public List<JSONObject> SechoirER24Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.sechER24ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.sechER24LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Sechoir ER24");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ridotto/placage")
    public List<JSONObject> RapportPlacage(){
        List<JSONObject> placage = new ArrayList<>();
        placage.addAll(Placage());
        placage.addAll(Ecorçage());
        placage.addAll(TapisDechets());
        placage.addAll(Ligne1());
        placage.addAll(Derouleuse1());
        placage.addAll(Bobineuse1());
        placage.addAll(MagBob1());
        placage.addAll(MassicotEN());
        placage.addAll(MassicotEZ1());
        placage.addAll(Ligne2());
        placage.addAll(Derouleuse2());
        placage.addAll(Bobineuse2());
        placage.addAll(MagBob2());
        placage.addAll(MassicotA());
        placage.addAll(MassicotB());
        placage.addAll(Ligne3());
        placage.addAll(Derouleuse3());
        placage.addAll(Bobineuse3());
        placage.addAll(MagBob3());
        placage.addAll(MassicotEZ3());
        placage.addAll(MassicotEZ4());
        placage.addAll(Sechoirs());
        placage.addAll(SechoirEN());
        placage.addAll(SechoirEZ1());
        placage.addAll(SechoirEZ2());
        placage.addAll(SechoirEZ3());
        placage.addAll(SechoirEZ4());
        placage.addAll(SechoirER24());
        return placage;
    }
    
    @GetMapping("/ridotto/placageRange")
    public List<JSONObject> RapportPlacageRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> placage = new ArrayList<>();
        placage.addAll(PlacageRange(date1, date2, date3, date4));
        placage.addAll(EcorçageRange(date1, date2, date3, date4));
        placage.addAll(TapisDechetsRange(date1, date2, date3, date4));
        placage.addAll(Ligne1Range(date1, date2, date3, date4));
        placage.addAll(Derouleuse1Range(date1, date2, date3, date4));
        placage.addAll(Bobineuse1Range(date1, date2, date3, date4));
        placage.addAll(MagBob1Range(date1, date2, date3, date4));
        placage.addAll(MassicotENRange(date1, date2, date3, date4));
        placage.addAll(MassicotEZ1Range(date1, date2, date3, date4));
        placage.addAll(Ligne2Range(date1, date2, date3, date4));
        placage.addAll(Derouleuse2Range(date1, date2, date3, date4));
        placage.addAll(Bobineuse2Range(date1, date2, date3, date4));
        placage.addAll(MagBob2Range(date1, date2, date3, date4));
        placage.addAll(MassicotARange(date1, date2, date3, date4));
        placage.addAll(MassicotBRange(date1, date2, date3, date4));
        placage.addAll(Ligne3Range(date1, date2, date3, date4));
        placage.addAll(Derouleuse3Range(date1, date2, date3, date4));
        placage.addAll(Bobineuse3Range(date1, date2, date3, date4));
        placage.addAll(MagBob3Range(date1, date2, date3, date4));
        placage.addAll(MassicotEZ3Range(date1, date2, date3, date4));
        placage.addAll(MassicotEZ4Range(date1, date2, date3, date4));
        placage.addAll(SechoirsRange(date1, date2, date3, date4));
        placage.addAll(SechoirENRange(date1, date2, date3, date4));
        placage.addAll(SechoirEZ1Range(date1, date2, date3, date4));
        placage.addAll(SechoirEZ2Range(date1, date2, date3, date4));
        placage.addAll(SechoirEZ3Range(date1, date2, date3, date4));
        placage.addAll(SechoirEZ4Range(date1, date2, date3, date4));
        placage.addAll(SechoirER24Range(date1, date2, date3, date4));
        return placage;
    }
    
    
    @GetMapping("/encollageBrazil")
    public List<JSONObject> EncollageBrazil(){
        List<JSONObject> alty = alpi.EncollageBrazilThisYear();
    List<JSONObject> ally = alpi.EncollageBrazilLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne Encollage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encollageBrazilRange")
    public List<JSONObject> EncollageBrazilRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.EncollageBrazilThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.EncollageBrazilLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Ligne Encollage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1Brazil")
    public List<JSONObject> Encolleuse1Brazil(){
        List<JSONObject> alty = alpi.encolleuse1BrazilThisYear();
    List<JSONObject> ally = alpi.encolleuse1BrazilLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1BrazilRange")
    public List<JSONObject> Encolleuse1BrazilRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.encolleuse1BrazilThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.encolleuse1BrazilLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2Brazil")
    public List<JSONObject> Encolleuse2Brazil(){
        List<JSONObject> alty = alpi.encolleuse2BrazilThisYear();
    List<JSONObject> ally = alpi.encolleuse2BrazilLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2BrazilRange")
    public List<JSONObject> Encolleuse2BrazilRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.encolleuse2BrazilThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.encolleuse2BrazilLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3Brazil")
    public List<JSONObject> Encolleuse3Brazil(){
        List<JSONObject> alty = alpi.encolleuse3BrazilThisYear();
    List<JSONObject> ally = alpi.encolleuse3BrazilLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3BrazilRange")
    public List<JSONObject> Encolleuse3BrazilRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.encolleuse3BrazilThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.encolleuse3BrazilLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 3");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/scieBongBrazil")
    public List<JSONObject> ScieBongioanni(){
        List<JSONObject> alty = alpi.scieBongThisYear();
    List<JSONObject> ally = alpi.scieBongLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Scie Bongioanni");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/scieBongBrazilRange")
    public List<JSONObject> ScieBongioanniRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.scieBongThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.scieBongLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Scie Bongioanni");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/trancheuse1")
    public List<JSONObject> Trancheuse1(){
        List<JSONObject> alty = alpi.trancheuse1ThisYear();
    List<JSONObject> ally = alpi.trancheuse1LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Trancheuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/trancheuse1Range")
    public List<JSONObject> Trancheuse1Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.trancheuse1ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.trancheuse1LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Trancheuse 1");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/trancheuse2")
    public List<JSONObject> Trancheuse2(){
        List<JSONObject> alty = alpi.trancheuse2ThisYear();
    List<JSONObject> ally = alpi.trancheuse2LastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Trancheuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/trancheuse2Range")
    public List<JSONObject> Trancheuse2Range(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.trancheuse2ThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.trancheuse2LastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Trancheuse 2");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/tranchage")
    public List<JSONObject> Tranchage(){
        List<JSONObject> alty = alpi.tranchageThisYear();
    List<JSONObject> ally = alpi.tranchageLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Tranchage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/tranchageRange")
    public List<JSONObject> TranchageRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.tranchageThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.tranchageLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Tranchage");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/presseTete")
    public List<JSONObject> PresseTete(){
        List<JSONObject> alty = alpi.presseTeteThisYear();
    List<JSONObject> ally = alpi.presseTeteLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Presse Tête");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/presseTeteRange")
    public List<JSONObject> PresseTeteRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.presseTeteThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.presseTeteLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Presse Tête");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ridotto/brazil")
    public List<JSONObject> RapportBrazil(){
        List<JSONObject> brazil = new ArrayList<>();
        brazil.addAll(Brazil());
        brazil.addAll(EncollageBrazil());
        brazil.addAll(Encolleuse1Brazil());
        brazil.addAll(Encolleuse2Brazil());
        brazil.addAll(Encolleuse3Brazil());
        brazil.addAll(ScieBongioanni());
        brazil.addAll(PresseTete());
        brazil.addAll(Tranchage());
        brazil.addAll(Trancheuse1());
        brazil.addAll(Trancheuse2());
        return brazil;        
    }
    
    @GetMapping("/ridotto/brazilRange")
    public List<JSONObject> RapportBrazilRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> brazil = new ArrayList<>();
        brazil.addAll(BrazilRange(date1, date2, date3, date4));
        brazil.addAll(EncollageBrazilRange(date1, date2, date3, date4));
        brazil.addAll(Encolleuse1BrazilRange(date1, date2, date3, date4));
        brazil.addAll(Encolleuse2BrazilRange(date1, date2, date3, date4));
        brazil.addAll(Encolleuse3BrazilRange(date1, date2, date3, date4));
        brazil.addAll(ScieBongioanniRange(date1, date2, date3, date4));
        brazil.addAll(PresseTeteRange(date1, date2, date3, date4));
        brazil.addAll(TranchageRange(date1, date2, date3, date4));
        brazil.addAll(Trancheuse1Range(date1, date2, date3, date4));
        brazil.addAll(Trancheuse2Range(date1, date2, date3, date4));
        return brazil;        
    }
    
    
    @GetMapping("/presseSimi")
    public List<JSONObject> PresseSimi(){
        List<JSONObject> alty = alpi.presseSimiThisYear();
    List<JSONObject> ally = alpi.presseSimiLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Presse Simi");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/presseSimiRange")
    public List<JSONObject> PresseSimiRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.presseSimiThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.presseSimiLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Presse Simi");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1CP")
    public List<JSONObject> Encolleuse1CP(){
        List<JSONObject> alty = alpi.encolleuse1CPThisYear();
    List<JSONObject> ally = alpi.encolleuse1CPLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 1 P.");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse1CPRange")
    public List<JSONObject> Encolleuse1CPRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.encolleuse1CPThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.encolleuse1CPLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 1 P.");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2CP")
    public List<JSONObject> Encolleuse2CP(){
        List<JSONObject> alty = alpi.encolleuse2CPThisYear();
    List<JSONObject> ally = alpi.encolleuse2CPLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 2 P.");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse2CPRange")
    public List<JSONObject> Encolleuse2CPRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.encolleuse2CPThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.encolleuse2CPLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 2 P.");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3CP")
    public List<JSONObject> Encolleuse3CP(){
        List<JSONObject> alty = alpi.encolleuse3CPThisYear();
    List<JSONObject> ally = alpi.encolleuse3CPLastYear();
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 3 S.");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/encolleuse3CPRange")
    public List<JSONObject> Encolleuse3CPRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> alty = alpi.encolleuse3CPThisYearRange(date3, date4);
    List<JSONObject> ally = alpi.encolleuse3CPLastYearRange(date1, date2);
    Map<String, Object> response = new HashMap<>(); 
    List<JSONObject> MTBF2 = new ArrayList<>();
    for (int i = 0; i < alty.size(); i++){
        for (int j = 0; j < ally.size(); j++){
        double x = (Integer.parseInt(alty.get(i).get("nbre").toString()));
        double y = (Integer.parseInt(ally.get(j).get("nbre").toString()));
        double x1 = (Double.parseDouble(alty.get(i).get("TDT").toString()));
        double y1 = (Double.parseDouble(ally.get(j).get("TDT").toString()));
        double x2 = (Double.parseDouble(alty.get(i).get("TTR").toString()));
        double y2 = (Double.parseDouble(ally.get(j).get("TTR").toString()));
        double x3 = (Double.parseDouble(alty.get(i).get("WT").toString()));
        double y3 = (Double.parseDouble(ally.get(j).get("WT").toString()));

//            response.put("annee", pty.get(i).get("annee"));
        response.put("dep", "Encolleuse 3 S.");
        response.put("date1", alty.get(i).get("date"));
        response.put("nbre1", alty.get(i).get("nbre"));
        response.put("TDT1", alty.get(i).get("TDT"));
        if(x == 0){
            response.put("WT1", 0);
            response.put("TTR1", 0);
            response.put("MDT1", 0);
        }else{
            response.put("WT1", x3/x);
            response.put("TTR1", x2/x);
            response.put("MDT1", x1/x);
        }
        response.put("date2", ally.get(j).get("date"));
        response.put("nbre2", ally.get(j).get("nbre"));
        response.put("TDT2", ally.get(j).get("TDT"));
        if(y == 0){
            response.put("WT2", 0);
            response.put("TTR2", 0);
            response.put("MDT2", 0);
        }else{
            response.put("WT2", y3/y);
            response.put("TTR2", y2/y);
            response.put("MDT2", y1/y);
        }
        if(x==0 && y==0){
        response.put("taux", 0);}
        if(x==0 && y!=0){
        response.put("taux", -100);}
        if(x!=0 && y==0){
        response.put("taux", 100);}
        if(x!=0 && y!=0){
        response.put("taux", ((x/y)-1)*100);}

        if(x1==0 && y1==0){
        response.put("taux_TDT", 0);
        response.put("taux_MDT", 0);}
        if(x1==0 && y1!=0){
        response.put("taux_TDT", -100);
        response.put("taux_MDT", -100);}
        if(x1!=0 && y1==0){
        response.put("taux_TDT", 100);
        response.put("taux_MDT", 100);}
        if(x1!=0 && y1!=0){
        response.put("taux_TDT", ((x1/y1)-1)*100);
        response.put("taux_MDT", (((x1/x)/(y1/y))-1)*100);}

        if(x2==0 && y2==0){
        response.put("taux_TTR", 0);}
        if(x2==0 && y2!=0){
        response.put("taux_TTR", -100);}
        if(x2!=0 && y2==0){
        response.put("taux_TTR", 100);}
        if(x2!=0 && y2!=0){
        response.put("taux_TTR", ((x2/x)/(y2/y)-1)*100);}

        if(x3==0 && y3==0){
        response.put("taux_WT", 0);}
        if(x3==0 && y3!=0){
        response.put("taux_WT", -100);}
        if(x3!=0 && y3==0){
        response.put("taux_WT", 100);}
        if(x3!=0 && y3!=0){
        response.put("taux_WT", ((x3/x)/(y3/y)-1)*100);}

        json = new JSONObject(response);
        MTBF2.add(json);
            
        }
        }
        System.out.println("récapitulatif: \n" + MTBF2);
        return MTBF2;
    }
    
    @GetMapping("/ridotto/contreplaque")
    public List<JSONObject> RapportContreplaque(){
        List<JSONObject> cp = new ArrayList<>();
        cp.addAll(Contreplaque());
        cp.addAll(PresseSimi());
        cp.addAll(Encolleuse1CP());
        cp.addAll(Encolleuse2CP());
        cp.addAll(Encolleuse3CP());
        return cp;        
    }
    
    @GetMapping("/ridotto/contreplaqueRange")
    public List<JSONObject> RapportContreplaqueRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> cp = new ArrayList<>();
        cp.addAll(ContreplaqueRange(date1, date2, date3, date4));
        cp.addAll(PresseSimiRange(date1, date2, date3, date4));
        cp.addAll(Encolleuse1CPRange(date1, date2, date3, date4));
        cp.addAll(Encolleuse2CPRange(date1, date2, date3, date4));
        cp.addAll(Encolleuse3CPRange(date1, date2, date3, date4));
        return cp;        
    }
    
    @GetMapping("/ridotto/scierie")
    public List<JSONObject> RapportScierie(){
        List<JSONObject> scierie = new ArrayList<>();
        scierie.addAll(Scierie());
        return scierie;        
    }
    
    @GetMapping("/ridotto/scierieRange")
    public List<JSONObject> RapportScierie(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut1") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin1") LocalDate date2, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut2") LocalDate date3, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin2") LocalDate date4){
        List<JSONObject> scierie = new ArrayList<>();
        scierie.addAll(ScierieRange(date1, date2, date3, date4));
        return scierie;        
    }
}
