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
	
	String détails;
	
	String description;
	
	java.sql.Date date;
	  
	Date heure_arret;
	
	Date debut_inter; 
	
	Date fin_inter;
	
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

	public String getDétails() {
		return détails;
	}

	public void setDétails(String détails) {
		this.détails = détails;
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

	public Date getHeure_arret() {
		return heure_arret;
	}

	public void setHeure_arret(Date heure_arret) {
		this.heure_arret = heure_arret;
	}

	public Date getDebut_inter() {
		return debut_inter;
	}

	public void setDebut_inter(Date debut_inter) {
		this.debut_inter = debut_inter;
	}

	public Date getFin_inter() {
		return fin_inter;
	}

	public void setFin_inter(Date fin_inter) {
		this.fin_inter = fin_inter;
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
	
	
	
}
