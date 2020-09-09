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
import com.example.demo.entity.Heures;
import com.example.demo.entity.Pannes;
import com.example.demo.repository.DashboardRepository;
import com.example.demo.repository.DepartementRepository;
import com.example.demo.repository.PanneRepository;
import com.example.demo.service.inter.IDepartementService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.crypto.Data;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/departements")
public class DepartementController {
	
	@Autowired
	private IDepartementService depService;
        
	@Autowired
	private PanneRepository panneRepository;
        
        @Autowired
        private DepartementRepository departementRepository;
        
        LocalDate date1, date2 ;
        String mts, j, l, n  ;
        JSONObject json, json2, json3, json4;
        @Autowired
        private DashboardRepository dashboardRepository;
        
	
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
//		return departementRepository.panneDep(dep);
                List<JSONObject> Allpannes = departementRepository.panneDep(dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
		return MTBF2;
	}
        
        @GetMapping("/today/{dep}")
	public List<JSONObject> today(@PathVariable Long dep){
            LocalDate date = LocalDate.now();		 
            List<JSONObject> Allpannes = departementRepository.ToDayPannes(date, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/hier/{dep}")
	public List<JSONObject> hier(@PathVariable Long dep){
            LocalDate date = LocalDate.now().minusDays(1);		 
            List<JSONObject> Allpannes = departementRepository.ToDayPannes(date, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/csem/{dep}")
	public List<JSONObject> thisWeek(@PathVariable Long dep){
            
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("jour de la semaine: "+week);
            if(week == 1){
                date1 = LocalDate.now().minusDays(6);
                date2 = LocalDate.now();                
            }else if(week == 2){
                date1 = LocalDate.now();
                date2 = LocalDate.now().plusDays(6);                
            }else if(week == 3){
                date1 = LocalDate.now().minusDays(1);
                date2 = LocalDate.now().plusDays(5);                
            }else if(week == 4){
                date1 = LocalDate.now().minusDays(2);
                date2 = LocalDate.now().plusDays(4);                
            }else if(week == 5){
                date1 = LocalDate.now().minusDays(3);
                date2 = LocalDate.now().plusDays(3);                
            }else if(week == 6){
                date1 = LocalDate.now().minusDays(4);
                date2 = LocalDate.now().plusDays(2);                
            }else if(week == 7){
                date1 = LocalDate.now().minusDays(5);
                date2 = LocalDate.now().plusDays(1);                
            }
            System.out.println("jour de la semaine1: "+ date1.getDayOfWeek() + date1.getDayOfMonth());
            System.out.println("jour de la semaine7: "+ date2.getDayOfWeek() + date2.getDayOfMonth());
            		 
            List<JSONObject> Allpannes = departementRepository.WeekPannes(date1, date2, dep);
            System.out.println("all :" + Allpannes);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/semp/{dep}")
	public List<JSONObject> lastWeek(@PathVariable Long dep){
            
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            if(week == 1){
                date1 = LocalDate.now().minusDays(13);
                date2 = LocalDate.now().minusDays(7);                
            }else if(week == 2){
                date1 = LocalDate.now().minusDays(7);
                date2 = LocalDate.now().minusDays(1);                
            }else if(week == 3){
                date1 = LocalDate.now().minusDays(8);
                date2 = LocalDate.now().minusDays(2);                
            }else if(week == 4){
                date1 = LocalDate.now().minusDays(9);
                date2 = LocalDate.now().minusDays(3);                
            }else if(week == 5){
                date1 = LocalDate.now().minusDays(10);
                date2 = LocalDate.now().minusDays(4);                
            }else if(week == 6){
                date1 = LocalDate.now().minusDays(11);
                date2 = LocalDate.now().minusDays(5);                
            }else if(week == 7){
                date1 = LocalDate.now().minusDays(12);
                date2 = LocalDate.now().minusDays(6);                
            }
            System.out.println("jour de la semaine passée1: "+ date1.getDayOfWeek() + date1.getDayOfMonth());
            System.out.println("jour de la semaine^passée7: "+ date2.getDayOfWeek() + date2.getDayOfMonth());
            
            List<JSONObject> Allpannes = departementRepository.WeekPannes(date1, date2, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
            
	}
        
        @GetMapping("/Date_range/{dep}")
	public List<JSONObject> dateRange(@PathVariable Long dep, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){            
//            return panneRepository.WeekPannes(date1, date2);
            List<JSONObject> Allpannes = departementRepository.WeekPannes(date1, date2, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
                
        @GetMapping("/thisMonth/{dep}")
	public List<JSONObject> thisMonth(@PathVariable Long dep){
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
            
//		return panneRepository.MonthPannes(mts);
            List<JSONObject> Allpannes = departementRepository.MonthPannes(mts, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/lastMonth/{dep}")
	public List<JSONObject> lastMonth(@PathVariable Long dep){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month < 10){
                if(month == 0){
                    mts = String.valueOf(year - 1)+"/12";
                }else{
                    mts = String.valueOf(year)+"/0"+ String.valueOf(month);
                }                
            }else{
                mts = String.valueOf(year)+"/"+ String.valueOf(month);
            }
            System.out.println("mois passé: "+ mts);
//		return panneRepository.MonthPannes(mts);
                List<JSONObject> Allpannes = departementRepository.MonthPannes(mts, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/thisYear/{dep}")
	public List<JSONObject> thisYear(@PathVariable Long dep){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
            String mt = String.valueOf(year);
            System.out.println("année: "+ year);
            
//		return panneRepository.YearPannes(mt);
                List<JSONObject> Allpannes = departementRepository.YearPannes(mt, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/lastYear/{dep}")
	public List<JSONObject> lastYear(@PathVariable Long dep){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
            String mt = String.valueOf(year-1);
            System.out.println("an passé: "+ mt);
//		return panneRepository.YearPannes(mt);
                List<JSONObject> Allpannes = departementRepository.YearPannes(mt, dep);
            Map<String, Object> response2 = new HashMap<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            Allpannes.forEach(t -> {
                String numero = t.get("numero").toString();
                List<JSONObject> Endpannes = panneRepository.Heures(numero);
                List<JSONObject> Causepannes = panneRepository.Details(numero);
                System.out.println("taille :" + Endpannes.size());
                        if (Endpannes.size() == 2){
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", Causepannes.get(1).get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", Endpannes.get(0).get("heure_arret"));
                    response2.put("debut_inter", Endpannes.get(0).get("debut_inter"));
                    response2.put("fin_inter", Endpannes.get(1).get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", Causepannes.get(1).get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                        }
                        else {
                    response2.put("machine", t.get("machine"));
                    response2.put("code", t.get("code"));
                    response2.put("idM", t.get("idM"));
                    response2.put("date", t.get("date"));
                    response2.put("numero", t.get("numero"));
                    response2.put("cause", t.get("cause"));
                    response2.put("description", t.get("description"));
                    response2.put("details", t.get("details"));
                    response2.put("heure_arret", t.get("heure_arret"));
                    response2.put("debut_inter", t.get("debut_inter"));
                    response2.put("fin_inter", t.get("fin_inter"));
                    response2.put("etat", t.get("etat"));
                    response2.put("cont", t.get("cont"));
                    response2.put("quart", t.get("quart"));
                    response2.put("outil", t.get("outil"));
                    response2.put("ref", t.get("ref"));
                    response2.put("qte", t.get("qte"));
                    response2.put("wt", t.get("wt"));
                    response2.put("ttr", t.get("ttr"));
                    response2.put("dt", t.get("dt"));
                    response2.put("nomOP", t.get("nomOP"));
                    response2.put("prenomOP", t.get("prenomOP"));
                    response2.put("matOP", t.get("matOP"));
                    response2.put("nomTec", t.get("nomTec"));
                    response2.put("preTec", t.get("preTec"));
                    response2.put("matricule", t.get("matricule"));
                    response2.put("fonction", t.get("fonction"));
                    json = new JSONObject(response2);
                }
                
                MTBF2.add(json);
                
            });
             
		return MTBF2;
	}
        
        @GetMapping("/pannes/countThisMonth/{dep}")
	public List<JSONObject> CountDepPanneThisMonth(@PathVariable Long dep){
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
            
            List<JSONObject> pby = departementRepository.CountDepPanne(mts, dep);
            List<JSONObject> MTBF = new ArrayList<>();

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
//                        System.out.println("Total Down Time: "+ String.format("%.0f", td.get("TDT")));
                        if(h.equals(m) && h.equals(x) && h.equals(y)){
                            response2.put("date", h);
                            response2.put("nbre", nb.get("nbre"));
                            response2.put("TDT", td.get("TDT").toString().substring(0, td.get("TDT").toString().length() - 2));
                            response2.put("WT", wts.get("WT").toString().substring(0, wts.get("WT").toString().length() - 2));
                            response2.put("TTR", tt.get("TTR").toString().substring(0, tt.get("TTR").toString().length() - 2));
                            json2 = new JSONObject(response2);
                        }
                        
                    });
                });
            });
            MTBF.add(json2);
        });}else{
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json2 = new JSONObject(response2);
            MTBF.add(json2);
         }
            System.out.println("final \n" + MTBF);
		return MTBF;
	}
                        
        @GetMapping("/pannes/countLastMonth/{dep}")
	public List<JSONObject> CountDepPanneLastMonth(@PathVariable Long dep){
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
		List<JSONObject> pby = departementRepository.CountDepPanne(mts, dep);
List<JSONObject> MTBF = new ArrayList<>();

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
        });}else{
            response2.put("nbre", 0);
            response2.put("TDT", 0);
            response2.put("WT", 0);
            response2.put("TTR", 0);
            json2 = new JSONObject(response2);
            MTBF.add(json2);
         }
            System.out.println("final \n" + MTBF);
		return MTBF;
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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> hby = departementRepository.HByYear(dep);
            List<JSONObject> pby = departementRepository.PByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String, Object> response2 = new HashMap<>();
             Map<String, Object> response3 = new HashMap<>();
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
         List<JSONObject> hh2 = new ArrayList<>();
         
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
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().doubleValue());
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
            response2.put("WT", datr.getValue().doubleValue());
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
            response2.put("TTR", datee.getValue().doubleValue());
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
                        
                        if(h.equals(m) ){
                            response2.put("date", h);
                            response2.put("nbre", nb.get("nbre"));
                            response2.put("TDT", td.get("TDT"));
                            response2.put("WT", wts.get("WT"));
                            response2.put("TTR", tt.get("TTR"));
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
                        response2.put("HT", hby.get(i).get("HT"));
                        response2.put("WT", Double.parseDouble("0"));
                        response2.put("TTR", Double.parseDouble("0"));
                        response2.put("nbre", 0);
                        response2.put("TDT", Double.parseDouble("0"));
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
                            response2.put("date", String.valueOf(y.get("date")));
                            response2.put("nbre", t.get("nbre"));
                            response2.put("TDT", t.get("TDT"));                        
                            response2.put("WT", t.get("WT"));
                            response2.put("TTR", t.get("TTR"));
                            response2.put("HT", y.get("HT"));
                            json = new JSONObject(response2);
                        }else{
                            response2.put("date", String.valueOf(y.get("date")));
                            response2.put("nbre", y.get("nbre"));                        
                            response2.put("WT", y.get("WT"));
                            response2.put("TTR", y.get("TTR"));
                            response2.put("TDT", y.get("TDT"));
                            response2.put("HT", y.get("HT"));
                            json = new JSONObject(response2);
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
            
            Map<String, Double> h2 = test.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((Double)t.get("HT")).doubleValue()))); 
        
        h2.entrySet().stream()
            .forEach(datek -> {
                System.out.println("test2 " + datek.getKey() + " = " + datek.getValue()); 
            response2.put("date", datek.getKey());
            response2.put("HT", datek.getValue().doubleValue());
            json2 = new JSONObject(response2);
            hh2.add(json2);            
            });
            
            Map<String, Integer> result2 = test.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(datep -> {
                System.out.println("test3 " + datep.getKey() + " = " + datep.getValue()); 
            response2.put("date", datep.getKey());
            response2.put("nbre", datep.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        Map<String, Double> tdtd2 = test.stream().collect(
            Collectors.groupingBy(f -> f.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((double)g.get("TDT"))))); 
        
        tdtd2.entrySet().stream()
            .forEach(datesp -> {
                System.out.println("test4 " + datesp.getKey() + " = " + datesp.getValue()); 
            response2.put("date", datesp.getKey());
            response2.put("TDT", datesp.getValue().doubleValue());
            json2 = new JSONObject(response2);
            tdth2.add(json2);            
            });
//        
        Map<String, Double> wt1d2 = test.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((double)t.get("WT"))))); 
        
        wt1d2.entrySet().stream()
            .forEach(datrn -> {
                System.out.println("test5 " + datrn.getKey() + " = " + datrn.getValue()); 
            response2.put("date", datrn.getKey());
            response2.put("WT", datrn.getValue().doubleValue());
            json2 = new JSONObject(response2);
            wth2.add(json2);            
            });
//        
        Map<String, Double> ttrs2 = test.stream().collect(
            Collectors.groupingBy(e -> e.get("date").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(t -> ((Double)t.get("TTR")).doubleValue()))); 
        
        ttrs2.entrySet().stream()
            .forEach(dateej -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("date", dateej.getKey());
            response2.put("TTR", dateej.getValue());
            json2 = new JSONObject(response2);
            ttrh2.add(json2);            
            });
        
        nbre2.forEach(nb->{
            tdth2.forEach(td->{
                wth2.forEach(wts->{
                    ttrh2.forEach(tt->{
                        hh2.forEach(hh->{
                        String h = String.valueOf(nb.get("date"));
                        String m = String.valueOf(td.get("date"));
                        String x = String.valueOf(wts.get("date"));
                        String y = String.valueOf(tt.get("date"));
                        String z = String.valueOf(hh.get("date"));
                        
                        if(h.equals(m) && h.equals(x) && h.equals(y) && h.equals(z)){
                            response2.put("date", h);
                            response2.put("nbre", nb.get("nbre"));
                            response2.put("TDT", td.get("TDT"));
                            response2.put("WT", wts.get("WT"));
                            response2.put("TTR", tt.get("TTR"));
                            response2.put("HT", hh.get("HT"));
                            json2 = new JSONObject(response2);
                        }
                        });
                    });
                });
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);
            
        
//        if(fin.size() > 2){
//            String first = String.valueOf(fin.getFirst().get("date"));
//            String last = String.valueOf(fin.getLast().get("date"));
//            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
//            String nlast = String.valueOf(fin.getLast().get("nbre"));
//            
//            String tdt2 = String.valueOf(fin.getLast().get("TDT"));
//            String tdt3 = String.valueOf(fin.getFirst().get("TDT"));
//            
//            String wt2 = String.valueOf(fin.getLast().get("WT"));
//            String wt3 = String.valueOf(fin.getFirst().get("WT"));
//            
//            String ttr2 = String.valueOf(fin.getLast().get("TTR"));
//            String ht = String.valueOf(fin.getLast().get("HT"));
//            String ttr3 = String.valueOf(fin.getFirst().get("TTR"));
//            String ht3 = String.valueOf(fin.getFirst().get("HT"));
//        for(int i = 1; i< fin.size(); i++ ){
//            
//            String el1 = String.valueOf(fin.get(i).get("date"));
//            String el = String.valueOf(fin.get(i-1).get("date"));
//            
//            String nel1 = String.valueOf(fin.get(i).get("nbre"));
//            String nel = String.valueOf(fin.get(i-1).get("nbre"));
//            
//            String tdt = String.valueOf(fin.get(i-1).get("TDT"));
//            String tdt1 = String.valueOf(fin.get(i).get("TDT"));
//            
//            String wt = String.valueOf(fin.get(i-1).get("WT"));
//            String wt1 = String.valueOf(fin.get(i).get("WT"));
//            
//            String ttr = String.valueOf(fin.get(i-1).get("TTR"));
//            String ttr1 = String.valueOf(fin.get(i).get("TTR"));
//            
//            ListIterator li = fin.listIterator();
//            
//            
//                if(el.equals(el1)){
//                    if(nel.equals("0") && !nel1.equals("0") && tdt.equals("0") && !tdt1.equals("0")){
//                        response.put("date", el);
//                        response.put("nbre", nel1);
//                        response.put("TDT", tdt1);
//                        response.put("WT", wt1);
//                        response.put("TTR", ttr1);
//                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
//                        json3 = new JSONObject(response);
//                    }else if(nel1.equals("0") && !nel.equals("0") && !tdt.equals("0") && tdt1.equals("0")){
//                        response.put("date", el);
//                        response.put("nbre", nel);
//                        response.put("TDT", tdt);
//                        response.put("WT", wt);
//                        response.put("TTR", ttr);
//                        response.put("HT", String.valueOf(fin.get(i).get("HT")));
//                        json3 = new JSONObject(response);
//                    }
//                    fin.remove(i);
//                }
//                else {
//                    response.put("date", el);
//                    response.put("nbre", nel);
//                    response.put("TDT", tdt);
//                    response.put("WT", wt);
//                    response.put("TTR", ttr);
//                    response.put("HT", String.valueOf(fin.get(i-1).get("HT")));
//                    json3 = new JSONObject(response);
//                }
//            
////            else if(fin.size() == 1){
////                if(first.equals(el)){
////
////                }
////            } else if(fin.size() == 0){
////                if(first.equals(el)){
////
////                }
////            }
//            test5.add(json3);
//            response.put("date", last);
//            response.put("nbre", nlast);
//            response.put("TDT", tdt2);
//            response.put("WT", wt2);
//            response.put("TTR", ttr2);
//            response.put("HT", ht);
//            json4 = new JSONObject(response);
//            test6.add(json4);
//            
//            System.out.println("suis dépassé"+ json3);
//        }
//        }
//        else if(fin.size() == 2){
//            String first = String.valueOf(fin.getFirst().get("date"));
//            String last = String.valueOf(fin.getLast().get("date"));
//            String nfirst = String.valueOf(fin.getFirst().get("nbre"));
//            String nlast = String.valueOf(fin.getLast().get("nbre"));
//            
//            String tdt2 = String.valueOf(fin.getLast().get("TDT"));
//            String tdt3 = String.valueOf(fin.getFirst().get("TDT"));
//            
//            String wt2 = String.valueOf(fin.getLast().get("WT"));
//            String wt3 = String.valueOf(fin.getFirst().get("WT"));
//            
//            String ttr2 = String.valueOf(fin.getLast().get("TTR"));
//            String ht = String.valueOf(fin.getLast().get("HT"));
//            String ttr3 = String.valueOf(fin.getFirst().get("TTR"));
//            String ht3 = String.valueOf(fin.getFirst().get("HT"));
//            for(int i = 0; i<fin.size(); i++){
//                if(first.equals(last)){
//                    if(nfirst.equals("0") && !nlast.equals("0") && tdt3.equals("0") && !tdt2.equals("0")){
//                        response.put("date", first);
//                        response.put("nbre", nlast);
//                        response.put("TDT", tdt2);
//                        response.put("WT", wt2);
//                        response.put("TTR", ttr2);
//                        response.put("HT", ht);
//                        json3 = new JSONObject(response);
//                    }else if(nlast.equals("0") && !nfirst.equals("0") && !tdt3.equals("0") && tdt2.equals("0")){
//                        response.put("date", first);
//                        response.put("nbre", nfirst);
//                        response.put("TDT", tdt3);
//                        response.put("WT", wt3);
//                        response.put("TTR", ttr3);
//                        response.put("HT", ht);
//                        json3 = new JSONObject(response);
//                    }
////                    fin.remove(fin.getFirst());
//                    test5.add(json3);
//                }else{
//                    test5.addAll(fin);
//                }
//                
//        }
//            }
//        else if (fin.size() == 1){
//            for(int y = 0; y<fin.size(); y++){
//            response.put("date", String.valueOf(fin.get(y).get("date")));
//            response.put("nbre", String.valueOf(fin.get(y).get("nbre")));
//            response.put("TDT", String.valueOf(fin.get(y).get("TDT")));
//            response.put("WT", String.valueOf(fin.get(y).get("WT")));
//            response.put("TTR", String.valueOf(fin.get(y).get("TTR")));
//            response.put("HT", String.valueOf(fin.get(y).get("HT")));
//            json3 = new JSONObject(response);
//                test5.add(json3);
//                System.out.println("ça ne donne pas" + json3);
//            }
//            }
//         
////        test1.forEach(x->{
////            mdtty.forEach(t-> {                  
////                
////                   String h = String.valueOf(x.get("date"));
////                   String m = String.valueOf(t.get("date"));                    
////                   String n = String.valueOf(t.get("nbre"));   
////                   String nh = String.valueOf(x.get("nbre"));   
////                   //si date l'heure de fct = date panne
////                 if(!h.equals(m)) {
////                     response.put("date", String.valueOf(x.get("date")));
////                     response.put("nbre", String.valueOf(x.get("nbre")));
////                     response.put("TDT", String.valueOf(x.get("TDT")));
////                     response.put("HT", String.valueOf(x.get("HT")));
////                     json3 = new JSONObject(response);
////                 }  
////                 else if(h.equals(m) && n.equals(nh)){
////                        response.put("date", String.valueOf(x.get("date")));
////                        response.put("nbre", String.valueOf(t.get("nbre")));
////                        response.put("TDT", String.valueOf(t.get("TDT")));
////                        response.put("HT", String.valueOf(x.get("HT")));
////                        json3 = new JSONObject(response);
////                     }
////                     
////                             
////            });      
////                  test5.add(json3);        
////        });   
////
////        for(int i = 0; i<test5.size(); i++){
////           for(int j = 1; j<test5.size()+1; j++){
////               String di = String.valueOf(test5.get(i).get("date"));
////               String dj = String.valueOf(test5.get(j).get("date"));
////           } 
////        }
////        test5.addAll(test6);
////        if(fin.size() > 2){
////            test5.addAll(test6);
////        }
//        test5.addAll(test6);
//        test7.addAll(test5);
        return MTBF2;
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
        List<JSONObject> pty = departementRepository.PThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.HThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
        System.out.println("HFin \n" + test3);
         

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
            System.out.println("test 1:\n" + test1);
            
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
        
        @GetMapping("/paretoDepRange/{dep}")
        public List<JSONObject> ParetoThisYear(@PathVariable Long dep, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){                   

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.ParetoRange(dep, date1, date2);
            
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
        
        @GetMapping("/paretoDepThisMonth/{dep}")
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

            List<JSONObject> pty = departementRepository.ParetoThisMonth(dep, mts);
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
        
        @GetMapping("/paretoDepLastMonth/{dep}")
        public List<JSONObject> ParetoLastMonth(@PathVariable Long dep){

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

            List<JSONObject> pty = departementRepository.ParetoThisMonth(dep, mts);
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
        
        @GetMapping("/ligne1MtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> Ligne1ByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.Ligne1HTByYear(dep);
            List<JSONObject> pby = departementRepository.Ligne1DTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
         LinkedHashSet<JSONObject> test7 = new LinkedHashSet<>();
         List<JSONObject> test6 = new ArrayList<>();
        List<JSONObject> test5 = new ArrayList<>();
         
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
        
        @GetMapping("/ligne1MtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> Ligne1ThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.Ligne1DTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.Ligne1HTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/ligne2MtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> Ligne2ByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.Ligne2HTByYear(dep);
            List<JSONObject> pby = departementRepository.Ligne2DTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/ligne2MtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> Ligne2ThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.Ligne2DTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.Ligne2HTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/ligne3MtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> Ligne3ByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.Ligne3HTByYear(dep);
            List<JSONObject> pby = departementRepository.Ligne3DTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/ligne3MtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> Ligne3ThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.Ligne3DTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.Ligne3HTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/sechoirMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> SechoirByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.SechoirHTByYear(dep);
            List<JSONObject> pby = departementRepository.SechoirDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/sechoirMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> SechoirThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.SechoirDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.SechoirHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/ecorcageMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> EcorcageByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.EcorçageHTByYear(dep);
            List<JSONObject> pby = departementRepository.EcorçageDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/ecorcageMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> EcorcageThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.EcorçageDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.EcorçageHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/encollageBrazilMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> EncollageBrazilByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.EncollageBrazilHTByYear(dep);
            List<JSONObject> pby = departementRepository.EncollageBrazilDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/encollageBrazilMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> EncollageBrazilThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.EncollageBrazilDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.EncollageBrazilHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/encollageCPMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> EncollageCPByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.EncollageCPHTByYear(dep);
            List<JSONObject> pby = departementRepository.EncollageCPDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/encollageCPMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> EncollageCPThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.EncollageCPDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.EncollageCPHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/teintureMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> TeintureByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.TeintureHTByYear(dep);
            List<JSONObject> pby = departementRepository.TeintureDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/teintureMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> TeintureThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.TeintureDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.TeintureHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/tranchageMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> TranchageByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.TranchageHTByYear(dep);
            List<JSONObject> pby = departementRepository.TranchageDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/tranchageMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> TranchageThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.TranchageDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.TranchageHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/poncageMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> PoncageByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.PonçageHTByYear(dep);
            List<JSONObject> pby = departementRepository.PonçageDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();
System.out.println("panne ca:\n" + pby);
            System.out.println("heure ca:\n" + hby);
             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    


             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/poncageMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> PoncageThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.PonçageDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.PonçageHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
         Map<String,String> response = new HashMap<>();
         Map<String,String> response2 = new HashMap<>();
         
         System.out.println("panne ca:\n" + pty);
            System.out.println("heure ca:\n" + hty);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         
        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        });
        }else{
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
        
        @GetMapping("/pressageMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> PressageByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.PressageHTByYear(dep);
            List<JSONObject> pby = departementRepository.PressageDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    

            
             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/pressageMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> PressageThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.PressageDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.PressageHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/jointageMtbfByYear/{dep}")
        public LinkedHashSet<JSONObject> JointageByYear(@PathVariable Long dep){

            Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(0);
                int year = cal.get(Calendar.YEAR);

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> hby = departementRepository.JointageHTByYear(dep);
            List<JSONObject> pby = departementRepository.JointageDTByYear(dep);
            List<JSONObject> aby = dashboardRepository.AByYear();
             Map<String,String> response = new HashMap<>();
             Map<String,String> response2 = new HashMap<>();
             List<Map<String,String>> reponse = new ArrayList<>();

             List<JSONObject> test = new ArrayList<>();
             List<JSONObject> test2 = new ArrayList<>();    

            
             List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> wth = new ArrayList<>();
         List<JSONObject> ttrh = new ArrayList<>();
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
        
        @GetMapping("/jointageMtbfThisYear/{dep}")
        public LinkedHashSet<JSONObject> JointageThisYear(@PathVariable Long dep){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(0);
        int year = cal.get(Calendar.YEAR);
        String mt = String.valueOf(year);
        System.out.println("année: "+ year);
        
        List<JSONObject> MTBF = new ArrayList<>();
        List<JSONObject> mdtty = new ArrayList<>();
        List<JSONObject> pty = departementRepository.JointageDTThisYear(dep, mt);
        List<JSONObject> hty = departementRepository.JointageHTThisYear(dep, mt);
        List<JSONObject> aty = dashboardRepository.AThisYear(mt);
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
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
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
         

        if(!MTBF.isEmpty()){
        test3.forEach(y->{
            MTBF.forEach(t-> {
                
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
        
        @GetMapping("/paretoDerouleuseTDTRange")
        public List<JSONObject> DerouleuseParetoTDTThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){       
            
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.DerParetoRange(date1, date2);

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
        
        @GetMapping("/paretoDerouleuseTDTThisMonth")
        public List<JSONObject> DerouleuseParetoTDTThisMonth(){

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
            List<JSONObject> pty = departementRepository.DerParetoThisMonth(mts);

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
        
        @GetMapping("/paretoDerouleuseTDTLastMonth")
        public List<JSONObject> DerouleuseParetoTDTLastMonth(){

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
            List<JSONObject> pty = departementRepository.DerParetoThisMonth(mts);

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
        
        @GetMapping("/paretoDerouleuseMDTRange")
        public List<JSONObject> DerouleuseParetoMDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){
    
            
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.DerParetoRange(date1, date2);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoDerouleuseMDTThisMonth")
        public List<JSONObject> DerouleuseParetoMDTThisMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.DerParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoDerouleuseMDTLastMonth")
        public List<JSONObject> DerouleuseParetoMDTLastMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.DerParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoBobineuseTDTRange")
        public List<JSONObject> BobineuseParetoTDTThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){    
            
            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.BobParetoRange(date1, date2);

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
        
        @GetMapping("/paretoBobineuseTDTThisMonth")
        public List<JSONObject> BobineuseParetoTDTThisMonth(){

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
            List<JSONObject> pty = departementRepository.BobParetoThisMonth(mts);

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
        
        @GetMapping("/paretoBobineuseTDTLastMonth")
        public List<JSONObject> BobineuseParetoTDTLastMonth(){

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
            List<JSONObject> pty = departementRepository.BobParetoThisMonth(mts);

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
        
        @GetMapping("/paretoBobineuseMDTRange")
        public List<JSONObject> BobineuseParetoMDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.BobParetoRange(date1, date2);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoBobineuseMDTThisMonth")
        public List<JSONObject> BobineuseParetoMDTThisMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.BobParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoBobineuseMDTLastMonth")
        public List<JSONObject> BobineuseParetoMDTLastMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.BobParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoMagasinBobineTDTRange")
        public List<JSONObject> MagasinBobineParetoTDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MagBobParetoRange(date1, date2);

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
        
        @GetMapping("/paretoMagasinBobineTDTThisMonth")
        public List<JSONObject> MagasinBobineParetoTDTThisMonth(){

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
            List<JSONObject> pty = departementRepository.MagBobParetoThisMonth(mts);

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
        
        @GetMapping("/paretoMagasinBobineTDTLastMonth")
        public List<JSONObject> MagasinBobineParetoTDTLastMonth(){

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
            List<JSONObject> pty = departementRepository.MagBobParetoThisMonth(mts);

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
        
        @GetMapping("/paretoMagasinBobineMDTRange")
        public List<JSONObject> MagasinBobineParetoMDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MagBobParetoRange(date1, date2);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoMagasinBobineMDTThisMonth")
        public List<JSONObject> MagasinBobineParetoMDTThisMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MagBobParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoMagasinBobineMDTLastMonth")
        public List<JSONObject> MagasinBobineParetoMDTLastMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MagBobParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoMassicotTDTRange")
        public List<JSONObject> MassicotParetoTDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MassicotParetoRange(date1, date2);

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
        
        @GetMapping("/paretoMassicotTDTThisMonth")
        public List<JSONObject> MassicotParetoTDTThisMonth(){

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
            List<JSONObject> pty = departementRepository.MassicotParetoThisMonth(mts);

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
        
        @GetMapping("/paretoMassicotTDTLastMonth")
        public List<JSONObject> MassicotParetoTDTLastMonth(){

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
            List<JSONObject> pty = departementRepository.MassicotParetoThisMonth(mts);

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
        
        @GetMapping("/paretoMassicotMDTRange")
        public List<JSONObject> MassicotParetoMDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MassicotParetoRange(date1, date2);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoMassicotMDTThisMonth")
        public List<JSONObject> MassicotParetoMDTThisMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MassicotParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoMassicotMDTLastMonth")
        public List<JSONObject> MassicotParetoMDTLastMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.MassicotParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        
        @GetMapping("/paretoSechoirTDTRange")
        public List<JSONObject> SechoirParetoTDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.SechoirParetoRange(date1, date2);

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
        
        @GetMapping("/paretoSechoirTDTThisMonth")
        public List<JSONObject> SechoirParetoTDTThisMonth(){

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
            List<JSONObject> pty = departementRepository.SechoirParetoThisMonth(mts);

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
        
        @GetMapping("/paretoSechoirTDTLastMonth")
        public List<JSONObject> SechoirParetoTDTLastMonth(){

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
            List<JSONObject> pty = departementRepository.SechoirParetoThisMonth(mts);

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
        
        @GetMapping("/paretoSechoirMDTRange")
        public List<JSONObject> SechoirParetoMDTRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.SechoirParetoRange(date1, date2);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoSechoirMDTThisMonth")
        public List<JSONObject> SechoirParetoMDTThisMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.SechoirParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoSechoirMDTLastMonth")
        public List<JSONObject> SechoirParetoMDTLastMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.SechoirParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoTrancheuseTDTRange")
        public List<JSONObject> TrancheuseParetoTDTThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.TrancheuseParetoRange(date1, date2);

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
        
        @GetMapping("/paretoTrancheuseTDTThisMonth")
        public List<JSONObject> TrancheuseParetoTDTThisMonth(){

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
            List<JSONObject> pty = departementRepository.TrancheuseParetoThisMonth(mts);

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
        
        @GetMapping("/paretoTrancheuseTDTLastMonth")
        public List<JSONObject> TrancheuseParetoTDTLastMonth(){

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
            List<JSONObject> pty = departementRepository.TrancheuseParetoThisMonth(mts);

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
        
        @GetMapping("/paretoTrancheuseMDTRange")
        public List<JSONObject> TrancheuseParetoMDTThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.TrancheuseParetoRange(date1, date2);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoTrancheuseMDTThisMonth")
        public List<JSONObject> TrancheuseParetoMDTThisMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.TrancheuseParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoTrancheuseMDTLastMonth")
        public List<JSONObject> TrancheuseParetoMDTLastMonth(){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.TrancheuseParetoThisMonth(mts);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        
        @GetMapping("/paretoEncolleuseTDTRange/{dep}")
        public List<JSONObject> EncolleuseParetoTDTThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2, @PathVariable Long dep){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> pty = departementRepository.EncolleuseParetoRange(date1, date2, dep);

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
        
        @GetMapping("/paretoEncolleuseTDTThisMonth/{dep}")
        public List<JSONObject> EncolleuseParetoTDTThisMonth(@PathVariable Long dep){

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
            List<JSONObject> pty = departementRepository.EncolleuseParetoThisMonth(mts, dep);

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
        
        @GetMapping("/paretoEncolleuseTDTLastMonth/{dep}")
        public List<JSONObject> EncolleuseParetoTDTLastMonth(@PathVariable Long dep){

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
            List<JSONObject> pty = departementRepository.EncolleuseParetoThisMonth(mts, dep);

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
        
        @GetMapping("/paretoEncolleuseMDTRange/{dep}")
        public List<JSONObject> EncolleuseParetoMDTThisYear(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2, @PathVariable Long dep){

            List<JSONObject> MTBF = new ArrayList<>();
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.EncolleuseParetoRange(date1, date2, dep);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoEncolleuseMDTThisMonth/{dep}")
        public List<JSONObject> EncolleuseParetoMDTThisMonth(@PathVariable Long dep){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.EncolleuseParetoThisMonth(mts, dep);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
        @GetMapping("/paretoEncolleuseMDTLastMonth/{dep}")
        public List<JSONObject> EncolleuseParetoMDTLastMonth(@PathVariable Long dep){

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
            List<JSONObject> MTBF2 = new ArrayList<>();
            List<JSONObject> pty = departementRepository.EncolleuseParetoThisMonth(mts, dep);

            Map<String,Object> response = new HashMap<>();
         Map<String,Object> response2 = new HashMap<>();
         
         List<JSONObject> nbre = new ArrayList<>();
         List<JSONObject> tdth = new ArrayList<>();
         List<JSONObject> nbre2 = new ArrayList<>();
         List<JSONObject> tdth2 = new ArrayList<>();
         
        Map<String, Integer> result = pty.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((BigInteger)t.get("nbre")).intValue()))); 
        
        result.entrySet().stream()
            .forEach(date -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", date.getKey());
            response2.put("nbre", date.getValue().intValue());
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
            response2.put("TDT", dates.getValue().intValue());
            json = new JSONObject(response2);
            tdth.add(json);            
            });
        System.out.println("trdt\n " + tdth);
        
        tdth.forEach(nb->{
            nbre.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", Integer.parseInt(td.get("nbre").toString()));
                            response.put("MDT", Double.parseDouble(nb.get("TDT").toString())/Double.parseDouble(td.get("nbre").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF.add(json2);
        });
                        System.out.println("final \n" + MTBF);
                        
        
                        
        Map<String, Integer> result2 = MTBF.stream().collect(
            Collectors.groupingBy(e -> e.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingInt(t -> ((Integer)t.get("nbre")).intValue()))); 
        
        result2.entrySet().stream()
            .forEach(dater -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dater.getKey());
            response2.put("nbre", dater.getValue().intValue());
            json2 = new JSONObject(response2);
            nbre2.add(json2);            
            });
        
        System.out.println("nbre2\n " + nbre2);
        
        Map<String, Double> tdtd2 = MTBF.stream().collect(
            Collectors.groupingBy(f -> f.get("nom").toString(),
            LinkedHashMap::new,
            Collectors.summingDouble(g -> ((Double)g.get("MDT")).doubleValue()))); 
        
        tdtd2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(dates2 -> {
//                System.out.println("test de date " + date.getKey() + " = " + date.getValue()); 
            response2.put("nom", dates2.getKey());
            response2.put("MDT", dates2.getValue().intValue());
            json = new JSONObject(response2);
            tdth2.add(json);            
            });
        System.out.println("trdt2\n " + tdth);
        
        tdth2.forEach(nb->{
            nbre2.forEach(td->{
                        String h = String.valueOf(nb.get("nom"));
                        String m = String.valueOf(td.get("nom"));
                        
                        if(h.equals(m) ){
                            response.put("nom", h);
                            response.put("nbre", String.valueOf(td.get("nbre")));
                            response.put("MDT", (nb.get("MDT").toString()));
                            json2 = new JSONObject(response);
                        }
            });
            MTBF2.add(json2);
        });
                        System.out.println("final2 \n" + MTBF2);                
                    
            return MTBF2;
        }
        
    
    @GetMapping("/dashboard/{dep}")
    public List<JSONObject> test(@PathVariable Long dep){
        date1 = LocalDate.now();
        date2 = date1.minusMonths(1);
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = departementRepository.dashboard(date2, date1, dep);
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
    
    @GetMapping("/dashboardRange/{dep}")
    public List<JSONObject> dashboardRange(@PathVariable Long dep, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){
        
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = departementRepository.dashboard(date1, date2, dep);
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
    
    @GetMapping("/dashboardThisMonth/{dep}")
    public List<JSONObject> dashboardThisMonth(@PathVariable Long dep){
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
            
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = departementRepository.dashboardThisMonth(mts, dep);
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
    
    @GetMapping("/dashboardLastMonth/{dep}")
    public List<JSONObject> dashboardLastMonth(@PathVariable Long dep){
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
            
        List<JSONObject> dash = new ArrayList<>();
        List<JSONObject> test = departementRepository.dashboardThisMonth(mts, dep);
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
}
