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
import com.example.demo.entity.Techniciens;
import com.example.demo.model.OperateurModel;
import com.example.demo.repository.OperateurRepository;
import com.example.demo.service.inter.IOperateurService;
import net.minidev.json.JSONObject;

@RestController 
@CrossOrigin
@RequestMapping(value = "/api/operateurs")
public class OperateurController {
	
	@Autowired
	private IOperateurService opService;
	
	@Autowired
	private OperateurRepository operateurRepository;
	
	@GetMapping
	public List<JSONObject> getOperateurs(){
		return operateurRepository.operateurs();
	}
        
        @GetMapping("/active")
	public List<JSONObject> activeTech(){
		return operateurRepository.ActivatedOp();
	}
        
        @GetMapping("/desactive")
	public List<JSONObject> desactiveTech(){
		return operateurRepository.DesactivatedOp();
	}
        
        @PutMapping("/{matricule}")
	public void desactiveOperateur(@PathVariable Long matricule) {
	    Operateurs tech = new Operateurs();
        tech = operateurRepository.findByMatricule(matricule);
        if(tech.isEtat()){
            tech.setEtat(false);
        }else{
            tech.setEtat(true);
        }
            opService.updateOperateur(tech);
	}
	
	@GetMapping("/{id}")
	public Operateurs getById(@PathVariable Long id){
		return opService.findOne(id);
	}
	
	@PostMapping
	public void addOperateur(@RequestBody Operateurs operateur) {
            operateur.setEtat(true);
		opService.addOperateur(operateur);
	}
	
	@PutMapping
	public void updateOperateur(@RequestBody OperateurModel machineModel) {
            Operateurs machine = new Operateurs();
                machine = opService.findOne(machineModel.getIdOP());
                machine.setNom(machineModel.getNomOP());
                machine.setMatricule(machineModel.getMatOP());
                machine.setPrenom(machineModel.getPrenomOP());                
                machine.setEtat(machineModel.isEtat());
                machine.setLocalisation("bonaberi");
		opService.updateOperateur(machine);
	}
	
	@DeleteMapping("/{matricule}")
	public void deleOperateur(@PathVariable Long matricule) {
		opService.deleteOperateur(matricule);
	}
	

}
