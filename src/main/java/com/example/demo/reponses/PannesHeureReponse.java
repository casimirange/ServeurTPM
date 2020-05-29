package com.example.demo.reponses;

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
public class PannesHeureReponse {
	
        private LocalDateTime heureArret;
        private LocalDateTime debutInter;
        private LocalDateTime finInter;
        private String numero;
        
        
}
