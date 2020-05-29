/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.entity.Heures;
import com.example.demo.entity.Machines;
import com.example.demo.repository.HeureRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.service.inter.IHeureService;
import java.time.LocalDate;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Casimir
 */
@Service
public class HeuresServices implements IHeureService{

    @Autowired
    private HeureRepository heureRepository;
    
    @Autowired
    private MachineRepository machineRepository;
    
    @Override
    public List<Heures> allHeures() {
        return heureRepository.findAll();
    }

    @Override
    public List<JSONObject> toutesHeures() {
        return heureRepository.Heures();
    }

    @Override
    public Heures findOne(Long id) {
        return heureRepository.findById(id).get();
    }

    @Override
    public void addHeures(Heures heure, Long code_machine) {
        Machines machine = machineRepository.getOne(code_machine);

        heure.setMachines(machine);	
        heureRepository.save(heure);
    }

    @Override
    public void updateHeures(Heures heure, Long code_machine) {
        Machines machine = machineRepository.getOne(code_machine);

        heure.setMachines(machine);	
        heureRepository.save(heure);
    }

    @Override
    public void deleteHeures(Long id) {
        Heures heure = new Heures();
        heure.setIdHeure(id);
        heureRepository.delete(heure);
    }
    
}
