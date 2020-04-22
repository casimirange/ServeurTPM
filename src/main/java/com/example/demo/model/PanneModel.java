package com.example.demo.model;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
import com.example.demo.entity.Outils;
import com.example.demo.entity.Techniciens;

public class PanneModel {

	Long idPanne;
	
	String cause;
	
	String details;
	
	String description;
        
        String outil;
        
        String ref;
        
        int qte;
	
	java.sql.Date date;
	  
	Date heureArret;
	
	Date debutInter; 
	
	Date finInter;
	
	Long idTechnicien;
	
	Long idOperateur;
	
	Long idMachine;
	
	boolean etat;
	
	int numero;
	
	Long idOutil;

	public Long getIdPanne() {
		return idPanne;
	}

	public void setIdPanne(Long idPanne) {
		this.idPanne = idPanne;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public Date getHeureArret() {
		return heureArret;
	}

	public void setHeureArret(Date heure_arret) {
		this.heureArret = heure_arret;
	}

	public Date getDebutInter() {
		return debutInter;
	}

	public void setDebutInter(Date debut_inter) {
		this.debutInter = debut_inter;
	}

	public Date getFinInter() {
		return finInter;
	}

	public void setFinInter(Date fin_inter) {
		this.finInter = fin_inter;
	}

	public Long getIdTechnicien() {
		return idTechnicien;
	}

	public void setIdTechnicien(Long idTechnicien) {
		this.idTechnicien = idTechnicien;
	}

	public Long getIdOperateur() {
		return idOperateur;
	}

	public void setIdOperateur(Long idOperateur) {
		this.idOperateur = idOperateur;
	}

	public Long getIdMachine() {
		return idMachine;
	}

	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Long getIdOutil() {
		return idOutil;
	}

	public void setIdOutil(Long idOutil) {
		this.idOutil = idOutil;
	}

    public String getOutil() {
        return outil;
    }

    public void setOutil(String outil) {
        this.outil = outil;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
	
	
	
}
