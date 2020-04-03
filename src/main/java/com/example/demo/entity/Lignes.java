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

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@Table(name = "lignes")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Lignes implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLigne;
	
	private String nomLigne;
	
	@ManyToOne //plusieurs lignes pour un département
	@JoinColumn(name = "idDepartement")
	//@JsonIgnore
	public Departement departement;
	
	@OneToMany(mappedBy = "lignes")
	private List<Machines> machines;

	public Lignes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Lignes( String nomLigne) {
		super();
		this.nomLigne = nomLigne;		
	}

	public Lignes(Long idLigne, String nomLigne, Departement departement, List<Machines> machines) {
		super();
		this.idLigne = idLigne;
		this.nomLigne = nomLigne;
		this.departement = departement;
		this.machines = machines;
	}

	public Long getIdLigne() {
		return idLigne;
	}

	public void setIdLigne(Long idLigne) {
		this.idLigne = idLigne;
	}

	public String getNomLigne() {
		return nomLigne;
	}

	public void setNomLigne(String nomLigne) {
		this.nomLigne = nomLigne;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	public List<Machines> getMachines() {
		return machines;
	}

	public void setMachines(List<Machines> machines) {
		this.machines = machines;
	}
	
	
}
