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
public class CountPannesResponse {
    private String machine;	
	private String code;  
        private Long idM;
        private Long nbre;
        private LocalDate date;
        private String numero;
        private String cause;
        private String description;
        private String details;
        private LocalDateTime heureArret;
        private LocalDateTime debutInter;
        private LocalDateTime finInter;
        private boolean etat;
        private boolean cont;
        private int quart;
        @Column(nullable = true)
        private String outil;
        @Column(nullable = true)
        private String ref;
        @Column(nullable = true)
        private int qte;
        
        private String nomOP;
        private String prenomOP;
        private Long matOp;
        
        private String nomTec;
        private String preTec;
        private Long matricule;
        private String fonction;
        
        private String dep;
        private Long idDepartement;
        
}
