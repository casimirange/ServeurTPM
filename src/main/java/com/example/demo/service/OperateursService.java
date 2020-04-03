package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Operateurs;
import com.example.demo.repository.OperateurRepository;
import com.example.demo.service.inter.IOperateurService;

@Service
public class OperateursService implements IOperateurService {

	@Autowired
	private OperateurRepository operateurRepository ;
	

	public OperateurRepository getOperateurRepository() {
		return operateurRepository;
	}

	public void setOperateurRepository(OperateurRepository operateurRepository) {
		this.operateurRepository = operateurRepository;
	}

	@Override
	public List<Operateurs> allOperateurs() {
		return operateurRepository.findAll();
	}

	@Override
	public Operateurs findOne(Long matricule) {
		return operateurRepository.findById(matricule).get();
	}

	@Override
	public void addOperateur(Operateurs operateur) {
		operateurRepository.save(operateur);
	}

	@Override
	public void updateOperateur(Operateurs operateur) {
		operateurRepository.save(operateur);
	}

	@Override
	public void deleteOperateur(Long matricule) {
		Operateurs operateur = new Operateurs();
		operateur.setMatricule(matricule);
		operateurRepository.delete(operateur);
	}

}
