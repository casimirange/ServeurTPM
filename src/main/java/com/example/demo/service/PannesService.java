package com.example.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
import com.example.demo.entity.Outils;
import com.example.demo.entity.Pannes;
import com.example.demo.entity.Techniciens;
import com.example.demo.reponses.PSR;
import com.example.demo.reponses.PannesNonAcheveesReponse;
import com.example.demo.reponses.PannesReponse;
import com.example.demo.reponses.PannesHeureReponse;
import com.example.demo.reponses.PannesTechReponse;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.OperateurRepository;
import com.example.demo.repository.OutilRepository;
import com.example.demo.repository.PanneRepository;
import com.example.demo.repository.TechnicienRepository;
import com.example.demo.service.inter.IPanneService;
import net.minidev.json.JSONObject;

@Service
public class PannesService implements IPanneService {

	
	@Autowired
	private PanneRepository panneRepository ;


	@Autowired
	private MachineRepository machineRepository;
	
	@Autowired
	private TechnicienRepository technicienRepository;
	
	@Autowired
	private OperateurRepository operateurRepository;
	
	@Autowired
	private OutilRepository outilRepository;

	@Override
	public List<Pannes> allPannes() {
		return panneRepository.findAll();
	}
        
	@Override
	public List<JSONObject> toutesPannes() {
		return panneRepository.ToutesLesPannes();
	}               
	
	public List<JSONObject> FindByNum(String numero) {
		return panneRepository.Techs(numero);
	}
        
	public List<JSONObject> PannesNonAchevees() {
		return panneRepository.PannesNonAchevees();
	}

	@Override
	public Pannes findOne(Long numero) {
		return panneRepository.findById(numero).get();
	}

	@Override
	public void addPanne(Pannes panne, Long code_machine, Long id_oper, Long id_tech) {
		Machines machine = machineRepository.getOne(code_machine);
		Operateurs oper = operateurRepository.getOne(id_oper);
		//Outils outil = outilRepository.getOne(id_outil);
		Techniciens tech = technicienRepository.getOne(id_tech);
		
		panne.setMachines(machine);
		panne.setOperateurs(oper);
		panne.setTechniciens(tech);
		
//		Random rand = new Random();
//		int upperbound = 1000000;
		
//		panne.setNumero(rand.nextInt(upperbound));	
		panneRepository.save(panne);
		
	}		

	@Override
	public void updatePanne(Pannes panne, Long code_machine, Long id_oper, Long id_tech) {
		Machines machine = machineRepository.getOne(code_machine);
		Operateurs oper = operateurRepository.getOne(id_oper);
		//Outils outil = outilRepository.getOne(id_outil);
		Techniciens tech = technicienRepository.getOne(id_tech);
		
		panne.setMachines(machine);
		panne.setOperateurs(oper);
		panne.setTechniciens(tech);
		
//		Random rand = new Random();
//		int upperbound = 1000000;
		
//		panne.setNumero(rand.nextInt(upperbound));	
		panneRepository.save(panne);
	}

	@Override
	public void deletePanne(String numero) {
		Pannes panne = new Pannes();
		panne.setNumero(numero);
		panneRepository.delete(panne);
	}

    @Override
    public JSONObject showPanne(String numero) {
        return panneRepository.findPanne(numero);
    }



}
