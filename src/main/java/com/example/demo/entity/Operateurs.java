package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "operateurs")
public class Operateurs implements Serializable{
	
	@Id
	@GeneratedValue
	private Long idOperateur;
	
	private String nom;
	 
	private String prenom;
	
	private int matricule;
	
	@OneToMany(mappedBy = "operateurs")
	private List<Pannes> pannes;
	
	@ManyToOne(fetch = FetchType.LAZY) //plusieurs operateurs pour une panne
	@JoinColumn(name = "idArret")
	private Arrets arrets;

	public Operateurs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Operateurs(Long idOperateur, String nom, String prenom, int matricule, List<Pannes> panne) {
		super();
		this.idOperateur = idOperateur;
		this.nom = nom;
		this.prenom = prenom;
		this.matricule = matricule;
		this.pannes = panne;
	}

	public Long getIdOperateur() {
		return idOperateur;
	}

	public void setIdOperateur(Long idOperateur) {
		this.idOperateur = idOperateur;
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
	
	

}
