package com.example.demo.service.inter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Lignes;

@Component
public interface ILigneService {

	//List<Lignes> allLignes();
	
	public Lignes findBy(Long id);
	
	void addLigne(Lignes ligne,Long id_dep);
	
	void updateLigne(Lignes ligne,Long id_dep);
	
	void deleteLigne(Long id);
	
	public List<Lignes> getAllLignes();
	
}
