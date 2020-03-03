package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "heures")
public class Heures implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idHeure;
	
	private Time heure_debut;
	
	private Time heure_fin;
	
	private Date date;
	
	@ManyToOne //plusieurs lignes pour un département
	@JoinColumn(name = "idMachine")
	private Machines machines;

	public Heures() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Heures(Long idHeure, Time heure_debut, Time heure_fin, Date date, Machines machine) {
		super();
		this.idHeure = idHeure;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.date = date;
		this.machines = machine;
	}

	public Long getIdHeure() {
		return idHeure;
	}

	public void setIdHeure(Long idHeure) {
		this.idHeure = idHeure;
	}

	public Time getHeure_debut() {
		return heure_debut;
	}

	public void setHeure_debut(Time heure_debut) {
		this.heure_debut = heure_debut;
	}

	public Time getHeure_fin() {
		return heure_fin;
	}

	public void setHeure_fin(Time heure_fin) {
		this.heure_fin = heure_fin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Machines getMachine() {
		return machines;
	}

	public void setMachine(Machines machine) {
		this.machines = machine;
	}
	
	
	
}
