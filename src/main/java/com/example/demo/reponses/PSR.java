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
public class PSR {
    private String machine;	
	private String code;  
        private Long idMachine;
        
        private LocalDate date;
        private String numero;
        private String cause;
        private String description;
        private String details;
        private LocalDateTime heureArret;
        private LocalDateTime debutInter;
        private LocalDateTime finInter;
        private boolean etat;
        @Column(nullable = true)
        private String outil;
        @Column(nullable = true)
        private String ref;
        @Column(nullable = true)
        private int qte;
        
        private Long wt;
        private Long ttr;
        private Long dt;
        
        private String nomOP;
        private String prenomOP;
        private Long matOp;
        
        private String nomTec;
        private String preTec;
        private Long matricule;
        private String fonction;
}
