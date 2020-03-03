package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "departement")
public class Departement implements Serializable{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long idDepartement;
	
	private String nom;
	
	private String centre_cout;
	
	private String responsable;
	
	@OneToMany(mappedBy = "departement")
	private List<Lignes> lignes;

	public Departement(String nom, String centre_cout, String responsable) {
		super();
		this.nom = nom;
		this.centre_cout = centre_cout;
		this.responsable = responsable;
	}

	public Departement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return idDepartement;
	}

	public void setId(Long id) {
		this.idDepartement = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCentre_cout() {
		return centre_cout;
	}

	public void setCentre_cout(String centre_cout) {
		this.centre_cout = centre_cout;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDepartement == null) ? 0 : idDepartement.hashCode());
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
		Departement other = (Departement) obj;
		if (idDepartement == null) {
			if (other.idDepartement != null)
				return false;
		} else if (!idDepartement.equals(other.idDepartement))
			return false;
		return true;
	}

}
