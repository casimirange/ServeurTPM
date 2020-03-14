package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.repository.DepartementRepository;
import com.example.demo.repository.LigneRepository;
import com.example.demo.service.inter.ILigneService;

@Service
public class LigneService implements ILigneService {

	
	@Autowired
	private LigneRepository ligneRepository;
    @Autowired
    DepartementRepository departementRepository;
    
	@Override
	public List<Lignes> allLignes() {
		return ligneRepository.findAll();
	}

	@Override
	public void addLigne(Lignes ligne,Long id_dep) {
		Departement dep = departementRepository.getOne(id_dep);
		ligne.setDepartement(dep);
		ligneRepository.save(ligne);
	}

	@Override
	public void deleteLigne(Long id) {
		Lignes ligne = new Lignes();
		ligne.setIdLigne(id);
		ligneRepository.delete(ligne);
	}

	@Override
	public Lignes findBy(Long id) {
		return ligneRepository.findById(id).get();
	}

	@Override
	public void updateLigne(Lignes ligne,Long id_dep) {
		Departement dep = departementRepository.getOne(id_dep);
		ligne.setDepartement(dep);
		ligneRepository.save(ligne);
	}

}
