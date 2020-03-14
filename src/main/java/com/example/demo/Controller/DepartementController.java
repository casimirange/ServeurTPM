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
import com.example.demo.service.inter.IDepartementService;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/departements")
public class DepartementController {
	
	@Autowired
	private IDepartementService depService;
	
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
	

}
