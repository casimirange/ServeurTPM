package com.example.demo.service.inter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;


public interface IDepartementService {

	public List<Departement> allDepartements();
	
	public Departement findOne(Long id);
	
	void addDepartement(Departement departement);
	
	void updateDep(Departement departement);
	
	void deleteDep(Long id);
	
}
