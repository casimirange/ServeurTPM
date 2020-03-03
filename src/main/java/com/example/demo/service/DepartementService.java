package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Departement;
import com.example.demo.repository.DepartementRepository;
import com.example.demo.service.inter.IDepartementService;

@Service
@Primary  //puisque on a deux services qui implémentent la mm onterface, cett annotation stipule que ce service soit prioritaire
public class DepartementService implements IDepartementService {

	
	@Autowired
	private DepartementRepository departementRepository;
	
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
		departement.setId(id);
		departementRepository.delete(departement);
	}

}
