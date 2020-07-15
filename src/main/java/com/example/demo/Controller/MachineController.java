package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Lignes;
import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
import com.example.demo.model.LigneModel;
import com.example.demo.model.MachineModel;
import com.example.demo.repository.MachineRepository;
import com.example.demo.service.inter.IMachineService;
import com.example.demo.service.inter.IOperateurService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/machines")
public class MachineController {
    LocalDate date1, date2 ;
    String mts, j, l, n  ;
    JSONObject json, json2, json3, json4;    
	
	@Autowired
	private IMachineService machineService;
        
        @Autowired
	private MachineRepository machineRepository;
	
	@GetMapping
	public List<Machines> getMachines(){
		return machineService.allMachines();
	}
	
	@GetMapping("/{code}")
	public Machines getById(@PathVariable Long code){
		return machineService.findOne(code);
	}
	
	@PostMapping
	public void creationMachine(@RequestBody MachineModel machineModel) {
		Machines machine = new Machines(machineModel.getNom(), machineModel.getCode(), machineModel.getCentreCout(), machineModel.getLabel());
		machineService.addMachine(machine, machineModel.getIdLigne());
		//return new ResponseEntity<>("machine créée",HttpStatus.CREATED);
	}
	
	@PutMapping
	public void updateMachine(@RequestBody MachineModel machineModel) {
		Machines machine = new Machines(machineModel.getNom(), machineModel.getCode(), machineModel.getCentreCout(), machineModel.getLabel());
		machineService.updateMachine(machine, machineModel.getIdLigne());
		//return new ResponseEntity<>("machine modifiée",HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{code}")
	public void deleMachine(@PathVariable Long code) {
		machineService.deleteMachine(code);
	}
	
        @GetMapping("/departement/{dep}")
	public List<JSONObject> allMachinesByDepartmetn(@PathVariable Long dep){
		return machineRepository.machDep(dep);
	}
        
        @GetMapping("/pannes/{dep}")
	public List<JSONObject> historiquePannesMachines(@PathVariable Long dep){
		return machineRepository.historiquePannes(dep);
	}
        
        @GetMapping("/hour/LastMonth/{dep}")
	public JSONObject HourLastMonth(@PathVariable Long dep){
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
            return machineRepository.HourThisMonthMachine(mts, dep);
	}
        
        @GetMapping("/hour/ThisMonth/{dep}")
	public JSONObject HourThisMonth(@PathVariable Long dep){
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
            return machineRepository.HourThisMonthMachine(mts, dep);
	}       
        
        @GetMapping("/mtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> MTBFTByYearAlpi(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = machineRepository.HByYear(dep);
            List<JSONObject> pby = machineRepository.PByYear(dep);
//            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String, Object> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();  
             
             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         List<JSONObject> wth2 = new ArrayList<>();
         List<JSONObject> ttrh2 = new ArrayList<>();
         
         List<JSONObject> test5 = new ArrayList<>();
         LinkedHashSet<JSONObject> test7 = new LinkedHashSet<>();
         List<JSONObject> test6 = new ArrayList<>();
         
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

//            System.out.println("pannes :\n" + pby);
//            System.out.println("heures :\n" + hby);
            for(int i = 0; i< hby.size(); i++){
                        response2.put("date", String.valueOf(hby.get(i).get("date")));
                        response2.put("HT", String.valueOf(hby.get(i).get("HT")));
                        response2.put("WT", "0");
                        response2.put("TTR", "0");
                        response2.put("nbre", "0");
                        response2.put("TDT", "0");
                        json2 = new JSONObject(response2);
                test2.add(json2);
            } 
            System.out.println("heures :\n" + test2);

            if(!MTBF.isEmpty()){
            test2.forEach(y->{
                MTBF.forEach(t-> {
    //                aby.forEach(a->{                   

                       String h = String.valueOf(y.get("date"));
                       String m = String.valueOf(t.get("date"));
    //                   String ar = String.valueOf(a.get("date"));                   
                       //si date l'heure de fct = date panne
                       
                       System.out.println("d1 : " + h + " d2 : "+ m);

                       if(h.equals(m)){
                            response.put("date", String.valueOf(y.get("date")));
                            response.put("nbre", String.valueOf(t.get("nbre")));
                            response.put("TDT", String.valueOf(t.get("TDT")));                        
                            response.put("WT", String.valueOf(t.get("WT")));
                            response.put("TTR", String.valueOf(t.get("TTR")));
                            response.put("HT", String.valueOf(y.get("HT")));
                            json = new JSONObject(response);
                        }else{
                            response.put("date", String.valueOf(y.get("date")));
                            response.put("nbre", String.valueOf(y.get("nbre")));                        
                            response.put("WT", String.valueOf(y.get("WT")));
                            response.put("TTR", String.valueOf(y.get("TTR")));
                            response.put("TDT", String.valueOf(y.get("TDT")));
                            response.put("HT", String.valueOf(y.get("HT")));
                            json = new JSONObject(response);
                       }      
                       test.add(json); 
                });                

            });}else{
                test.addAll(test2);
            } 
         LinkedList<JSONObject> fin = new LinkedList<>();
        fin.addAll(test);
            System.out.println("total1 : " +test.size());
            System.out.println("total12 : " +test);
            System.out.println("total2 : " +fin.size());
            System.out.println("total21 : " +fin);
            
        
        if(fin.size() > 2){
            String first = String.valueOf(fin.getFirst().get("date"));
            String last = String.valueOf(fin.getLast().get("date"));
            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
            String nlast = String.valueOf(fin.getLast().get("nbre"));
            
            String tdt2 = String.valueOf(fin.getLast().get("TDT"));
            String tdt3 = String.valueOf(fin.getFirst().get("TDT"));
            
            String wt2 = String.valueOf(fin.getLast().get("WT"));
            String wt3 = String.valueOf(fin.getFirst().get("WT"));
            
            String ttr2 = String.valueOf(fin.getLast().get("TTR"));
            String ht = String.valueOf(fin.getLast().get("HT"));
            String ttr3 = String.valueOf(fin.getFirst().get("TTR"));
            String ht3 = String.valueOf(fin.getFirst().get("HT"));
        for(int i = 1; i< fin.size(); i++ ){
            
            String el1 = String.valueOf(fin.get(i).get("date"));
            String el = String.valueOf(fin.get(i-1).get("date"));
            
            String nel1 = String.valueOf(fin.get(i).get("nbre"));
            String nel = String.valueOf(fin.get(i-1).get("nbre"));
            
            String tdt = String.valueOf(fin.get(i-1).get("TDT"));
            String tdt1 = String.valueOf(fin.get(i).get("TDT"));
            
            String wt = String.valueOf(fin.get(i-1).get("WT"));
            String wt1 = String.valueOf(fin.get(i).get("WT"));
            
            String ttr = String.valueOf(fin.get(i-1).get("TTR"));
            String ttr1 = String.valueOf(fin.get(i).get("TTR"));
            
            ListIterator li = fin.listIterator();
            
            
                if(el.equals(el1)){
                    if(nel.equals("0") && !nel1.equals("0") && tdt.equals("0") && !tdt1.equals("0")){
                        response.put("date", el);
                        response.put("nbre", nel1);
                        response.put("TDT", tdt1);
                        response.put("WT", wt1);
                        response.put("TTR", ttr1);
                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
                        json3 = new JSONObject(response);
                    }else if(nel1.equals("0") && !nel.equals("0") && !tdt.equals("0") && tdt1.equals("0")){
                        response.put("date", el);
                        response.put("nbre", nel);
                        response.put("TDT", tdt);
                        response.put("WT", wt);
                        response.put("TTR", ttr);
                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
                        json3 = new JSONObject(response);
                    }
                    fin.remove(i);
                }
                else {
                    response.put("date", el);
                    response.put("nbre", nel);
                    response.put("TDT", tdt);
                    response.put("WT", wt);
                    response.put("TTR", ttr);
                    response.put("HT", String.valueOf(fin.get(i-1).get("HT")));
                    json3 = new JSONObject(response);
                }
            
//            else if(fin.size() == 1){
//                if(first.equals(el)){
//
//                }
//            } else if(fin.size() == 0){
//                if(first.equals(el)){
//
//                }
//            }
            test5.add(json3);
            response.put("date", last);
            response.put("nbre", nlast);
            response.put("TDT", tdt2);
            response.put("WT", wt2);
            response.put("TTR", ttr2);
            response.put("HT", ht);
            json4 = new JSONObject(response);
            test6.add(json4);
            
            System.out.println("suis dépassé"+ json3);
        }
        }
        else if(fin.size() == 2){
            String first = String.valueOf(fin.getFirst().get("date"));
            String last = String.valueOf(fin.getLast().get("date"));
            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
            String nlast = String.valueOf(fin.getLast().get("nbre"));
            
            String tdt2 = String.valueOf(fin.getLast().get("TDT"));
            String tdt3 = String.valueOf(fin.getFirst().get("TDT"));
            
            String wt2 = String.valueOf(fin.getLast().get("WT"));
            String wt3 = String.valueOf(fin.getFirst().get("WT"));
            
            String ttr2 = String.valueOf(fin.getLast().get("TTR"));
            String ht = String.valueOf(fin.getLast().get("HT"));
            String ttr3 = String.valueOf(fin.getFirst().get("TTR"));
            String ht3 = String.valueOf(fin.getFirst().get("HT"));
            for(int i = 0; i<fin.size(); i++){
                if(first.equals(last)){
                    if(nfirst.equals("0") && !nlast.equals("0") && tdt3.equals("0") && !tdt2.equals("0")){
                        response.put("date", first);
                        response.put("nbre", nlast);
                        response.put("TDT", tdt2);
                        response.put("WT", wt2);
                        response.put("TTR", ttr2);
                        response.put("HT", ht);
                        json3 = new JSONObject(response);
                    }else if(nlast.equals("0") && !nfirst.equals("0") && !tdt3.equals("0") && tdt2.equals("0")){
                        response.put("date", first);
                        response.put("nbre", nfirst);
                        response.put("TDT", tdt3);
                        response.put("WT", wt3);
                        response.put("TTR", ttr3);
                        response.put("HT", ht);
                        json3 = new JSONObject(response);
                    }
//                    fin.remove(fin.getFirst());
                    test5.add(json3);
                }else{
                    test5.addAll(fin);
                }
                
        }
            }
        else if (fin.size() == 1){
            for(int y = 0; y<fin.size(); y++){
            response.put("date", String.valueOf(fin.get(y).get("date")));
            response.put("nbre", String.valueOf(fin.get(y).get("nbre")));
            response.put("TDT", String.valueOf(fin.get(y).get("TDT")));
            response.put("WT", String.valueOf(fin.get(y).get("WT")));
            response.put("TTR", String.valueOf(fin.get(y).get("TTR")));
            response.put("HT", String.valueOf(fin.get(y).get("HT")));
            json3 = new JSONObject(response);
                test5.add(json3);
                System.out.println("ça ne donne pas" + json3);
            }
            }
         
//        test1.forEach(x->{
//            mdtty.forEach(t-> {                  
//                
//                   String h = String.valueOf(x.get("date"));
//                   String m = String.valueOf(t.get("date"));                    
//                   String n = String.valueOf(t.get("nbre"));   
//                   String nh = String.valueOf(x.get("nbre"));   
//                   //si date l'heure de fct = date panne
//                 if(!h.equals(m)) {
//                     response.put("date", String.valueOf(x.get("date")));
//                     response.put("nbre", String.valueOf(x.get("nbre")));
//                     response.put("TDT", String.valueOf(x.get("TDT")));
//                     response.put("HT", String.valueOf(x.get("HT")));
//                     json3 = new JSONObject(response);
//                 }  
//                 else if(h.equals(m) && n.equals(nh)){
//                        response.put("date", String.valueOf(x.get("date")));
//                        response.put("nbre", String.valueOf(t.get("nbre")));
//                        response.put("TDT", String.valueOf(t.get("TDT")));
//                        response.put("HT", String.valueOf(x.get("HT")));
//                        json3 = new JSONObject(response);
//                     }
//                     
//                             
//            });      
//                  test5.add(json3);        
//        });   
//
//        for(int i = 0; i<test5.size(); i++){
//           for(int j = 1; j<test5.size()+1; j++){
//               String di = String.valueOf(test5.get(i).get("date"));
//               String dj = String.valueOf(test5.get(j).get("date"));
//           } 
//        }
//        test5.addAll(test6);
//        if(fin.size() > 2){
//            test5.addAll(test6);
//        }
        test5.addAll(test6);
        test7.addAll(test5);
        return test7;
        }

        @GetMapping("/mtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> MTBFThisYearAlpi(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = machineRepository.PThisYear(dep, mt);
        List<JSONObject> hty = machineRepository.HThisYear(dep, mt);
//        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
         Map<String,String> response = new HashMap<>();
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
        
        Map<String, Double> tdtd = pty.stream().collect(
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
            mdtty.add(json2);
        });
                        System.out.println("final \n" + mdtty);
        for(int i = 0; i< hty.size(); i++){
                    response2.put("date", String.valueOf(hty.get(i).get("date")));
                    response2.put("HT", String.valueOf(hty.get(i).get("HT")));
                    response2.put("nbre", "0");
                    response2.put("TDT", "0");                    
                    response2.put("WT", "0");
                    response2.put("TTR", "0");
                    json2 = new JSONObject(response2);
            test3.add(json2);
        } 
         

        if(!mdtty.isEmpty()){
        test3.forEach(y->{
            mdtty.forEach(t-> {
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));
                   String x = String.valueOf(t.get("nbre"));
                   
                   if(h.equals(m)){                       
                        response.put("date", String.valueOf(y.get("date")));
                        response.put("nbre", String.valueOf(t.get("nbre")));
                        response.put("TDT", String.valueOf(t.get("TDT")));
                        response.put("WT", String.valueOf(t.get("WT")));
                        response.put("TTR", String.valueOf(t.get("TTR")));
                        response.put("HT", String.valueOf(y.get("HT")));
                        json = new JSONObject(response);                       
                    }else{
                        response.put("date", String.valueOf(y.get("date")));
                        response.put("nbre", String.valueOf(y.get("nbre")));
                        response.put("TDT", String.valueOf(y.get("TDT")));
                        response.put("WT", String.valueOf(y.get("WT")));
                        response.put("TTR", String.valueOf(y.get("TTR")));
                        response.put("HT", String.valueOf(y.get("HT")));
                        json = new JSONObject(response);
                   }                           

           test1.add(json);
            });           
//                   test1.add(json);
//                   test5.add(json);
        }); }else{
            test1.addAll(test3);
        }
        
        LinkedList<JSONObject> fin = new LinkedList<>();
        fin.addAll(test1);
            System.out.println("total1 : " +test1.size());
            System.out.println("total12 : " +test1);
            System.out.println("total2 : " +fin.size());
            System.out.println("total21 : " +fin);
            
        
        if(fin.size() > 2){
            String first = String.valueOf(fin.getFirst().get("date"));
            String last = String.valueOf(fin.getLast().get("date"));
            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
            String nlast = String.valueOf(fin.getLast().get("nbre"));
            
            String tdt2 = String.valueOf(fin.getLast().get("TDT"));
            String tdt3 = String.valueOf(fin.getFirst().get("TDT"));
            
            String wt2 = String.valueOf(fin.getLast().get("WT"));
            String wt3 = String.valueOf(fin.getFirst().get("WT"));
            
            String ttr2 = String.valueOf(fin.getLast().get("TTR"));
            String ht = String.valueOf(fin.getLast().get("HT"));
            String ttr3 = String.valueOf(fin.getFirst().get("TTR"));
            String ht3 = String.valueOf(fin.getFirst().get("HT"));
        for(int i = 1; i< fin.size(); i++ ){
            
            String el1 = String.valueOf(fin.get(i).get("date"));
            String el = String.valueOf(fin.get(i-1).get("date"));
            
            String nel1 = String.valueOf(fin.get(i).get("nbre"));
            String nel = String.valueOf(fin.get(i-1).get("nbre"));
            
            String tdt = String.valueOf(fin.get(i-1).get("TDT"));
            String tdt1 = String.valueOf(fin.get(i).get("TDT"));
            
            String wt = String.valueOf(fin.get(i-1).get("WT"));
            String wt1 = String.valueOf(fin.get(i).get("WT"));
            
            String ttr = String.valueOf(fin.get(i-1).get("TTR"));
            String ttr1 = String.valueOf(fin.get(i).get("TTR"));
            
            ListIterator li = fin.listIterator();
            
            
                if(el.equals(el1)){
                    if(nel.equals("0") && !nel1.equals("0") && tdt.equals("0") && !tdt1.equals("0")){
                        response.put("date", el);
                        response.put("nbre", nel1);
                        response.put("TDT", tdt1);
                        response.put("WT", wt1);
                        response.put("TTR", ttr1);
                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
                        json3 = new JSONObject(response);
                    }else if(nel1.equals("0") && !nel.equals("0") && !tdt.equals("0") && tdt1.equals("0")){
                        response.put("date", el);
                        response.put("nbre", nel);
                        response.put("TDT", tdt);
                        response.put("WT", wt);
                        response.put("TTR", ttr);
                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
                        json3 = new JSONObject(response);
                    }
                    fin.remove(i);
                }
                else {
                    response.put("date", el);
                    response.put("nbre", nel);
                    response.put("TDT", tdt);
                    response.put("WT", wt);
                    response.put("TTR", ttr);
                    response.put("HT", String.valueOf(fin.get(i-1).get("HT")));
                    json3 = new JSONObject(response);
                }
            
//            else if(fin.size() == 1){
//                if(first.equals(el)){
//
//                }
//            } else if(fin.size() == 0){
//                if(first.equals(el)){
//
//                }
//            }
            test5.add(json3);
            response.put("date", last);
            response.put("nbre", nlast);
            response.put("TDT", tdt2);
            response.put("WT", wt2);
            response.put("TTR", ttr2);
            response.put("HT", ht);
            json4 = new JSONObject(response);
            test6.add(json4);
            
            System.out.println("suis dépassé"+ json3);
        }
        }
        else if(fin.size() == 2){
            String first = String.valueOf(fin.getFirst().get("date"));
            String last = String.valueOf(fin.getLast().get("date"));
            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
            String nlast = String.valueOf(fin.getLast().get("nbre"));
            
            String tdt2 = String.valueOf(fin.getLast().get("TDT"));
            String tdt3 = String.valueOf(fin.getFirst().get("TDT"));
            
            String wt2 = String.valueOf(fin.getLast().get("WT"));
            String wt3 = String.valueOf(fin.getFirst().get("WT"));
            
            String ttr2 = String.valueOf(fin.getLast().get("TTR"));
            String ht = String.valueOf(fin.getLast().get("HT"));
            String ttr3 = String.valueOf(fin.getFirst().get("TTR"));
            String ht3 = String.valueOf(fin.getFirst().get("HT"));
            for(int i = 0; i<fin.size(); i++){
                if(first.equals(last)){
                    if(nfirst.equals("0") && !nlast.equals("0") && tdt3.equals("0") && !tdt2.equals("0")){
                        response.put("date", first);
                        response.put("nbre", nlast);
                        response.put("TDT", tdt2);
                        response.put("WT", wt2);
                        response.put("TTR", ttr2);
                        response.put("HT", ht);
                        json3 = new JSONObject(response);
                    }else if(nlast.equals("0") && !nfirst.equals("0") && !tdt3.equals("0") && tdt2.equals("0")){
                        response.put("date", first);
                        response.put("nbre", nfirst);
                        response.put("TDT", tdt3);
                        response.put("WT", wt3);
                        response.put("TTR", ttr3);
                        response.put("HT", ht);
                        json3 = new JSONObject(response);
                    }
//                    fin.remove(fin.getFirst());
                    test5.add(json3);
                }else{
                    test5.addAll(fin);
                }
                
        }
            }
        else if (fin.size() == 1){
            for(int y = 0; y<fin.size(); y++){
            response.put("date", String.valueOf(fin.get(y).get("date")));
            response.put("nbre", String.valueOf(fin.get(y).get("nbre")));
            response.put("TDT", String.valueOf(fin.get(y).get("TDT")));
            response.put("WT", String.valueOf(fin.get(y).get("WT")));
            response.put("TTR", String.valueOf(fin.get(y).get("TTR")));
            response.put("HT", String.valueOf(fin.get(y).get("HT")));
            json3 = new JSONObject(response);
                test5.add(json3);
                System.out.println("ça ne donne pas" + json3);
            }
            }
         
//        test1.forEach(x->{
//            mdtty.forEach(t-> {                  
//                
//                   String h = String.valueOf(x.get("date"));
//                   String m = String.valueOf(t.get("date"));                    
//                   String n = String.valueOf(t.get("nbre"));   
//                   String nh = String.valueOf(x.get("nbre"));   
//                   //si date l'heure de fct = date panne
//                 if(!h.equals(m)) {
//                     response.put("date", String.valueOf(x.get("date")));
//                     response.put("nbre", String.valueOf(x.get("nbre")));
//                     response.put("TDT", String.valueOf(x.get("TDT")));
//                     response.put("HT", String.valueOf(x.get("HT")));
//                     json3 = new JSONObject(response);
//                 }  
//                 else if(h.equals(m) && n.equals(nh)){
//                        response.put("date", String.valueOf(x.get("date")));
//                        response.put("nbre", String.valueOf(t.get("nbre")));
//                        response.put("TDT", String.valueOf(t.get("TDT")));
//                        response.put("HT", String.valueOf(x.get("HT")));
//                        json3 = new JSONObject(response);
//                     }
//                     
//                             
//            });      
//                  test5.add(json3);        
//        });   
//
//        for(int i = 0; i<test5.size(); i++){
//           for(int j = 1; j<test5.size()+1; j++){
//               String di = String.valueOf(test5.get(i).get("date"));
//               String dj = String.valueOf(test5.get(j).get("date"));
//           } 
//        }
//        test5.addAll(test6);
//        if(fin.size() > 2){
//            test5.addAll(test6);
//        }
        test5.addAll(test6);
        test7.addAll(test5);
        return test7;
    } 

}
