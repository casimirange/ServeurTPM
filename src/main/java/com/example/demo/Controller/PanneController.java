package com.example.demo.Controller;

import java.util.List;

import javax.persistence.Query;

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

import com.example.demo.entity.Lignes;
import com.example.demo.entity.Pannes;
import com.example.demo.model.LigneModel;
import com.example.demo.model.PanneModel;
import com.example.demo.reponses.CountPannesResponse;
import com.example.demo.reponses.LignesReponse;
import com.example.demo.reponses.PSR;
import com.example.demo.reponses.PannesNonAcheveesReponse;
import com.example.demo.reponses.PannesReponse;
import com.example.demo.reponses.PannesHeureReponse;
import com.example.demo.reponses.PannesTechReponse;
import com.example.demo.repository.PanneRepository;
import com.example.demo.service.PannesService;
import com.example.demo.service.inter.IPanneService;
import java.time.LocalDate;
import java.time.Month;
import static java.time.temporal.TemporalQueries.zone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

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
        
        LocalDate date1, date2 ;
        String mts;
	
	@GetMapping
	public List<Pannes> getPannes(){
		return panneRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
	}
        
        @GetMapping("/all")
	public List<JSONObject> ToutesLesPannes(){
		return panneRepository.ToutesLesPannes();
	}
        
        @GetMapping("/tech/{numero}")
	public List<PannesTechReponse> findTechByNum(@PathVariable String numero){
		return panneRepository.Techs(numero);
	}
        
        @GetMapping("/heure/{numero}")
	public List<PannesHeureReponse> findHeureByNum(@PathVariable String numero){
		return panneRepository.Heures(numero);
	}
        
        @GetMapping("/today")
	public List<JSONObject> today(){
            LocalDate date = LocalDate.now();
		return panneRepository.ToDayPannes(date);
	}
        
        @GetMapping("/countoday")
	public List<CountPannesResponse> ctoday(){
            LocalDate date = LocalDate.now();
		return panneRepository.TotalLtodayPannes(date);
	}
        
        @GetMapping("/hier")
	public List<JSONObject> hier(){
            LocalDate date = LocalDate.now().minusDays(1);
		return panneRepository.ToDayPannes(date);
	}
        
        @GetMapping("/csem")
	public List<JSONObject> thisWeek(){
            
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("jour de la semaine: "+week);
            if(week == 1){
                date1 = LocalDate.now().minusDays(7);
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
            return panneRepository.WeekPannes(date1, date2);
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
            return panneRepository.WeekPannes(date1, date2);
	}
        
        @GetMapping("/Date_range")
	public List<JSONObject> dateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("debut") LocalDate date1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fin") LocalDate date2){            
            return panneRepository.WeekPannes(date1, date2);
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
            
		return panneRepository.MonthPannes(mts);
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
		return panneRepository.MonthPannes(mts);
	}
        
        @GetMapping("/thisYear")
	public List<JSONObject> thisYear(){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
            String mt = String.valueOf(year);
            System.out.println("année: "+ year);
            
		return panneRepository.YearPannes(mt);
	}
        
        @GetMapping("/lastYear")
	public List<JSONObject> lastYear(){
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
            String mt = String.valueOf(year-1);
            System.out.println("an passé: "+ mt);
		return panneRepository.YearPannes(mt);
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
	public List<PannesNonAcheveesReponse> PannesNonAchevees(){
		return panneRepository.PannesNonAchevees();
	} 
        
        @GetMapping("/count")
        public List<CountPannesResponse> countAll(){
            return panneRepository.TotalLPannes();
        } 
        
        @GetMapping("/countDep")
        public List<CountPannesResponse> countDepAll(){
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
                        panneModel.getNumero(),
                        panneModel.getDT(),
                        panneModel.getWT(),
                        panneModel.getTTR()
                );
		panneService.addPanne(panne, panneModel.getIdMachine(), panneModel.getIdOperateur(), panneModel.getIdTechnicien());

		return new ResponseEntity<>(panne,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{code}")
	public void deleMachine(@PathVariable Long numero) {
		panneService.deletePanne(numero);
	}
        
        @PutMapping("/{numero}")
	public void activePanne(@PathVariable String numero) {
	    List<Pannes> pan = new ArrayList<>();
            PanneModel panneModel = new PanneModel();
            
            for(int i=0; i< panneRepository.findByNumero(numero).size(); i++){
                pan = panneRepository.findByNumero(numero);
                if(!pan.get(i).isEtat()){
                    pan.get(i).setEtat(true); 
                }
                panneRepository.save(pan.get(i));
            }
        
	}
}
