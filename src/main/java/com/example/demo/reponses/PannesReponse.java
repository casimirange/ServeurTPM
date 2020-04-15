package com.example.demo.reponses;

import java.util.Date;
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
        private String détails;
        private Date heureArret;
        private Date debutInter;
        private Date finInter;
        private boolean etat;
        
        private String nomOP;
        private String prenomOP;
        private Long matOp;
        
        private String nomTec;
        private String preTec;
        private Long matricule;
        private String fonction;
        
        
}
