package com.example.demo.entity;

import java.io.Serializable;
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

@Entity
@Table(name = "machines")
public class Machines implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMachine;
	
	private String nom;

	private String matricule;
	
	private String centre_cout;
	
	private String Label;
	
	@ManyToOne //plusieurs machines pour une ligne
	@JoinColumn(name = "idLigne")
	private Lignes lignes;
	
	@OneToMany(mappedBy = "machines")
	private List<Pannes> pannes;
	
	@OneToMany(mappedBy = "machines")
	private List<Heures> heures;
	
	@OneToMany(mappedBy = "machines")
	private List<Maintenance> maintenance;
	
	@OneToMany(mappedBy = "machines")
	private List<Arrets> arrets;
	
	@OneToMany(mappedBy = "machines")
	private List<Audit> audit;

	public Machines() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Machines(Long idMachine, String nom, String matricule, String centre_cout, Lignes ligne, List<Pannes> panne,
			List<Heures> heure, List<Maintenance> maintenance, List<Arrets> arret, List<Audit> audit) {
		super();
		this.idMachine = idMachine;
		this.nom = nom;
		this.matricule = matricule;
		this.centre_cout = centre_cout;
		this.lignes = ligne;
		this.pannes = panne;
		this.heures = heure;
		this.maintenance = maintenance;
		this.arrets = arret;
		this.audit = audit;
	}

	public Long getIdMachine() {
		return idMachine;
	}

	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getCentre_cout() {
		return centre_cout;
	}

	public void setCentre_cout(String centre_cout) {
		this.centre_cout = centre_cout;
	}

	public Lignes getLigne() {
		return lignes;
	}

	public void setLigne(Lignes ligne) {
		this.lignes = ligne;
	}

	public List<Pannes> getPanne() {
		return pannes;
	}

	public void setPanne(List<Pannes> panne) {
		this.pannes = panne;
	}

	public List<Heures> getHeure() {
		return heures;
	}

	public void setHeure(List<Heures> heure) {
		this.heures = heure;
	}

	public List<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(List<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}

	public List<Arrets> getArret() {
		return arrets;
	}

	public void setArret(List<Arrets> arret) {
		this.arrets = arret;
	}

	public List<Audit> getAudit() {
		return audit;
	}

	public void setAudit(List<Audit> audit) {
		this.audit = audit;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public Lignes getLignes() {
		return lignes;
	}

	public void setLignes(Lignes lignes) {
		this.lignes = lignes;
	}

	public List<Pannes> getPannes() {
		return pannes;
	}

	public void setPannes(List<Pannes> pannes) {
		this.pannes = pannes;
	}

	public List<Heures> getHeures() {
		return heures;
	}

	public void setHeures(List<Heures> heures) {
		this.heures = heures;
	}

	public List<Arrets> getArrets() {
		return arrets;
	}

	public void setArrets(List<Arrets> arrets) {
		this.arrets = arrets;
	}
	
	
	
}
