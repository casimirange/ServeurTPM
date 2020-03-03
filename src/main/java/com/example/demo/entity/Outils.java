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
@Table(name = "outils")
public class Outils implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOutil;
	
	private int qte;
	
	private String nom;
	
	private String ref;
	
	@OneToMany(mappedBy = "outils")
	private List<Pannes> pannes;

	public Outils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Outils(Long idOutil, int qte, String nom, String ref, List<Pannes> panne) {
		super();
		this.idOutil = idOutil;
		this.qte = qte;
		this.nom = nom;
		this.ref = ref;
		this.pannes = panne;
	}

	public Long getIdOutil() {
		return idOutil;
	}

	public void setIdOutil(Long idOutil) {
		this.idOutil = idOutil;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public List<Pannes> getPanne() {
		return pannes;
	}

	public void setPanne(List<Pannes> panne) {
		this.pannes = panne;
	}

}
