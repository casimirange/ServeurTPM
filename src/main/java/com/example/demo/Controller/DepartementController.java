package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Departement;
import com.example.demo.repository.DashboardRepository;
import com.example.demo.repository.DepartementRepository;
import com.example.demo.service.inter.IDepartementService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import net.minidev.json.JSONObject;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/departements")
public class DepartementController {
	
	@Autowired
	private IDepartementService depService;
        
        @Autowired
        private DepartementRepository departementRepository;
        
        LocalDate date1, date2 ;
        String mts, j, l, n  ;
        
        @Autowired
        private DashboardRepository dashboardRepository;
        JSONObject json, json2, json3, json4;
	
	@GetMapping
	public List<Departement> getDepartements(){
		return depService.allDepartements();
	}
	
	@GetMapping("/{id}")
	public Departement getById(@PathVariable Long id){
		return depService.findOne(id);
	}
	
	@PostMapping
	public void addDepartement(@RequestBody Departement departement) {
		depService.addDepartement(departement);
	}
	
	@PutMapping
	public void updateDepartement(@RequestBody Departement departement) {
		depService.updateDep(departement);
	}
	
	@DeleteMapping("/{id}")
	public void deleDepartement(@PathVariable Long id) {
		depService.deleteDep(id);
	}
	
        @GetMapping("/pannes/{dep}")
	public List<JSONObject> allMachinesByDepartmetn(@PathVariable Long dep){
		return departementRepository.panneDep(dep);
	}
        
        @GetMapping("/pannes/countThisMonth/{dep}")
	public JSONObject CountDepPanneThisMonth(@PathVariable Long dep){
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
		return departementRepository.CountDepPanne(mts, dep);
	}
        
        
        
        @GetMapping("/pannes/countLastMonth/{dep}")
	public JSONObject CountDepPanneLastMonth(@PathVariable Long dep){
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
		return departementRepository.CountDepPanne(mts, dep);
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
            return departementRepository.HourDep(mts, dep);
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
            return departementRepository.HourDep(mts, dep);
	}
        
    
        
    @GetMapping("/mtbfByYear/{dep}")
    public List<JSONObject> MTBFTByYearAlpi(@PathVariable Long dep){
        
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> hby = departementRepository.HByYear(dep);
        List<JSONObject> pby = departementRepository.PByYear(dep);
        List<JSONObject> aby = dashboardRepository.AByYear();
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         List<Map<String,String>> reponse = new ArrayList<>();
         
         List<JSONObject> test = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>();    
         
         
        for(int i = 0; i< hby.size(); i++){
                    response2.put("date", String.valueOf(hby.get(i).get("date")));
                    response2.put("HT", String.valueOf(hby.get(i).get("HT")));
                    response2.put("nbre", "0");
                    response2.put("TDT", "0");
                    json2 = new JSONObject(response2);
            test2.add(json2);
        } 
         

        test2.forEach(y->{
            pby.forEach(t-> {
//                aby.forEach(a->{                   
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));
//                   String ar = String.valueOf(a.get("date"));                   
                   //si date l'heure de fct = date panne
                   
                   if(h.equals(m)){
                        response.put("date", String.valueOf(y.get("date")));
                        response.put("nbre", String.valueOf(t.get("nbre")));
                        response.put("TDT", String.valueOf(t.get("TDT")));
                        response.put("HT", String.valueOf(y.get("HT")));
                        json = new JSONObject(response);
                    }else{
                        response.put("date", String.valueOf(y.get("date")));
                        response.put("nbre", String.valueOf(y.get("nbre")));
                        response.put("TDT", String.valueOf(y.get("TDT")));
                        response.put("HT", String.valueOf(y.get("HT")));
                        json = new JSONObject(response);
                   }              
            });
            
            test.add(json); 
                         
        });           
        
        return test;

//        return departementRepository.PByYear(dep);
    }
    
    @GetMapping("/mtbfThisYear/{dep}")
    public LinkedHashSet<JSONObject> MTBFThisYearAlpi(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = departementRepository.PThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.HThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         List<Map<String,String>> reponse = new ArrayList<>();
         
         List<JSONObject> test3 = new ArrayList<>();
         List<JSONObject> test4 = new ArrayList<>(); 
         LinkedHashSet<JSONObject> test1 = new LinkedHashSet<>(); 
         List<JSONObject> test5 = new ArrayList<>();
         List<JSONObject> test2 = new ArrayList<>();
         LinkedHashSet<JSONObject> test7 = new LinkedHashSet<>();
         List<JSONObject> test6 = new ArrayList<>();

        for(int i = 0; i< hty.size(); i++){
                    response2.put("date", String.valueOf(hty.get(i).get("date")));
                    response2.put("HT", String.valueOf(hty.get(i).get("HT")));
                    response2.put("nbre", "0");
                    response2.put("TDT", "0");
                    json2 = new JSONObject(response2);
            test3.add(json2);
        } 
         

        test3.forEach(y->{
            mdtty.forEach(t-> {
                
                   String h = String.valueOf(y.get("date"));
                   String m = String.valueOf(t.get("date"));
                   String x = String.valueOf(t.get("nbre"));
                   
                   if(h.equals(m)){                       
                        response.put("date", String.valueOf(y.get("date")));
                        response.put("nbre", String.valueOf(t.get("nbre")));
                        response.put("TDT", String.valueOf(t.get("TDT")));
                        response.put("HT", String.valueOf(y.get("HT")));
                        json = new JSONObject(response);                       
                    }else{
                        response.put("date", String.valueOf(y.get("date")));
                        response.put("nbre", String.valueOf(y.get("nbre")));
                        response.put("TDT", String.valueOf(y.get("TDT")));
                        response.put("HT", String.valueOf(y.get("HT")));
                        json = new JSONObject(response);
                   }                           

           test1.add(json);
            });           
//                   test1.add(json);
//                   test5.add(json);
        }); 
        
        LinkedList<JSONObject> fin = new LinkedList<>();
        fin.addAll(test1);
        
        for(int i = 1; i< fin.size(); i++ ){
            String first = String.valueOf(fin.getFirst().get("date"));
            String last = String.valueOf(fin.getLast().get("date"));
            String el1 = String.valueOf(fin.get(i).get("date"));
            String el = String.valueOf(fin.get(i-1).get("date"));
            
            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
            String nlast = String.valueOf(fin.getLast().get("nbre"));
            String nel1 = String.valueOf(fin.get(i).get("nbre"));
            String nel = String.valueOf(fin.get(i-1).get("nbre"));
            
            String tdt = String.valueOf(fin.get(i-1).get("TDT"));
            String tdt1 = String.valueOf(fin.get(i).get("TDT"));
            
            ListIterator li = fin.listIterator();
            
            if(fin.size() > 2){
                if(el.equals(el1)){
                    if(nel.equals("0") && !nel1.equals("0") && tdt.equals("0") && !tdt1.equals("0")){
                        response.put("date", el);
                        response.put("nbre", nel1);
                        response.put("TDT", tdt1);
                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
                        json3 = new JSONObject(response);
                    }else if(nel1.equals("0") && !nel.equals("0") && !tdt.equals("0") && tdt1.equals("0")){
                        response.put("date", el);
                        response.put("nbre", nel);
                        response.put("TDT", tdt);
                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
                        json3 = new JSONObject(response);
                    }
                    fin.remove(i);
                }
                else {
                    response.put("date", el);
                    response.put("nbre", nel);
                    response.put("TDT", tdt);
                    response.put("HT", String.valueOf(fin.get(i-1).get("HT")));
                    json3 = new JSONObject(response);
                }
                
                
//                }else if(el.equals(el1)){
//                    if(nel.equals("0")){
//                        response.put("date", el);
//                        response.put("nbre", nel1);
//                        response.put("TDT", String.valueOf(fin.get(i+1).get("TDT")));
//                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
//                        json3 = new JSONObject(response);
//                    }
//                }else if(!el.equals(el1)){
//                    response.put("date", el);
//                    response.put("nbre", nel);
//                    response.put("TDT", String.valueOf(fin.get(i).get("TDT")));
//                    response.put("HT", String.valueOf(fin.get(i).get("HT")));
//                    json3 = new JSONObject(response);
//                }else if(el.equals(last)){
//                    if(nel.equals("0")){
//                        response.put("date", el);
//                        response.put("nbre", nlast);
//                        response.put("TDT", String.valueOf(fin.getLast().get("TDT")));
//                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
//                        json3 = new JSONObject(response);
//                    }
//                }else if(!el.equals(last)){
//                    response.put("date", last);
//                    response.put("nbre", nlast);
//                    response.put("TDT", String.valueOf(fin.getLast().get("TDT")));
//                    response.put("HT", String.valueOf(fin.getLast().get("HT")));
//                    json3 = new JSONObject(response);
//                }
            } else if(fin.size() == 2){
                if(first.equals(last)){

                }
            } else if(fin.size() == 1){
                if(first.equals(el)){

                }
            } else if(fin.size() == 0){
                if(first.equals(el)){

                }
            }
            test5.add(json3);
            response.put("date", last);
                    response.put("nbre", nlast);
                    response.put("TDT", String.valueOf(fin.getLast().get("TDT")));
                    response.put("HT", String.valueOf(fin.getLast().get("HT")));
                    json4 = new JSONObject(response);
            test6.add(json4);
            
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
        test5.addAll(test6);
        test7.addAll(test5);
        return test7;
    }    
        
}
