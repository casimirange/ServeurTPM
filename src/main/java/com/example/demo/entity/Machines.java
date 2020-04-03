package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "machines")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Machines implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMachine;
	
	private String nom;

	private String code;
	
	private String centre_cout;
	
	private String label;
	
	/*@Lob
	private byte pic;*/
	
	@ManyToOne //plusieurs machines pour une ligne
	@JoinColumn(name = "idLigne")
	//@JsonIgnore
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

	public Machines(String nom, String code, String centre_cout, String label) {
		super();
		this.nom = nom;
		this.code = code;
		this.centre_cout = centre_cout;
		this.label = label;
	}

	

	/*public Machines(String nom, String code, String centre_cout, String label, byte pic) {
		super();
		this.nom = nom;
		this.code = code;
		this.centre_cout = centre_cout;
		this.label = label;
		this.pic = pic;
	}*/

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCentre_cout() {
		return centre_cout;
	}

	public void setCentre_cout(String centre_cout) {
		this.centre_cout = centre_cout;
	}	

	public List<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(List<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}

	public List<Audit> getAudit() {
		return audit;
	}

	public void setAudit(List<Audit> audit) {
		this.audit = audit;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		label = label;
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
	

	/*public byte getPic() {
		return pic;
	}

	public void setPic(byte pic) {
		this.pic = pic;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMachine == null) ? 0 : idMachine.hashCode());
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
		Machines other = (Machines) obj;
		if (idMachine == null) {
			if (other.idMachine != null)
				return false;
		} else if (!idMachine.equals(other.idMachine))
			return false;
		return true;
	}
	
	
	
}
