/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.time.LocalDate;

/**
 *
 * @author Casimir
 */
public class HeureModel {
        
    private Long idHeure;

    private double heure;

    private LocalDate date;

    Long idMachine;

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

    public Long getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(Long idMachine) {
        this.idMachine = idMachine;
    }
    
    
}
