package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Techniciens;
import com.example.demo.repository.TechnicienRepository;
import com.example.demo.service.inter.ITechnicienService;

@Service
public class TechniciensService implements ITechnicienService {

	@Autowired
	private TechnicienRepository technicienRepository ;
	
	public TechnicienRepository getTechnicienRepository() {
		return technicienRepository;
	}

	public void setTechnicienRepository(TechnicienRepository technicienRepository) {
		this.technicienRepository = technicienRepository;
	}

	@Override
	public List<Techniciens> allTechniciens() {
		return technicienRepository.findAll();
	}

	@Override
	public Techniciens findOne(Long matricule) {
		return technicienRepository.findById(matricule).get();
	}

	@Override
	public void addTechnicien(Techniciens technicien) {
		technicienRepository.save(technicien);
	}

	@Override
	public void updateTech(Techniciens technicien) {
		technicienRepository.save(technicien);
	}

	@Override
	public void deleteTech(Long matricule) {
		Techniciens techniciens = new Techniciens();
		techniciens.setMatricule(matricule);
		technicienRepository.delete(techniciens);
	}

}
