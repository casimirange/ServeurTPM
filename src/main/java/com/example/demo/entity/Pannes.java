package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.soap.Text;

@Entity
@Table(name = "pannes")
public class Pannes implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPanne;
	
	private String cause;
	
	private String détails;
	
	private String description;
	
	private java.sql.Date date;
	  
	private Date heure_arret;
	
	private Date debut_inter; 
	
	private Date fin_inter;
	
	@ManyToOne
	@JoinColumn(name = "idPannes")
	private List<Techniciens> techniciens;
	
	@ManyToOne
	@JoinColumn(name = "idOperateur")
	private List<Operateurs> operateurs;
	
	@ManyToOne //plusieurs pannes pour une machine
	@JoinColumn(name = "idMachine")
	private Machines machines;
	
	private boolean etat;
	
	private long numero;
	
	@ManyToOne //plusieurs pannes pour une machine
	@JoinColumn(name = "idOutil", nullable = true)
	private Outils outils;

	public Pannes() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Pannes(Long idPanne, String cause, String détails, String description, java.sql.Date date, Date heure_arret,
			Date debut_inter, Date fin_inter, List<Techniciens> techniciens, List<Operateurs> operateurs,
			Machines machines, boolean etat, long numero, Outils outils) {
		super();
		this.idPanne = idPanne;
		this.cause = cause;
		this.détails = détails;
		this.description = description;
		this.date = date;
		this.heure_arret = heure_arret;
		this.debut_inter = debut_inter;
		this.fin_inter = fin_inter;
		this.techniciens = techniciens;
		this.operateurs = operateurs;
		this.machines = machines;
		this.etat = etat;
		this.numero = numero;
		this.outils = outils;
	}



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


	public List<Techniciens> getTechniciens() {
		return techniciens;
	}

	public void setTechniciens(List<Techniciens> techniciens) {
		this.techniciens = techniciens;
	}



	public List<Operateurs> getOperateurs() {
		return operateurs;
	}



	public void setOperateurs(List<Operateurs> operateurs) {
		this.operateurs = operateurs;
	}



	public Machines getMachines() {
		return machines;
	}



	public void setMachines(Machines machines) {
		this.machines = machines;
	}



	public Outils getOutils() {
		return outils;
	}



	public void setOutils(Outils outils) {
		this.outils = outils;
	}



	public Machines getMachine() {
		return machines;
	}

	public void setMachine(Machines machine) {
		this.machines = machine;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public Outils getPiece() {
		return outils;
	}

	public void setPiece(Outils outil) {
		this.outils = outil;
	}
	
	
	

}
