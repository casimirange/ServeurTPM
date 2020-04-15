package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "lignes")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Lignes implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLigne;
	
	private String nomLigne;
	
	@ManyToOne(fetch = FetchType.EAGER) //plusieurs lignes pour un département
	@JoinColumn(name = "idDepartement")
	//@JsonIgnore
	public Departement departement;
	
	@OneToMany(mappedBy = "lignes")
	private List<Machines> machines;

	/*public Lignes() {
		super();
		// TODO Auto-generated constructor stub
	}*/

	public Lignes( String nomLigne) {
		super();
		this.nomLigne = nomLigne;		
	}

        /*
	public Lignes(Long idLigne, String nomLigne, Departement departement, List<Machines> machines) {
		super();
		this.idLigne = idLigne;
		this.nomLigne = nomLigne;
		this.departement = departement;
		this.machines = machines;
	}*/

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

	@JsonBackReference
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
