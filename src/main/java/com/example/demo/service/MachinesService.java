package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.entity.Machines;
import com.example.demo.entity.Techniciens;
import com.example.demo.repository.LigneRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.TechnicienRepository;
import com.example.demo.service.inter.IMachineService;
import com.example.demo.service.inter.ITechnicienService;

@Service
public class MachinesService implements IMachineService {

	@Autowired
	private MachineRepository machineRepository ;
	
	@Autowired
	private LigneRepository ligneRepository;
	

	public MachineRepository getMachineRepository() {
		return machineRepository;
	}

	public void setMachineRepository(MachineRepository machineRepository) {
		this.machineRepository = machineRepository;
	}

	@Override
	public List<Machines> allMachines() {
		return machineRepository.findAll();
	}

	@Override
	public Machines findOne(Long code) {
		return machineRepository.findById(code).get();
	}

	@Override
	public void addMachine(Machines machine, Long id_ligne) {
		Lignes ligne = ligneRepository.getOne(id_ligne);
		machine.setLignes(ligne);
		machineRepository.save(machine);
	}

	@Override
	public void updateMachine(Machines machine, Long id_ligne) {
		Lignes ligne = ligneRepository.getOne(id_ligne);
		machine.setLignes(ligne);
		machineRepository.save(machine);
	}

	@Override
	public void deleteMachine(Long code) {
		Machines machines = new Machines();
		machines.setIdMachine(code);
		machineRepository.delete(machines);
	}

}
