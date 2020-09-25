package com.example.demo.Controller;

import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
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

import com.example.demo.entity.Pannes;
import com.example.demo.entity.Techniciens;
import com.example.demo.helper.panExcelHelper;
import com.example.demo.message.response.ResponseMessage;
import com.example.demo.model.PanneModel;
//import com.example.demo.reponses.CountPannesResponse;
import com.example.demo.reponses.PannesHeureReponse;
import com.example.demo.reponses.PannesTechReponse;
import com.example.demo.repository.PanneRepository;
import com.example.demo.service.ExcelService;
import com.example.demo.service.PannesService;
import com.example.demo.service.inter.IPanneService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/pannes")
public class PanneController {

	@Autowired
	private IPanneService panneService;
        @Autowired
        private PannesService ps;
        @Autowired
        private PanneRepository panneRepository;
        @Autowired
        ExcelService fileService;
        
        LocalDate date1, date2 ;
        JSONObject json, json2, json3, json4;
        String mts;
	
	@GetMapping
	public List<Pannes> getPannes(){
		return panneRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
	}
        
        @GetMapping("/all")
	public List<JSONObject> ToutesLesPannes(){
            List<JSONObject> Allpannes = panneRepository.ToutesLesPannes();
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
        
        @GetMapping("/tech/{numero}")
	public List<JSONObject> findTechByNum(@PathVariable String numero){
		return panneRepository.Techs(numero);
	}
        
        @GetMapping("/operateur/{numero}")
	public List<JSONObject> findOpByNum(@PathVariable String numero){
		return panneRepository.Ops(numero);
	}
        
        @GetMapping("/details/{numero}")
	public List<JSONObject> findDetailsByNum(@PathVariable String numero){
		return panneRepository.Details(numero);
	}
        
        @GetMapping("/outils/{numero}")
	public List<JSONObject> findOutilsByNum(@PathVariable String numero){
		return panneRepository.Outils(numero);
	}
        
        @GetMapping("/heure/{numero}")
	public List<JSONObject> findHeureByNum(@PathVariable String numero){
		return panneRepository.Heures(numero);
	}
        
        @GetMapping("/today")
	public List<JSONObject> today(){
            LocalDate date = LocalDate.now();		 
            List<JSONObject> Allpannes = panneRepository.ToDayPannes(date);
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
        
        @GetMapping("/countoday")
	public List<JSONObject> ctoday(){
            LocalDate date = LocalDate.now();
		return panneRepository.TotalLtodayPannes(date);
	}
        
        @GetMapping("/hier")
	public List<JSONObject> hier(){
            LocalDate date = LocalDate.now().minusDays(1);		 
            List<JSONObject> Allpannes = panneRepository.ToDayPannes(date);
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
        
        @GetMapping("/csem")
	public List<JSONObject> thisWeek(){
            
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
            		 
            List<JSONObject> Allpannes = panneRepository.WeekPannes(date1, date2);
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
        
        @GetMapping("/semp")
	public List<JSONObject> lastWeek(){
            
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
            
            List<JSONObject> Allpannes = panneRepository.WeekPannes(date1, date2);
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
        
        @GetMapping("/Date_range")
	public List<JSONObject> dateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){            
//            return panneRepository.WeekPannes(date1, date2);
            List<JSONObject> Allpannes = panneRepository.WeekPannes(date1, date2);
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
                
        @GetMapping("/thisMonth")
	public List<JSONObject> thisMonth(){
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
            List<JSONObject> Allpannes = panneRepository.MonthPannes(mts);
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
        
        @GetMapping("/lastMonth")
	public List<JSONObject> lastMonth(){
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
                List<JSONObject> Allpannes = panneRepository.MonthPannes(mts);
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
        
        @GetMapping("/thisYear")
	public List<JSONObject> thisYear(){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
            String mt = String.valueOf(year);
            System.out.println("année: "+ year);
            
//		return panneRepository.YearPannes(mt);
                List<JSONObject> Allpannes = panneRepository.YearPannes(mt);
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
        
        @GetMapping("/lastYear")
	public List<JSONObject> lastYear(){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
            String mt = String.valueOf(year-1);
            System.out.println("an passé: "+ mt);
//		return panneRepository.YearPannes(mt);
                List<JSONObject> Allpannes = panneRepository.YearPannes(mt);
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
        
        @GetMapping("/heure")
	public List<PannesHeureReponse> findHeureByNu(){
		return panneRepository.Heure();
	}
        
        
        @GetMapping("/{numero}")
	public JSONObject findPanne(@PathVariable String numero){
		return panneRepository.findPanne(numero);
	}
        
        @GetMapping("/p/{numero}")
	public List<Pannes> findPannes(@PathVariable String numero){
		return panneRepository.findByNumero(numero);
	}

        @GetMapping("/unfinished")
	public List<JSONObject> PannesNonAchevees(){
		return panneRepository.PannesNonAchevees();
	} 
        
        @GetMapping("/countThisMonth")
        public List<JSONObject> countAllThisMonth(){
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
            return panneRepository.TotalLPannes(mts);
        } 
        
        @GetMapping("/countLastMonth")
        public List<JSONObject> countAllLastMonth(){
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
            return panneRepository.TotalLPannes(mts);
        } 
        
        @GetMapping("/countRangeMonth")
        public List<JSONObject> countAllRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){
            
            return panneRepository.TotalRangePannes(date1, date2);
        } 
        
        @GetMapping("/countDep")
        public List<JSONObject> countDepAll(){
            return panneRepository.TotallDepPannes();
        }
	
	@PostMapping
	public void creationPanne(@RequestBody PanneModel panneModel) {
            
		Pannes panne = new Pannes(
                        panneModel.getCause(), 
                        panneModel.getDetails(), 
                        panneModel.getDescription(), 
                        panneModel.getOutil(),
                        panneModel.getRef(),
                        panneModel.getQte(),
                        panneModel.getDate(), 
                        panneModel.getHeureArret(), 
                        panneModel.getDebutInter(), 
                        panneModel.getFinInter(), 
                        panneModel.isEtat(), 
                        panneModel.isCont(),
                        panneModel.getQuart(),
                        panneModel.getNumero(),
                        panneModel.getDT(),
                        panneModel.getWT(),
                        panneModel.getTTR()
                );
		panneService.addPanne(panne, panneModel.getIdMachine(), panneModel.getIdOperateur(), panneModel.getIdTechnicien());
		
//		return new ResponseEntity<>(panne,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Pannes> continuePanne(@RequestBody PanneModel panneModel) {
            List<Pannes> pan = new ArrayList<Pannes>();
            pan = panneRepository.findByNumero(panneModel.getNumero());
            Pannes panne = new Pannes();
            for(int i = 0; i< pan.size(); i++){
                panne = pan.get(i);
                panne.setCause(panneModel.getCause());
                panne.setDetails(panneModel.getDetails());
                panne.setDescription(panneModel.getDescription());
                panne.setOutil(panneModel.getOutil());
                panne.setRef(panneModel.getRef());
                panne.setQte(panneModel.getQte());
                panne.setDate(panneModel.getDate());
                panne.setHeure_arret(panneModel.getHeureArret());
                panne.setDebut_inter(panneModel.getDebutInter());
                panne.setFin_inter(panneModel.getFinInter());
                panne.setEtat(panneModel.isEtat());
                panne.setCont(panneModel.isCont());
                panne.setQuart(panneModel.getQuart());
                
                panneService.addPanne(panne, panneModel.getIdMachine(), panneModel.getIdOperateur(), panneModel.getIdTechnicien());
            }
//		Pannes pannes = new Pannes(
//                        panneModel.getCause(), 
//                        panneModel.getDetails(), 
//                        panneModel.getDescription(), 
//                        panneModel.getOutil(),
//                        panneModel.getRef(),
//                        panneModel.getQte(),
//                        panneModel.getDate(), 
//                        panneModel.getHeureArret(), 
//                        panneModel.getDebutInter(), 
//                        panneModel.getFinInter(), 
//                        panneModel.isEtat(), 
//                        panneModel.isCont(),
//                        panneModel.getQuart(),
//                        panneModel.getNumero(),
//                        panneModel.getDT(),
//                        panneModel.getWT(),
//                        panneModel.getTTR()
//                );
//		panneService.addPanne(panne, panneModel.getIdMachine(), panneModel.getIdOperateur(), panneModel.getIdTechnicien());

		return new ResponseEntity<>(panne,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{numero}")
	public void deletePanne(@PathVariable String numero) {
            List<Pannes> pan = new ArrayList<>();
            pan = panneRepository.findByNumero(numero);
            for(int i=0; i< pan.size(); i++){                
                panneRepository.delete(pan.get(i));
            }
	}
	
	@DeleteMapping("/tech/{idPanne}")
	public void deleteTechnicienPanne(@PathVariable Long idPanne) {
            Pannes pan = panneService.findOne(idPanne);              
                panneRepository.delete(pan);
	}
        
        @PutMapping("/{numero}")
	public void activePanne(@PathVariable String numero) {
	    List<Pannes> pan = new ArrayList<>();            
            for(int i=0; i< panneRepository.findByNumero(numero).size(); i++){
                pan = panneRepository.findByNumero(numero);
                if(!pan.get(i).isEtat()){
                    pan.get(i).setEtat(true); 
                }
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/etat/{numero}")
	public void updateEtatPanne(@PathVariable String numero) {
	    List<Pannes> pan = new ArrayList<>();            
            for(int i=0; i< panneRepository.findByNumero(numero).size(); i++){
                pan = panneRepository.findByNumero(numero);
                if(!pan.get(i).isEtat()){
                    pan.get(i).setEtat(true); 
                    pan.get(i).setCont(true);
                }else{
                    pan.get(i).setEtat(false); 
                    pan.get(i).setCont(false);
                }
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/periode/{numero}/{quart}")
	public void updatePériodePanne(@PathVariable String numero,@PathVariable int quart, 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("heureArret") LocalDateTime heureArret,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("debutInter") LocalDateTime debutInter,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("finInter") LocalDateTime finInter
        ) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumeroAndQuart(numero, quart);         
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setHeure_arret(heureArret);
                    pan.get(i).setDebut_inter(debutInter);
                    pan.get(i).setFin_inter(finInter);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/outils/{numero}/{quart}")
	public void updateOutilsPanne(@PathVariable String numero,@PathVariable int quart, 
                @RequestParam("outil") String outil,
                @RequestParam("qte") int qte,
                @RequestParam("ref") String ref
        ) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumeroAndQuart(numero, quart);         
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setOutil(outil);
                    pan.get(i).setQte(qte);
                    pan.get(i).setRef(ref);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/date/{numero}")
	public void updateDatePanne(@PathVariable String numero, 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date
        ) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumero(numero);         
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setDate(date);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/description/{numero}")
	public void updateDescriptionPanne(@PathVariable String numero, @RequestParam("description") String description) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumero(numero);         
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setDescription(description);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/cause/{numero}/{quart}")
	public void updateCausePanne(@PathVariable String numero, @PathVariable int quart, @RequestParam("cause") String cause) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumeroAndQuart(numero, quart);              
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setCause(cause);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/details/{numero}/{quart}")
	public void updateDetailsPanne(@PathVariable String numero, @PathVariable int quart, @RequestParam("details") String details) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumeroAndQuart(numero, quart);            
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setDetails(details);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/operateur/{numero}/{quart}")
	public void updateOperateurPanne(@PathVariable String numero, @PathVariable int quart, @RequestParam("operateur") Operateurs op) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumeroAndQuart(numero, quart);            
            for(int i=0; i< pan.size(); i++){ 
                    pan.get(i).setOperateurs(op);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PutMapping("/machine/{numero}")
	public void updateMachinePanne(@PathVariable String numero, @RequestParam("machine") Machines mach) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumero(numero);            
            for(int i=0; i< pan.size(); i++){ 
                pan.get(i).setMachines(mach);
                panneRepository.save(pan.get(i));
            }        
	}
        
        @PostMapping("/technicien/{numero}/{quart}")
	public void addTechnicienPanne(@PathVariable String numero, @PathVariable int quart, @RequestParam("tec") Techniciens tec) {
	    List<Pannes> pan = new ArrayList<>();   
            pan = panneRepository.findByNumeroAndQuart(numero, quart); 
            Pannes panneModel = pan.get(0);
            Pannes panne = new Pannes(
                        panneModel.getCause(), 
                        panneModel.getDetails(), 
                        panneModel.getDescription(), 
                        panneModel.getOutil(),
                        panneModel.getRef(),
                        panneModel.getQte(),
                        panneModel.getDate(), 
                        panneModel.getHeure_arret(), 
                        panneModel.getDebut_inter(), 
                        panneModel.getFin_inter(), 
                        panneModel.isEtat(), 
                        panneModel.isCont(),
                        panneModel.getQuart(),
                        panneModel.getNumero(),
                        panneModel.getDT(),
                        panneModel.getWT(),
                        panneModel.getTTR()
                );
	    panneService.addPanne(panne, panneModel.getMachines().getIdMachine(), panneModel.getOperateurs().getIdOperateur(), tec.getIdTechnicien());
		  
	}
        
        @PostMapping("/upload")
        public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
          String message = "";
          if (new panExcelHelper().hasExcelFormat(file)) {
            try {
              fileService.savePanne(file);
              message = "Uploaded the file successfully: " + file.getOriginalFilename();
              return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
              message = "Could not upload the file: " + file.getOriginalFilename() + "!";
              return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
          }

          message = "Please upload an excel file!";
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        
        @GetMapping("/download")
        public ResponseEntity<org.springframework.core.io.Resource> getFile() {
          String filename = "tutorials.csv";
          InputStreamResource file = new InputStreamResource(fileService.loadPanne());

          return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
              .contentType(MediaType.parseMediaType("application/csv"))
              .body(file);
        }
}
