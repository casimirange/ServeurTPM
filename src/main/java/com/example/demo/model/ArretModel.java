/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Casimir
 */
public class ArretModel {
    Long idArret;
	
    Long idMachine;

    private LocalDate date;
    
    private Date created_at;

    private LocalDateTime debutArret; 
    
    private LocalDateTime finArret;

    private String cause;

    private boolean etat;

    private String numero;

    public Long getIdArret() {
        return idArret;
    }

    public void setIdArret(Long idArret) {
        this.idArret = idArret;
    }

    public Long getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(Long idMachine) {
        this.idMachine = idMachine;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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

    public LocalDateTime getDebutArret() {
        return debutArret;
    }

    public void setDebutArret(LocalDateTime debutArret) {
        this.debutArret = debutArret;
    }

    public LocalDateTime getFinArret() {
        return finArret;
    }

    public void setFinArret(LocalDateTime finArret) {
        this.finArret = finArret;
    }
    
    
}
