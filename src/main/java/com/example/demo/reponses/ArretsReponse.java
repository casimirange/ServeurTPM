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
public class ArretsReponse {
    private String machine;	
    private String code;  
    private Long idMachine;
    private Long idArret;
    private java.util.Date createdAt;
    private LocalDate date;
    private String numero;
    private String cause;
    private LocalDateTime debutArret;
    private LocalDateTime finArret;
}
