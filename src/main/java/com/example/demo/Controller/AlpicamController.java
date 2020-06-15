/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

import com.example.demo.repository.AlpicamRepository;
import java.util.Calendar;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casimir
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin/alpicam")
public class AlpicamController {
    @Autowired
    private AlpicamRepository alpicamRepository;
    
    String mts, j, l, n  ;
    
    @GetMapping("/typePanneThisYear")
    public List<JSONObject> countThisYear(){
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(0);
            int year = cal.get(Calendar.YEAR);
                mts = String.valueOf(year);
        return alpicamRepository.typePanne(mts);
    }
}
