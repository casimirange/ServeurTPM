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
public class PannesTechReponse {
	
        
        private String nomTec;
        private String preTec;
        private Long matricule;
        private String fonction;
        private int quart;
        
        
}
