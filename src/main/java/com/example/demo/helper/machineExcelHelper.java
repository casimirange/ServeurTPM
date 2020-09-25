/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.helper;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Lignes;
import com.example.demo.entity.Machines;
import com.example.demo.entity.Operateurs;
import com.example.demo.entity.Pannes;
import com.example.demo.entity.Techniciens;
import com.example.demo.repository.LigneRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.OperateurRepository;
import com.example.demo.repository.TechnicienRepository;
import com.example.demo.service.LigneService;
import com.example.demo.service.MachinesService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Casimir
 */
public class machineExcelHelper {
    public  String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
     String[] HEADERs = { "idMachine", "Code", "Nom", "Label", "Centre_cout"};
//     String[] HEADERs = { "idPanne", "Date", "IdMachine", "Description", "Cause", "Details", "Heure_arret", "Debut_inter",
//    "Fin_inter", "WT", "TTR", "DT", "idOperateur", "idTechnicien", "Outil", "Qte", "Ref", "Quart", "Etat", "Cont", "Numero"};
     String SHEET = "mach";
    Lignes ligne ;
    JSONObject json;   
    

  public boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    } 

    return true;
  }

  public  ByteArrayInputStream tutorialsToExcel(List<Machines> tutorials) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Machines tutorial : tutorials) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(tutorial.getIdMachine());
        row.createCell(1).setCellValue(tutorial.getCode());
        row.createCell(2).setCellValue(tutorial.getNom());
        row.createCell(3).setCellValue(tutorial.getLabel());
//        row.createCell(4).setCellValue(tutorial.getLignes().getIdLigne());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public List<Machines> excelToTutorials(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
        
      List<Machines> tutorials = new ArrayList<Machines>();
      List<JSONObject> li = new ArrayList<JSONObject>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        Machines tutorial = new Machines();
//        Pannes tutorial = new Pannes();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          long x = 1;
            System.out.println("valeur: "+x);
//          mach = machineRepository.findById(x).get();
//          tec = technicienRepository.findById(x).get();
//          op = operateurRepository.findById(x).get();
//          Lignes lignes  = ligneRepository.findById((long)4).get();
//            System.out.println("ligne "+lignes);
          switch (cellIdx) {
          case 0:
              System.out.println("double "+currentCell.getStringCellValue());
//            tutorial.setDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
            tutorial.setCode(currentCell.getStringCellValue());
            break;

//          

          case 1:
              System.out.println("double 2: "+currentCell.getStringCellValue());
            tutorial.setNom(currentCell.getStringCellValue());
            break;

          case 2:
              System.out.println("double 3: "+currentCell.getStringCellValue());
            tutorial.setLabel(currentCell.getStringCellValue());
            break;

          case 3:
              System.out.println("double 4:"+currentCell.getStringCellValue());
            tutorial.setCentre_cout(currentCell.getStringCellValue());
            break;
            
//          case 4:
//              System.out.println("double 5:"+(long)currentCell.getNumericCellValue());
//    //          tutorial.setMachines(mach);
//              double y = currentCell.getNumericCellValue();
//              Long s = (new Double(y)).longValue();
//              System.out.println("ache : "+s);
//              Map<String, Object> response2 = new HashMap<>();
//              response2.put("ligne", s);
//            json = new JSONObject(response2);
//            break;

//          case 5:
//            tutorial.setHeure_arret(currentCell.getLocalDateTimeCellValue());
//            break;
//
//          case 6:
//            tutorial.setDebut_inter(currentCell.getLocalDateTimeCellValue());
//            break;
//
//          case 7:
//            tutorial.setFin_inter(currentCell.getLocalDateTimeCellValue());
//            break;
//
//          case 8:
//            tutorial.setWT((int)currentCell.getNumericCellValue());
//            break;
//
//          case 9:
//            tutorial.setTTR((int)currentCell.getNumericCellValue());
//            break;
//
//          case 10:
//            tutorial.setDT((int)currentCell.getNumericCellValue());
//            break;
//
//          case 11:
//            tutorial.setOperateurs(op);
//            break;
//
//          case 12:
//            tutorial.setTechniciens(tec);
//            break;
//
//          case 13:
//            tutorial.setOutil(currentCell.getStringCellValue());
//            break;
//
//          case 14:
//            tutorial.setQte((int)currentCell.getNumericCellValue());
//            break;
//
//          case 15:
//            tutorial.setRef(currentCell.getStringCellValue());
//            break;
//
//          case 16:
//            tutorial.setQuart((int)currentCell.getNumericCellValue());
//            break;
//
//          case 17:
//            tutorial.setEtat(currentCell.getBooleanCellValue());
//            break;
//
//          case 18:
//            tutorial.setCont(currentCell.getBooleanCellValue());
//            break;
//
//          case 19:
//            tutorial.setNumero(currentCell.getStringCellValue());
//            break;

          default:
            break;
          }

          cellIdx++;
        }

        tutorials.add(tutorial);
        li.add(json);
        System.out.println("feuillets "+tutorials);
        System.out.println("lignes "+json);
      }

      workbook.close();

      return tutorials;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public List<JSONObject> excelToTutorials2(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
        
      List<Machines> tutorials = new ArrayList<Machines>();
      List<JSONObject> li = new ArrayList<JSONObject>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        Machines tutorial = new Machines();
//        Pannes tutorial = new Pannes();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          long x = 1;
            System.out.println("valeur: "+x);
//          mach = machineRepository.findById(x).get();
//          tec = technicienRepository.findById(x).get();
//          op = operateurRepository.findById(x).get();
//          Lignes lignes  = ligneRepository.findById((long)4).get();
//            System.out.println("ligne "+lignes);
          switch (cellIdx) {
          case 0:
              System.out.println("double "+currentCell.getStringCellValue());
//            tutorial.setDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
            tutorial.setCode(currentCell.getStringCellValue());
            break;

//          

          case 1:
              System.out.println("double 2: "+currentCell.getStringCellValue());
            tutorial.setNom(currentCell.getStringCellValue());
            break;

          case 2:
              System.out.println("double 3: "+currentCell.getStringCellValue());
            tutorial.setLabel(currentCell.getStringCellValue());
            break;

          case 3:
              System.out.println("double 4:"+currentCell.getStringCellValue());
            tutorial.setCentre_cout(currentCell.getStringCellValue());
            break;
            
          case 4:
              System.out.println("double 5:"+(long)currentCell.getNumericCellValue());
    //          tutorial.setMachines(mach);
              double y = currentCell.getNumericCellValue();
              Long s = (new Double(y)).longValue();
              System.out.println("ache : "+s);
              Map<String, Object> response2 = new HashMap<>();
              response2.put("ligne", s);
            json = new JSONObject(response2);
            break;

          default:
            break;
          }

          cellIdx++;
        }

        tutorials.add(tutorial);
        li.add(json);
        System.out.println("feuillets "+tutorials);
        System.out.println("lignes "+json);
      }

      workbook.close();

      return li;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
