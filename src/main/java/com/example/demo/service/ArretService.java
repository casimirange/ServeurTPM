/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.entity.Arrets;
import com.example.demo.entity.Machines;
import com.example.demo.reponses.ArretsReponse;
import com.example.demo.repository.ArretRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.service.inter.IArretService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Casimir
 */
@Service
public class ArretService implements IArretService {
    
    @Autowired
    private ArretRepository arretRepository ;
    
    @Autowired
    private MachineRepository machineRepository;

    @Override
    public List<Arrets> allArrets() {
        return arretRepository.findAll();
    }

    @Override
    public List<ArretsReponse> toutesPannes() {
        return arretRepository.TousLesArrets();
    }

    @Override
    public Arrets findOne(Long numero) {
        return arretRepository.findById(numero).get();
    }

    @Override
    public void addArret(Arrets arret, Long code_machine) {
        Machines machine = machineRepository.getOne(code_machine);

        arret.setMachines(machine);	
        arretRepository.save(arret);
    }

    @Override
    public void updateArret(Arrets arret, Long code_machine) {
        Machines machine = machineRepository.getOne(code_machine);

        arret.setMachines(machine);	
        arretRepository.save(arret);
    }

    @Override
    public void deleteArret(Long id) {
        Arrets arret = new Arrets();
        arret.setIdArret(id);
        arretRepository.delete(arret);
    }

    @Override
    public Arrets showArret(Long id) {
        return arretRepository.findById(id).get();
    }
    
}
