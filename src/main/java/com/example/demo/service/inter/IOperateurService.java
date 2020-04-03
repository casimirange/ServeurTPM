package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.Operateurs;

public interface IOperateurService {

	public List<Operateurs> allOperateurs();
	
	public Operateurs findOne(Long matricule);
	
	void addOperateur(Operateurs technicien);
	
	void updateOperateur(Operateurs technicien);
	
	void deleteOperateur(Long matricule);
	
}
