package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "audit")
public class Audit implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAudit;
	
	private Date date;
	
	private int note;
	
	private String motif;
	
	private String Observation;
	
	@ManyToOne //plusieurs lignes pour un département
	@JoinColumn(name = "idMachine")
	private Machines machines;
	
	@ManyToOne //plusieurs lignes pour un département
	@JoinColumn(name = "idEmploye")
	private Employé employe;

	public Audit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Audit(Long idAudit, Date date, int note, String motif, String observation, Machines machine,
			Employé employe) {
		super();
		this.idAudit = idAudit;
		this.date = date;
		this.note = note;
		this.motif = motif;
		Observation = observation;
		this.machines = machine;
		this.employe = employe;
	}

	public Long getIdAudit() {
		return idAudit;
	}

	public void setIdAudit(Long idAudit) {
		this.idAudit = idAudit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getObservation() {
		return Observation;
	}

	public void setObservation(String observation) {
		Observation = observation;
	}

	public Machines getMachine() {
		return machines;
	}

	public void setMachine(Machines machine) {
		this.machines = machine;
	}

	public Employé getEmploye() {
		return employe;
	}

	public void setEmploye(Employé employe) {
		this.employe = employe;
	}
	
}
