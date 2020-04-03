package com.example.demo.model;

import com.example.demo.entity.Departement;

public class LigneModel {
 Long idDepartement;
 String nomLigne;
 Long idLigne;
 Departement dep;

public Long getIdLigne() {
	return idLigne;
}
public void setIdLigne(Long idLigne) {
	this.idLigne = idLigne;
}
public Long getIdDepartement() {
	return idDepartement;
}
public void setIdDepartement(Long idDepartement) {
	this.idDepartement = idDepartement;
}
public String getNomLigne() {
	return nomLigne;
}
public void setNomLigne(String nomLigne) {
	this.nomLigne = nomLigne;
}
public Departement getDep() {
	return dep;
}
public void setDep(Departement dep) {
	this.dep = dep;
}

 

 
}
