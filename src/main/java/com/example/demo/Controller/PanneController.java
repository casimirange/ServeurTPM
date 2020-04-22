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
import com.example.demo.reponses.LignesReponse;
import com.example.demo.reponses.PannesReponse;
import com.example.demo.service.PannesService;
import com.example.demo.service.inter.IPanneService;
import java.util.Random;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/pannes")
public class PanneController {

	@Autowired
	private IPanneService panneService;
        @Autowired
        private PannesService ps;
	
	@GetMapping
	public List<Pannes> getPannes(){
		return ps.allPannes();
	}
        
        @GetMapping("/all")
	public List<PannesReponse> ToutesLesPannes(){
		return panneService.toutesPannes();
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
                        panneModel.getNumero());
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
                        panneModel.getNumero());
		panneService.addPanne(panne, panneModel.getIdMachine(), panneModel.getIdOperateur(), panneModel.getIdTechnicien());

		return new ResponseEntity<>(panne,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{code}")
	public void deleMachine(@PathVariable Long numero) {
		panneService.deletePanne(numero);
	}
}
