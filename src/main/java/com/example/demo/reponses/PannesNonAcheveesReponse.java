package com.example.demo.reponses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PannesNonAcheveesReponse {

	private String machine;	
	private String code;  
	private Long idM;  
        
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
        
        private String nomOP;
        private String prenomOP;
        private Long matOp;
        
        private String nomTec;
        private String preTec;
        private Long matricule;
        private String fonction;
        
        
}
