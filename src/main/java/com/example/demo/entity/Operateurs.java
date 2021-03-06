package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Value;

//import org.hibernate.validator.constraints.UniqueElements;


@Entity
@Table(name = "operateurs")
public class Operateurs implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOperateur;
	
	private String nom;
	 
	private String prenom;

        @Column(columnDefinition="varchar(20) default 'bonaberi'")
	private String localisation;    
        
        @Column(columnDefinition = "BIT default true", length = 1)
	private boolean etat;
        
	@Column(unique = true)
	private Long matricule;
	
	@OneToMany(mappedBy = "operateurs")
	private List<Pannes> pannes;
	

	public Operateurs() {
		super();
		// TODO Auto-generated constructor stub
	}

        public Operateurs(String nom, String prenom, String localisation, boolean etat, Long matricule) {
            this.nom = nom;
            this.prenom = prenom;
            this.localisation = localisation;
            this.etat = etat;
            this.matricule = matricule;
        }	

	public Operateurs(Long idOperateur, String nom, String prenom, Long matricule, List<Pannes> pannes) {
		super();
		this.idOperateur = idOperateur;
		this.nom = nom;
		this.prenom = prenom;
		this.matricule = matricule;
		this.pannes = pannes;
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


	public Long getIdOperateur() {
		return idOperateur;
	}



	public void setIdOperateur(Long idOperateur) {
		this.idOperateur = idOperateur;
	}



	public List<Pannes> getPannes() {
		return pannes;
	}



	public void setPannes(List<Pannes> pannes) {
		this.pannes = pannes;
	}

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
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
		Operateurs other = (Operateurs) obj;
		if (matricule == null) {
			if (other.matricule != null)
				return false;
		} else if (!matricule.equals(other.matricule))
			return false;
		return true;
	}
	
	

}
