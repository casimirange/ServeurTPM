package com.example.demo.entity;

import java.io.Serializable;
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
@Table(name = "employe")
public class Employé implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmploye;
	
	private String nom;
	
	private String prenom;
	
	private int matricule;
	
		
	@OneToMany(mappedBy = "employe")
	private List<Audit> audit;

	public Employé() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employé(Long idEmploye, String nom, String prenom, int matricule) {
		super();
		this.idEmploye = idEmploye;
		this.nom = nom;
		this.prenom = prenom;
		this.matricule = matricule;
	}

	public Long getIdEmploye() {
		return idEmploye;
	}

	public void setIdEmploye(Long idEmploye) {
		this.idEmploye = idEmploye;
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

	public List<Audit> getAudit() {
		return audit;
	}

	public void setAudit(List<Audit> audit) {
		this.audit = audit;
	}
	
	

}
