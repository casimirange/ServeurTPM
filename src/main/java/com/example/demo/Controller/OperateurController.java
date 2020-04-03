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

import com.example.demo.entity.Operateurs;
import com.example.demo.service.inter.IOperateurService;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/operateurs")
public class OperateurController {
	
	@Autowired
	private IOperateurService opService;
	
	@GetMapping
	public List<Operateurs> getOperateurs(){
		return opService.allOperateurs();
	}
	
	@GetMapping("/{id}")
	public Operateurs getById(@PathVariable Long id){
		return opService.findOne(id);
	}
	
	@PostMapping
	public void addOperateur(@RequestBody Operateurs operateur) {
		opService.addOperateur(operateur);
	}
	
	@PutMapping
	public void updateOperateur(@RequestBody Operateurs operateur) {
		opService.updateOperateur(operateur);
	}
	
	@DeleteMapping("/{matricule}")
	public void deleOperateur(@PathVariable Long matricule) {
		opService.deleteOperateur(matricule);
	}
	

}
