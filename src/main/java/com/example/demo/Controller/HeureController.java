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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/api/heures")
public class HeureController {
    String mts;
    @Autowired
    private HeureRepository heureRepository ;
    
    @Autowired
    private HeuresServices heuresServices;
    
    @GetMapping("/thisMonth")
    public List<JSONObject> getHeures(){
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
        return heureRepository.HeuresMonth(mts);
    }
    
    @GetMapping("/lastMonth")
    public List<JSONObject> lastMonthHeures(){
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
        return heureRepository.HeuresMonth(mts);
    }
    
    @GetMapping("/range")
    public List<JSONObject> rangeHeures(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){
        return heureRepository.HeuresRange(date1, date2);
    }

    @GetMapping("/all")
    public List<JSONObject> ToutesLesHeures(){
        return heuresServices.toutesHeures();
    }
    
    @GetMapping("/machProg")
    public List<JSONObject> machProg(@RequestParam("dep") String dep){
        LocalDate date = LocalDate.now().minusDays(1);
        List<JSONObject> machines = heureRepository.HeuresByDepMachine(date, dep);
        return machines;
    }
    
    @GetMapping("/machProgRange")
    public List<JSONObject> machProgRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date, @RequestParam("dep") String dep){
        List<JSONObject> machines = heureRepository.HeuresByDepMachine(date, dep);
        return machines;
    }
    
    @GetMapping("/machProgRangeMonth")
    public List<JSONObject> machProgMonth(@RequestParam("date")String date,@RequestParam("dep") String dep){
        List<JSONObject> machines = heureRepository.HeuresByDepMachineMonth(date, dep);
        return machines;
    }

    @GetMapping("/groupByDep")
    public List<JSONObject> HeuresByDep(){
        LocalDate date = LocalDate.now().minusDays(1);
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> hbd = heureRepository.HeuresByDep(date);
        List<JSONObject> mbd = heureRepository.MachinesByDep();
        Map<String, Object> response2 = new HashMap<>();
        
        hbd.forEach(h ->{
            mbd.forEach(m ->{
                String x = h.get("dep").toString();
                String y = m.get("nom").toString();
                if(x.equals(y)){
                    response2.put("date", h.get("date"));
                    response2.put("dep", h.get("dep"));
                    response2.put("Total_mach", m.get("mach"));
                    response2.put("mach_prog", h.get("nombre_machine"));
                    response2.put("hour_prog", h.get("heure"));
                    response2.put("arrets", h.get("nbre"));
                    response2.put("AT", h.get("AT"));
                    JSONObject json2 = new JSONObject(response2);
                    MTBF2.add(json2);    
                }
            });
        });
        return MTBF2;
    }

    @GetMapping("/groupByDepRange")
    public List<JSONObject> HeuresByDepRangeDay(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date){
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> hbd = heureRepository.HeuresByDep(date);
        List<JSONObject> mbd = heureRepository.MachinesByDep();
        Map<String, Object> response2 = new HashMap<>();
        
        hbd.forEach(h ->{
            mbd.forEach(m ->{
                String x = h.get("dep").toString();
                String y = m.get("nom").toString();
                if(x.equals(y)){
                    response2.put("date", h.get("date"));
                    response2.put("dep", h.get("dep"));
                    response2.put("Total_mach", m.get("mach"));
                    response2.put("mach_prog", h.get("nombre_machine"));
                    response2.put("hour_prog", h.get("heure"));
                    response2.put("arrets", h.get("nbre"));
                    response2.put("AT", h.get("AT"));
                    JSONObject json2 = new JSONObject(response2);
                    MTBF2.add(json2);    
                }
            });
        });
        return MTBF2;
    }

    @GetMapping("/groupByDepRangeMonth")
    public List<JSONObject> HeuresByDepRangeMonth(@RequestParam("date") String date){
        List<JSONObject> MTBF2 = new ArrayList<>();
        List<JSONObject> hbd = heureRepository.HeuresByDepMonth(date);
        List<JSONObject> mbd = heureRepository.MachinesByDep();
        Map<String, Object> response2 = new HashMap<>();
        
        hbd.forEach(h ->{
            mbd.forEach(m ->{
                String x = h.get("dep").toString();
                String y = m.get("nom").toString();
                if(x.equals(y)){
                    response2.put("date", h.get("date"));
                    response2.put("dep", h.get("dep"));
                    response2.put("Total_mach", m.get("mach"));
                    response2.put("mach_prog", h.get("nombre_machine"));
                    response2.put("hour_prog", h.get("heure"));
                    response2.put("arrets", h.get("nbre"));
                    response2.put("AT", h.get("AT"));
                    JSONObject json2 = new JSONObject(response2);
                    MTBF2.add(json2);    
                }
            });
        });
        return MTBF2;
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
