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

import com.example.demo.entity.Techniciens;
import com.example.demo.model.TechnicienModel;
import com.example.demo.repository.TechnicienRepository;
import com.example.demo.service.inter.ITechnicienService;
import net.minidev.json.JSONObject;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/techniciens")
public class TechnicienController {
	
	@Autowired
	private ITechnicienService techService;
        
        @Autowired
        private TechnicienRepository technicienRepository;
	
	@GetMapping
	public List<JSONObject> getTechniciens(){
		return technicienRepository.AllTech();
	}
        
        @GetMapping("/active")
	public List<JSONObject> activeTech(){
		return technicienRepository.ActivatedTech();
	}
        
        @GetMapping("/desactive")
	public List<JSONObject> desactiveTech(){
		return technicienRepository.DesactivatedTech();
	}
	
	@GetMapping("/{matricule}")
	public Techniciens getById(@PathVariable Long matricule){
		return techService.findByMat(matricule);
	}
	
	@PostMapping
	public void addTechnicien(@RequestBody Techniciens technicien) {
            technicien.setEtat(true);
		techService.addTechnicien(technicien);
	}
	
	@PutMapping
	public void updateTechnicien(@RequestBody TechnicienModel technicienModel) {
            Techniciens technicien = new Techniciens();
            technicien = techService.findOne(technicienModel.getIdTechnicien());
            technicien.setNom(technicienModel.getNom());
            technicien.setMatricule(technicienModel.getMatricule());
            technicien.setFonction(technicienModel.getFonction());
            technicien.setPrenom(technicienModel.getPrenom());                
            technicien.setEtat(technicienModel.isEtat());
            technicien.setLocalisation("bonaberi");
            techService.updateTech(technicien);
	}
	
	@DeleteMapping("/{matricule}")
	public void deleTechnicien(@PathVariable Long matricule) {
		techService.deleteTech(matricule);
	}
	
        @PutMapping("/{matricule}")
	public void desactiveTechnicien(@PathVariable Long matricule) {
	    Techniciens tech = new Techniciens();
        tech = techService.findByMat(matricule);
        if(tech.isEtat()){
            tech.setEtat(false);
        }else{
            tech.setEtat(true);
        }
            techService.updateTech(tech);
	}

}
