package com.example.demo.service.inter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Lignes;
import com.example.demo.reponses.LignesReponse;

@Component
public interface ILigneService {

	public List<LignesReponse> allLignes();
	
	public Lignes findBy(Long id);
	
	void addLigne(Lignes ligne,Long id_dep);
	
	void updateLigne(Lignes ligne,Long id_dep);
	
	void deleteLigne(Long id);
	 
	public List<Lignes> getAllLignes();
	
}
