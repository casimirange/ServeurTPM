package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
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
import javax.xml.soap.Text;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "pannes")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Pannes implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPanne;
	
	private String cause;
	
	private String details;
	
	private String description;
        
        @Column(nullable = true)
        private String outil;
        
        @Column(nullable = true)
        private String ref;
        
        @Value("${some.key:0}")
        private int qte;
	
	private LocalDate date;
	  
	private LocalDateTime heure_arret;
	
	private LocalDateTime debut_inter; 
	
	private LocalDateTime fin_inter;
	
	@ManyToOne
	@JoinColumn(name = "idTechnicien")
	@JsonIgnore
	private Techniciens techniciens;
	
	@ManyToOne
	@JoinColumn(name = "idOperateur")
	@JsonIgnore
	private Operateurs operateurs;
	
	@ManyToOne //plusieurs pannes pour une machine
	@JoinColumn(name = "idMachine")
	@JsonIgnore
	private Machines machines;
	
	private boolean etat;
	
	private String numero;
        private int DT;
        private int WT;
        private int TTR;
	
//	@ManyToOne //plusieurs pannes pour une machine
//	@JoinColumn(name = "idOutil", nullable = true)
//        @JsonIgnore
//	private Outils outils;

	public Pannes() {
		super();
		// TODO Auto-generated constructor stub
	}


//	public Pannes(String cause, String détails, String description, java.sql.Date date, Date heure_arret,
//			Date debut_inter, Date fin_inter, boolean etat, int numero) {
//		super();
//		this.cause = cause;
//		this.details = détails;
//		this.description = description;
//		this.date = date;
//		this.heure_arret = heure_arret;
//		this.debut_inter = debut_inter;
//		this.fin_inter = fin_inter;
//		this.etat = etat;
//		this.numero = numero;
//	}

//	public Pannes(Long idPanne, String cause, String détails, String description, java.sql.Date date, Date heure_arret,
//			Date debut_inter, Date fin_inter, Techniciens techniciens, Operateurs operateurs,
//			Machines machines, boolean etat, int numero, Outils outils) {
//		super();
//		this.idPanne = idPanne;
//		this.cause = cause;
//		this.details = détails;
//		this.description = description;
//		this.date = date;
//		this.heure_arret = heure_arret;
//		this.debut_inter = debut_inter;
//		this.fin_inter = fin_inter;
//		this.techniciens = techniciens;
//		this.operateurs = operateurs;
//		this.machines = machines;
//		this.etat = etat;
//		this.numero = numero;
//		this.outils = outils;
//	}

//    public Pannes(String cause, String details, String description, String outil, String ref, int qte, LocalDate date, LocalDateTime heure_arret, LocalDateTime debut_inter, LocalDateTime fin_inter, boolean etat, int numero) {
//        this.cause = cause;
//        this.details = details;
//        this.description = description;
//        this.outil = outil;
//        this.ref = ref;
//        this.qte = qte;
//        this.date = date;
//        this.heure_arret = heure_arret;
//        this.debut_inter = debut_inter;
//        this.fin_inter = fin_inter;
//        this.etat = etat;
//        this.numero = numero;
//    }

    public Pannes(String cause, String details, String description, String outil, String ref, int qte, LocalDate date, 
            LocalDateTime heure_arret, LocalDateTime debut_inter, LocalDateTime fin_inter, boolean etat, String numero, int DT, int WT, int TTR) {
        this.cause = cause;
        this.details = details;
        this.description = description;
        this.outil = outil;
        this.ref = ref;
        this.qte = qte;
        this.date = date;
        this.heure_arret = heure_arret;
        this.debut_inter = debut_inter;
        this.fin_inter = fin_inter;
        this.etat = etat;
        this.numero = numero;
        this.DT = DT;
        this.WT = WT;
        this.TTR = TTR;
    }
     
      


	public Long getIdPanne() {
		return idPanne;
	}

	public void setIdPanne(Long idPanne) {
		this.idPanne = idPanne;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalDateTime getHeure_arret() {
		return heure_arret;
	}

	public void setHeure_arret(LocalDateTime heure_arret) {
		this.heure_arret = heure_arret;
	}

	public LocalDateTime getDebut_inter() {
		return debut_inter;
	}

	public void setDebut_inter(LocalDateTime debut_inter) {
		this.debut_inter = debut_inter;
	}

	public LocalDateTime getFin_inter() {
		return fin_inter;
	}

	public void setFin_inter(LocalDateTime fin_inter) {
		this.fin_inter = fin_inter;
	}


	public Techniciens getTechniciens() {
		return techniciens;
	}

	public void setTechniciens(Techniciens techniciens) {
		this.techniciens = techniciens;
	}



	public Operateurs getOperateurs() {
		return operateurs;
	}



	public void setOperateurs(Operateurs operateurs) {
		this.operateurs = operateurs;
	}



	public Machines getMachines() {
		return machines;
	}



	public void setMachines(Machines machines) {
		this.machines = machines;
	}



//	public Outils getOutils() {
//		return outils;
//	}



//	public void setOutils(Outils outils) {
//		this.outils = outils;
//	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}


    public String getOutil() {
        return outil;
    }

    public void setOutil(String outil) {
        this.outil = outil;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getDT() {
        return DT;
    }

    public void setDT(int DT) {
        this.DT = DT;
    }

    public int getWT() {
        return WT;
    }

    public void setWT(int WT) {
        this.WT = WT;
    }

    public int getTTR() {
        return TTR;
    }

    public void setTTR(int TTR) {
        this.TTR = TTR;
    }
    
}
