package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Pannes;
import com.example.demo.reponses.PannesReponse;

public interface IPanneService {

	public List<Pannes> allPannes();
        
        public List<PannesReponse> toutesPannes();
	
	public Pannes findOne(Long numero);
	
	void addPanne(Pannes panne, Long code_machine, Long id_oper, Long id_tech);
	
	//void continuePanne(Pannes panne, Long numero, Long code_machine, Long id_oper, Long id_tech);
	
	void updatePanne(Pannes panne, Long code_machine, Long id_oper, Long id_tech, Long id_outil);
	
	void deletePanne(Long numero);
}
