package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "maintenance")
public class Maintenance implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMaintenance;
	
	private Date date;
	
	@ManyToOne //plusieurs interventions pour une machine
	@JoinColumn(name = "idMachine")
	private Machines machines;
	
	private String Details;
	
	private boolean etat;

	public Maintenance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Maintenance(Long idMaintenance, Date date, Machines machine, String details, boolean etat) {
		super();
		this.idMaintenance = idMaintenance;
		this.date = date;
		this.machines = machine;
		Details = details;
		this.etat = etat;
	}

	public Long getIdMaintenance() {
		return idMaintenance;
	}

	public void setIdMaintenance(Long idMaintenance) {
		this.idMaintenance = idMaintenance;
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

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

}
