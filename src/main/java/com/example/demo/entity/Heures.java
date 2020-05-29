package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "heures")
public class Heures implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idHeure;
	
	private double heure;
	
	private LocalDate date;
	
	@ManyToOne //plusieurs lignes pour un département
	@JoinColumn(name = "idMachine")
	private Machines machines;

	public Heures() {
		super();
		// TODO Auto-generated constructor stub
	}

    public Heures(double heure, LocalDate date) {
        this.heure = heure;
        this.date = date;
    }

    public Long getIdHeure() {
        return idHeure;
    }

    public void setIdHeure(Long idHeure) {
        this.idHeure = idHeure;
    }

    public double getHeure() {
        return heure;
    }

    public void setHeure(double heure) {
        this.heure = heure;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Machines getMachines() {
        return machines;
    }

    public void setMachines(Machines machines) {
        this.machines = machines;
    }
	
	
}
