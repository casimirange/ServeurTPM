/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.reponses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
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
public class DashboardResponse {
    private String machine;	
    private String code;  
    private Long idMachine;
    private Long nbre;
    private Long DT;
//        private Long WT;
//        private Long TTR;
    private LocalDate date;
    private String numero;
    private String cause;
    private String description;
    private String details;
    private LocalDateTime heureArret;
    private LocalDateTime debutInter;
    private LocalDateTime finInter;
    private boolean etat;        
    private String dep;
    private Long idDepartement;
        
}
