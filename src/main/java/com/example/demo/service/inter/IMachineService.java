package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Machines;

public interface IMachineService {
	
	public List<Machines> allMachines();
	
	public Machines findOne(Long code);
	
	void addMachine(Machines machine, Long id_ligne);
	
	void updateMachine(Machines machine, Long id_ligne);
	
	void deleteMachine(Long c);

}
