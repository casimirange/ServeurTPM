/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Casimir
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MTBFModel {
     String date;
     String MTTR;
     String   AT;
     String  nbre;
     String   TDT;
     String   MDT;
     String   HT;
     String   WT;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMTTR() {
        return MTTR;
    }

    public void setMTTR(String MTTR) {
        this.MTTR = MTTR;
    }

    public String getAT() {
        return AT;
    }

    public void setAT(String AT) {
        this.AT = AT;
    }

    public String getNbre() {
        return nbre;
    }

    public void setNbre(String nbre) {
        this.nbre = nbre;
    }

    public String getTDT() {
        return TDT;
    }

    public void setTDT(String TDT) {
        this.TDT = TDT;
    }

    public String getMDT() {
        return MDT;
    }

    public void setMDT(String MDT) {
        this.MDT = MDT;
    }

    public String getHT() {
        return HT;
    }

    public void setHT(String HT) {
        this.HT = HT;
    }

    public String getWT() {
        return WT;
    }

    public void setWT(String WT) {
        this.WT = WT;
    }
     
     
}
