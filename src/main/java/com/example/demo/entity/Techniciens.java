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
@Table(name = "techniciens")
public class Techniciens implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTechnicien;
	
	private String nom;
	 
	private String prenom;
	
	@Column(unique = true)
	private int matricule;
	
	@OneToMany(mappedBy = "techniciens")
	private List<Pannes> pannes;

	public Techniciens() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Techniciens(Long idTechnicien, String nom, String prenom, int matricule, List<Pannes> panne) {
		super();
		this.idTechnicien = idTechnicien;
		this.nom = nom;
		this.prenom = prenom;
		this.matricule = matricule;
		this.pannes = panne;
	}

	public Long getIdTechnicien() {
		return idTechnicien;
	}

	public void setIdTechnicien(Long idTechnicien) {
		this.idTechnicien = idTechnicien;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getMatricule() {
		return matricule;
	}

	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}

	public List<Pannes> getPanne() {
		return pannes;
	}

	public void setPanne(List<Pannes> panne) {
		this.pannes = panne;
	}

	public List<Pannes> getPannes() {
		return pannes;
	}

	public void setPannes(List<Pannes> pannes) {
		this.pannes = pannes;
	}
	
	
	
}
