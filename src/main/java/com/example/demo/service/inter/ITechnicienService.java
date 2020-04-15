package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Techniciens;

public interface ITechnicienService {

	public List<Techniciens> allTechniciens();
        
        public List<Techniciens> allActiveTech();
        
        public List<Techniciens> allDesactiveTech();
	
	public Techniciens findOne(Long id);
        
        public Techniciens findByMat(Long matricule);
	
	void addTechnicien(Techniciens technicien);
	
	void updateTech(Techniciens technicien);
	
	void deleteTech(Long id);
        
        void activerTech(Long matricule);
        
	
}
