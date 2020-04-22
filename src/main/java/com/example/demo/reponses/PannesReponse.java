package com.example.demo.reponses;

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
public class PannesReponse {

	private String machine;	
	private String code;  
        
        private Date date;
        private int numero;
        private String cause;
        private String description;
        private String details;
        private Date heureArret;
        private Date debutInter;
        private Date finInter;
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
