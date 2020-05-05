package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "techniciens")
public class Techniciens implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long idTechnicien;
	
	private String nom;
	 
	private String prenom;
	
	private String fonction;    
        	
	private boolean etat;
	
	@Column(unique = true)
	private Long matricule;
	
	@OneToMany(mappedBy = "techniciens")
	private List<Pannes> pannes;

	public Techniciens() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Techniciens(String nom, String prenom, String fonction, Long matricule, boolean etat) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
		this.matricule = matricule;
                this.etat = etat;
	}

	public Techniciens(String nom, String prenom, String fonction, Long matricule, List<Pannes> pannes) {
		super();
		//this.idTechnicien = idTechnicien;
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
		this.matricule = matricule;
		this.pannes = pannes;
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

	public Long getMatricule() {
		return matricule;
	}

	public void setMatricule(Long matricule) {
		this.matricule = matricule;
	}

	public List<Pannes> getPannes() {
		return pannes;
	}

	public void setPannes(List<Pannes> pannes) {
		this.pannes = pannes;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

        public boolean isEtat() {
            return etat;
        }

        public void setEtat(boolean etat) {
            this.etat = etat;
        }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricule == null) ? 0 : matricule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Techniciens other = (Techniciens) obj;
		if (matricule == null) {
			if (other.matricule != null)
				return false;
		} else if (!matricule.equals(other.matricule))
			return false;
		return true;
	}
	
	
	
}
