package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "arrets")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Arrets implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArret;

    @ManyToOne //plusieurs arrêts pour une machine
    @JoinColumn(name = "idMachine")
    @JsonIgnore
    private Machines machines;

    private LocalDate date;

    /*@OneToMany(mappedBy = "arrets", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operateurs> operateurs;*/

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    private LocalDateTime debut_arret; 

    private LocalDateTime fin_arret;

    private String cause;

    @Column(unique = true)
    private String numero;

    public Arrets() {
            super();
            // TODO Auto-generated constructor stub
    }

    public Arrets(LocalDate date, LocalDateTime debut_arret, LocalDateTime fin_arret, String cause, String numero) {
        this.date = date;
        this.debut_arret = debut_arret;
        this.fin_arret = fin_arret;
        this.cause = cause;
        this.numero = numero;
    }

    public Long getIdArret() {
        return idArret;
    }

    public void setIdArret(Long idArret) {
            this.idArret = idArret;
    }

    public Machines getMachine() {
            return machines;
    }

    public void setMachine(Machines machine) {
            this.machines = machine;
    }

    public LocalDate getDate() {
            return date;
    }

    public void setDate(LocalDate date) {
            this.date = date;
    }

    public LocalDateTime getDebut_arret() {
            return debut_arret;
    }

    public void setDebut_arret(LocalDateTime debut_arret) {
            this.debut_arret = debut_arret;
    }

    public LocalDateTime getFin_arret() {
            return fin_arret;
    }

    public void setFin_arret(LocalDateTime fin_arret) {
            this.fin_arret = fin_arret;
    }

    public String getCause() {
            return cause;
    }

    public void setCause(String cause) {
            this.cause = cause;
    }

    public Machines getMachines() {
        return machines;
    }

    public void setMachines(Machines machines) {
        this.machines = machines;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
	
	
}
