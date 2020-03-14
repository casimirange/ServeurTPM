package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.repository.DepartementRepository;
import com.example.demo.repository.LigneRepository;
import com.example.demo.service.inter.IDepartementService;

@Service
@Primary  
public class DepartementService implements IDepartementService {

	
	@Autowired
	private DepartementRepository departementRepository;
	
	
	public DepartementRepository getDepartementRepository() {
		return departementRepository;
	}

	public void setDepartementRepository(DepartementRepository departementRepository) {
		this.departementRepository = departementRepository;
	}

	@Override
	public List<Departement> allDepartements() {
		return departementRepository.findAll();
	}

	@Override
	public void addDepartement(Departement departement) {
		departementRepository.save(departement);
	}

	@Override
	public void updateDep(Departement departement) {
		departementRepository.save(departement);
	}

	@Override
	public void deleteDep(Long id) {
		Departement departement = new Departement();
		departement.setIdDepartement(id);
		departementRepository.delete(departement);
	}

	@Override
	public Departement findOne(Long id) {
		
		return departementRepository.findById(id).get();
	}

}
