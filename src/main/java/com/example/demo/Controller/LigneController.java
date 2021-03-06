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

import com.example.demo.entity.Lignes;
import com.example.demo.model.LigneModel;
import com.example.demo.reponses.LignesReponse;
import com.example.demo.repository.LigneRepository;
import com.example.demo.service.inter.ILigneService;
import net.minidev.json.JSONObject;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/lignes")
public class LigneController {

	@Autowired
	private ILigneService ligneService;
	
	@Autowired
	private LigneRepository ligneRepository;
	
	@GetMapping("/all")
	public List<Lignes> getLignes(){
		return ligneService.getAllLignes();
	}
	
	@GetMapping()
	public List<JSONObject> ToutesLesLignes(){
		return ligneRepository.ToutesLesLignes();
	}
	
	@GetMapping("/{id}")
	public Lignes getById(@PathVariable Long id){
		return ligneService.findBy(id);
	}
	
	@PostMapping
	public ResponseEntity<String> creationLigne(@RequestBody LigneModel ligneModel) {
		Lignes ligne = new Lignes(ligneModel.getNomLigne());
		ligneService.addLigne(ligne,ligneModel.getIdDepartement());
		return new ResponseEntity<>("sss",HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<String> updateLignes(@RequestBody LigneModel ligneModel) {
		Lignes ligne = new Lignes();
                ligne = ligneService.findBy(ligneModel.getIdLigne());
                ligne.setNomLigne(ligneModel.getNomLigne());
		ligneService.updateLigne(ligne, ligneModel.getIdDepartement());
		return new ResponseEntity<>("sss",HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public void deletLignes(@PathVariable Long id) {
		ligneService.deleteLigne(id);
	}
	
}
