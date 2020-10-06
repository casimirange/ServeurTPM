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
import org.apache.poi.ss.usermodel.CellType;
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
public class panExcelHelper {
    public  String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
     String[] HEADERs = { "idMachine", "Code", "Nom", "Label", "Centre_cout"};
//     String[] HEADERs = { "idPanne", "Date", "IdMachine", "Description", "Cause", "Details", "Heure_arret", "Debut_inter",
//    "Fin_inter", "WT", "TTR", "DT", "idOperateur", "idTechnicien", "Outil", "Qte", "Ref", "Quart", "Etat", "Cont", "Numero"};
     String SHEET = "panne";
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


  public List<Pannes> excelToTutorials(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
        
      List<Pannes> tutorials = new ArrayList<Pannes>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        Pannes tutorial = new Pannes();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          switch (cellIdx) {
          case 0:
              System.out.println("date: "+currentCell.getLocalDateTimeCellValue().toLocalDate());
            tutorial.setDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
            break; 
            
          case 1:
              System.out.println("description: "+currentCell.getStringCellValue());
            tutorial.setDescription(currentCell.getStringCellValue());
            break;

          case 2:
              System.out.println("cause: "+currentCell.getStringCellValue());
            tutorial.setCause(currentCell.getStringCellValue());
            break;

          case 3:
              System.out.println("details: "+currentCell.getStringCellValue());
            tutorial.setDetails(currentCell.getStringCellValue());
            break;
            
          case 4:
              System.out.println("heure_Arret: "+currentCell.getLocalDateTimeCellValue());
            tutorial.setHeure_arret(currentCell.getLocalDateTimeCellValue());
            break;

          case 5:
              System.out.println("debutInter: "+currentCell.getLocalDateTimeCellValue());
            tutorial.setDebut_inter(currentCell.getLocalDateTimeCellValue());
            break;

          case 6:
              System.out.println("finInter: "+currentCell.getLocalDateTimeCellValue());
            tutorial.setFin_inter(currentCell.getLocalDateTimeCellValue());
            break;

          case 7:
              System.out.println("wt: "+(int)currentCell.getNumericCellValue());
            tutorial.setWT((int)currentCell.getNumericCellValue());
            break;

          case 8:
              System.out.println("ttr: "+(int)currentCell.getNumericCellValue());
            tutorial.setTTR((int)currentCell.getNumericCellValue());
            break;

          case 9:
              System.out.println("dt: "+(int)currentCell.getNumericCellValue());
            tutorial.setDT((int)currentCell.getNumericCellValue());
            break;

          case 10:
            Object x = currentCell.getCellType();
              System.out.println("hum: "+ x);
              String zd = String.valueOf(x);
              if(zd.equals("NUMERIC")){
                  zd = "";
                  System.out.println("outil: "+zd);
              }else{
                  zd = currentCell.getStringCellValue();
                  System.out.println("outildddd: "+zd);
              }
              tutorial.setOutil(zd);
            break;

          case 11:
//              Object yr = currentCell.getCellType();
//              System.out.println("hum0: "+ yr);
              System.out.println("qte: "+(int)currentCell.getNumericCellValue());
            tutorial.setQte((int)currentCell.getNumericCellValue());
            break;

          case 12:
              Object y = currentCell.getCellType();
              System.out.println("hum2: "+ y);
              String z = String.valueOf(y);
              if(z.equals("NUMERIC")){
                  z = "";
                  System.out.println("outil: "+z);
              }else{
                  z = currentCell.getStringCellValue();
                  System.out.println("outildddd: "+z);
              }
//              System.out.println("ref: "+currentCell.getStringCellValue());
            tutorial.setRef(z);
            break;

          case 13:
              System.out.println("Quart: "+(int)currentCell.getNumericCellValue());
            tutorial.setQuart((int)currentCell.getNumericCellValue());
            break;

          case 14:
              Object yf = currentCell.getCellType();
              System.out.println("hum23: "+ yf);
              String g = "0";
              String g2 = "TRUE";
              System.out.println("Etat: "+(new Boolean(currentCell.getStringCellValue()).booleanValue()));
            tutorial.setEtat(new Boolean(currentCell.getStringCellValue()).booleanValue());
            break;

          case 15:
              System.out.println("Cont: "+(new Boolean(currentCell.getStringCellValue()).booleanValue()));
            tutorial.setCont(new Boolean(currentCell.getStringCellValue()).booleanValue());
            break;

          case 16:
              System.out.println("numero: "+currentCell.getStringCellValue());
            tutorial.setNumero(currentCell.getStringCellValue());
            break;

          default:
            break;
          }

          cellIdx++;
        }

        tutorials.add(tutorial);
        System.out.println("feuillets "+tutorials);
      }

      workbook.close();

      return tutorials;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public List<JSONObject> machine(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
      List<JSONObject> machine = new ArrayList<JSONObject>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          switch (cellIdx) {            
          case 17:
              System.out.println("double 5:"+(long)currentCell.getNumericCellValue());              
              double y = currentCell.getNumericCellValue();
              Long s = (new Double(y)).longValue();
              System.out.println("ache : "+s);
              Map<String, Object> response2 = new HashMap<>();
              response2.put("machine", s);
            json = new JSONObject(response2);
            break;
          default:
            break;
          }
          cellIdx++;
        }
        machine.add(json);
      }

      workbook.close();

      return machine;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public List<JSONObject> technicien(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
      List<JSONObject> technicien = new ArrayList<JSONObject>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          switch (cellIdx) {            
          case 19:
              System.out.println("double 5:"+(long)currentCell.getNumericCellValue());              
              double y = currentCell.getNumericCellValue();
              Long s = (new Double(y)).longValue();
              System.out.println("ache : "+s);
              Map<String, Object> response2 = new HashMap<>();
              response2.put("technicien", s);
            json = new JSONObject(response2);
            break;
          default:
            break;
          }
          cellIdx++;
        }
        technicien.add(json);
      }

      workbook.close();

      return technicien;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public List<JSONObject> operateur(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
      List<JSONObject> operateur = new ArrayList<JSONObject>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          switch (cellIdx) {            
          case 18:
              System.out.println("double 5:"+(long)currentCell.getNumericCellValue());    
              double y = currentCell.getNumericCellValue();
              Long s = (new Double(y)).longValue();
              System.out.println("ache : "+s);
              Map<String, Object> response2 = new HashMap<>();
              response2.put("operateur", s);
            json = new JSONObject(response2);
            break;
          default:
            break;
          }
          cellIdx++;
        }
        operateur.add(json);
      }

      workbook.close();

      return operateur;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
