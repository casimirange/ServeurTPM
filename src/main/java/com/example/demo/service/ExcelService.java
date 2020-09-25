/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
import com.example.demo.entity.Pannes;
import com.example.demo.entity.Techniciens;
import com.example.demo.helper.ExcelHelper;
import com.example.demo.helper.machineExcelHelper;
import com.example.demo.helper.panExcelHelper;
import com.example.demo.repository.DepartementRepository;
import com.example.demo.repository.LigneRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.OperateurRepository;
import com.example.demo.repository.PanneRepository;
import com.example.demo.repository.TechnicienRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Casimir
 */
@Service
public class ExcelService {
    @Autowired
    DepartementRepository repository;
    @Autowired
    PanneRepository panneRepository;
    @Autowired
    MachineRepository machineRepository;
    @Autowired
    TechnicienRepository technicienRepository;
    @Autowired
    OperateurRepository operateurRepository;
    @Autowired
    LigneRepository ligneRepository;

  public void save(MultipartFile file) {
    try {
      List<Departement> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public void saveMachine(MultipartFile file) {
      System.out.println("deux");
    try {
      List<Machines> tutorials = new machineExcelHelper().excelToTutorials(file.getInputStream());
      List<Machines> tutorials3 = new ArrayList<Machines>();
      List<JSONObject> tutorials2 = new machineExcelHelper().excelToTutorials2(file.getInputStream());
      
      Machines machine = new Machines();
		
      
        for (int i = 0; i< tutorials.size(); i++) {
            machine = tutorials.get(i);
            Lignes ligne = ligneRepository.getOne((long)tutorials2.get(i).get("ligne"));
            machine.setLignes(ligne);
            tutorials3.add(machine);
        }
      
      
      machineRepository.saveAll(tutorials3);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public void savePanne(MultipartFile file) {
    try {
      List<Pannes> tutorials = new panExcelHelper().excelToTutorials(file.getInputStream());
      List<Pannes> tutorials3 = new ArrayList<Pannes>();
      List<JSONObject> mach = new panExcelHelper().machine(file.getInputStream());
      List<JSONObject> tec = new panExcelHelper().technicien(file.getInputStream());
      List<JSONObject> op = new panExcelHelper().operateur(file.getInputStream());
      
      Pannes panne = new Pannes();
		
      
        for (int i = 0; i< tutorials.size(); i++) {
            panne = tutorials.get(i);
            Machines machine = machineRepository.getOne((long)mach.get(i).get("machine"));
            Techniciens technicien = technicienRepository.getOne((long)tec.get(i).get("technicien"));
            Operateurs operateur = operateurRepository.getOne((long)op.get(i).get("operateur"));
            panne.setMachines(machine);
            panne.setTechniciens(technicien);
            panne.setOperateurs(operateur);
            tutorials3.add(panne);
        }
      
      
      panneRepository.saveAll(tutorials3);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream loadPanne() {
    List<Machines> tutorials = machineRepository.findAll();

    ByteArrayInputStream in = new panExcelHelper().tutorialsToExcel(tutorials);
    return in;
  }

  public ByteArrayInputStream load() {
    List<Departement> tutorials = repository.findAll();

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
    return in;
  }

  public List<Departement> getAllTutorials() {
    return repository.findAll();
  }
}
