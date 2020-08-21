package com.example.demo.model;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
import com.example.demo.entity.Outils;
import com.example.demo.entity.Techniciens;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PanneModel {

	Long idPanne;
	
	String cause;
	
	String details;
	
	String description;
        
        String outil;
        
        String ref;
        
        int qte;
	
	LocalDate date;
	  
	LocalDateTime heureArret;
	
	LocalDateTime debutInter; 
	
	LocalDateTime finInter;
	
	Long idTechnicien;
	
	Long idOperateur;
	
	Long idMachine;
	
	boolean etat;
	
	boolean cont;
        
        int quart;
	
	String numero;
        
        int DT;
        
        int WT;
        
        int TTR;
	
	Long idOutil;

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

	public LocalDateTime getHeureArret() {
		return heureArret;
	}

	public void setHeureArret(LocalDateTime heure_arret) {
		this.heureArret = heure_arret;
	}

	public LocalDateTime getDebutInter() {
		return debutInter;
	}

	public void setDebutInter(LocalDateTime debut_inter) {
		this.debutInter = debut_inter;
	}

	public LocalDateTime getFinInter() {
		return finInter;
	}

	public void setFinInter(LocalDateTime fin_inter) {
		this.finInter = fin_inter;
	}

	public Long getIdTechnicien() {
		return idTechnicien;
	}

	public void setIdTechnicien(Long idTechnicien) {
		this.idTechnicien = idTechnicien;
	}

	public Long getIdOperateur() {
		return idOperateur;
	}

	public void setIdOperateur(Long idOperateur) {
		this.idOperateur = idOperateur;
	}

	public Long getIdMachine() {
		return idMachine;
	}

	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}

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

	public Long getIdOutil() {
		return idOutil;
	}

	public void setIdOutil(Long idOutil) {
		this.idOutil = idOutil;
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

    public boolean isCont() {
        return cont;
    }

    public void setCont(boolean cont) {
        this.cont = cont;
    }

    public int getQuart() {
        return quart;
    }

    public void setQuart(int quart) {
        this.quart = quart;
    }
	
	
	
}
