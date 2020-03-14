package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Techniciens;

public interface ITechnicienService {

	public List<Techniciens> allTechniciens();
	
	public Techniciens findOne(Long matricule);
	
	void addTechnicien(Techniciens technicien);
	
	void updateTech(Techniciens technicien);
	
	void deleteTech(Long matricule);
	
}
