package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Departement;

public interface IDepartementService {

	List<Departement> allDepartements();
	
	void addDepartement(Departement departement);
	
	void updateDep(Departement departement);
	
	void deleteDep(Long id);
	
}
