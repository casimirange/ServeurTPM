/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.reponses;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class StatsRepose {
    private LocalDate date;
    private Long nbre;
    private Long DT;        
}
