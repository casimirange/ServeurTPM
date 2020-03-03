package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "arrets")
public class Arrets implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idArret;
	
	@ManyToOne //plusieurs arrêts pour une machine
	@JoinColumn(name = "idMachine")
	private Machines machines;
	
	private java.sql.Date date;
	
	@OneToMany(mappedBy = "arrets", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Operateurs> operateurs;
	
	private Date debut_arret; 
	
	private Date fin_arret;
	
	private String cause;

	public Arrets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Arrets(Long idArret, Machines machine, java.sql.Date date, List<Operateurs> operateur, Date debut_arret,
			Date fin_arret, String cause) {
		super();
		this.idArret = idArret;
		this.machines = machine;
		this.date = date;
		this.operateurs = operateur;
		this.debut_arret = debut_arret;
		this.fin_arret = fin_arret;
		this.cause = cause;
	}

	public Long getIdArret() {
		return idArret;
	}

	public void setIdArret(Long idArret) {
		this.idArret = idArret;
	}

	public Machines getMachine() {
		return machines;
	}

	public void setMachine(Machines machine) {
		this.machines = machine;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public List<Operateurs> getOperateur() {
		return operateurs;
	}

	public void setOperateur(List<Operateurs> operateur) {
		this.operateurs = operateur;
	}

	public Date getDebut_arret() {
		return debut_arret;
	}

	public void setDebut_arret(Date debut_arret) {
		this.debut_arret = debut_arret;
	}

	public Date getFin_arret() {
		return fin_arret;
	}

	public void setFin_arret(Date fin_arret) {
		this.fin_arret = fin_arret;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
	
}
