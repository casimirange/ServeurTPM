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
public class TechnicienModel {
    Long idTechnicien;
    String nom;
    String prenom;
    String fonction;
    Long matricule;
    @Column(columnDefinition="varchar(20) default 'bonaberi'")
    private String localisation;  
    @Column(columnDefinition = "BIT default true", length = 1)
    private boolean etat;

    public Long getIdTechnicien() {
        return idTechnicien;
    }

    public void setIdTechnicien(Long idTechnicien) {
        this.idTechnicien = idTechnicien;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Long getMatricule() {
        return matricule;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
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

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    
    
}
