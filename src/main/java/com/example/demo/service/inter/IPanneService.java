package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Pannes;
import com.example.demo.reponses.PSR;
import com.example.demo.reponses.PannesReponse;
import net.minidev.json.JSONObject;

public interface IPanneService {

	public List<Pannes> allPannes();
        
        public List<JSONObject> toutesPannes();
	
	public Pannes findOne(Long numero);
	
	void addPanne(Pannes panne, Long code_machine, Long id_oper, Long id_tech);
	
	void updatePanne(Pannes panne, Long code_machine, Long id_oper, Long id_tech);
	
	void deletePanne(Long numero);

        JSONObject showPanne(String numero);
        
        
}
