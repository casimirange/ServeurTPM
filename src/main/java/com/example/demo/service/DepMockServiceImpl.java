package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.service.inter.IDepartementService;
import com.fasterxml.jackson.databind.ser.std.IterableSerializer;

@Service
public class DepMockServiceImpl implements IDepartementService{

	private List<Departement> departements;
	
	private Departement dep;
	
	public DepMockServiceImpl() {
		departements = new ArrayList<Departement>();
		
		/*departements.add(new Departement("Brazil", "B350", "Ivan POMPEI"));
		departements.add(new Departement("Contreplaqué", "B520", "Kevin Tchintcheu"));
		departements.add(new Departement("Placage", "B300", "Bassa Amanganga"));
		departements.add(new Departement("Scierie", "B450", "Victor Sa'ah"));*/
	}
	
	@Override
	public List<Departement> allDepartements() {
		return departements;
	}

	@Override
	public void addDepartement(Departement departement) {
		departements.add(departement);
	}

	@Override
	public void updateDep(Departement departement) {
		departements.remove(departement);
		departements.add(departement);
	}

	@Override
	public void deleteDep(Long id) {
		Departement departement = new Departement();
		departement.setIdDepartement(id);
		departements.remove(departement);
	}

	@Override
	public Departement findOne(Long id) {
		
		return dep;
	}

	/*@Override
	public List<Lignes> allLignes(Departement departement) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
