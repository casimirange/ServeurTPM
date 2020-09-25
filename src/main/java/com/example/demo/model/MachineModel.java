package com.example.demo.model;

import javax.persistence.Column;

public class MachineModel {

	 Long idLigne;
	 String nomMachine;
	 String label;
	 String centreCout;
	 String code;
	 Long idMachine;
         @Column(columnDefinition="varchar(20) default 'bonaberi'")
	private String localisation;    
        
        @Column(columnDefinition = "BIT default true", length = 1)
	private boolean etat;
	 //byte[] photo;
	 
	public Long getIdLigne() {
		return idLigne;
	}
	public void setIdLigne(Long idLigne) {
		this.idLigne = idLigne;
	}
	public String getNom() {
		return nomMachine;
	}
	public void setNom(String nom) {
		this.nomMachine = nom;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCentreCout() {
		return centreCout;
	}
	public void setCentreCout(String centreCout) {
		this.centreCout = centreCout;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String Localisation) {
        this.localisation = Localisation;
    }
	
	
	
	
	 
	 
}
