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
import com.example.demo.service.inter.IMachineService;
import com.example.demo.service.inter.IOperateurService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/machines")
public class MachineController {
	
	@Autowired
	private IMachineService machineService;
	
	@GetMapping
	public List<Machines> getMachines(){
		return machineService.allMachines();
	}
	
	@GetMapping("/{code}")
	public Machines getById(@PathVariable Long code){
		return machineService.findOne(code);
	}
	
	@PostMapping
	public ResponseEntity<Machines> creationMachine(@RequestBody MachineModel machineModel) {
		Machines machine = new Machines(machineModel.getNom(), machineModel.getCode(), machineModel.getCentreCout(), machineModel.getLabel());
		machineService.addMachine(machine, machineModel.getIdLigne());
		return new ResponseEntity<>(machine,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Machines> updateMachine(@RequestBody MachineModel machineModel) {
		Machines machine = new Machines(machineModel.getNom(), machineModel.getCode(), machineModel.getCentreCout(), machineModel.getLabel());
		machineService.updateMachine(machine, machineModel.getIdLigne());
		return new ResponseEntity<>(machine,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{code}")
	public void deleMachine(@PathVariable Long code) {
		machineService.deleteMachine(code);
	}
	

}
