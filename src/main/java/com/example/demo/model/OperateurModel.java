/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import javax.persistence.Column;

/**
 *
 * @author Casimir
 */
public class OperateurModel {
    Long idOP;
    String nomOP;
    String prenomOP;
    Long matOP;
    @Column(columnDefinition="varchar(20) default 'bonaberi'")
    private String localisation;  
    @Column(columnDefinition = "BIT default true", length = 1)
    private boolean etat;

    public Long getIdOP() {
        return idOP;
    }

    public void setIdOP(Long idOP) {
        this.idOP = idOP;
    }

    public String getNomOP() {
        return nomOP;
    }

    public void setNomOP(String nomOP) {
        this.nomOP = nomOP;
    }

    public String getPrenomOP() {
        return prenomOP;
    }

    public void setPrenomOP(String prenomOP) {
        this.prenomOP = prenomOP;
    }

    public Long getMatOP() {
        return matOP;
    }

    public void setMatOP(Long matOP) {
        this.matOP = matOP;
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

    
    
}
