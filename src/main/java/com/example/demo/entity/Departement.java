package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "departement")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Departement implements Serializable{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long idDepartement;
	
	private String nom;
	
//	private String centre_cout;
	
	private String responsable;
	
	@Column(columnDefinition="varchar(20) default 'bonaberi'")
	private String localisation;
	
	@OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
	public List<Lignes> lignes;

	public Departement(String nom, String responsable, String localisation) {
		super();
		this.nom = nom;
		this.responsable = responsable;
		this.localisation = localisation;
	}

        /*
	public Departement(Long idDepartement, String nom, String centre_cout, String responsable, List<Lignes> lignes) {
		super();
		this.idDepartement = idDepartement;
		this.nom = nom;
		this.centre_cout = centre_cout;
		this.responsable = responsable;
		this.lignes = lignes;
	}

	public Departement() {
		super();
	}*/

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

//	public String getCentre_cout() {
//		return centre_cout;
//	}
//
//	public void setCentre_cout(String centre_cout) {
//		this.centre_cout = centre_cout;
//	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Long getIdDepartement() {
		return idDepartement;
	}

	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}

        
        
	@JsonManagedReference
	public List<Lignes> getLignes() {
		return lignes;
	}

	public void setLignes(List<Lignes> lignes) {
		this.lignes = lignes;
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
