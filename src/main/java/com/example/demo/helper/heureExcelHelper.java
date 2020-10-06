/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.helper;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Heures;
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
public class heureExcelHelper {
    public  String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
     String[] HEADERs = { "idMachine", "Code", "Nom", "Label", "Centre_cout"};
//     String[] HEADERs = { "idPanne", "Date", "IdMachine", "Description", "Cause", "Details", "Heure_arret", "Debut_inter",
//    "Fin_inter", "WT", "TTR", "DT", "idOperateur", "idTechnicien", "Outil", "Qte", "Ref", "Quart", "Etat", "Cont", "Numero"};
     String SHEET = "heure";
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


  public List<Heures> excelToTutorials(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
        
      List<Heures> tutorials = new ArrayList<Heures>();
      
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();
        Heures tutorial = new Heures();
        
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          switch (cellIdx) {
          case 0:
              System.out.println("date: "+currentCell.getLocalDateTimeCellValue().toLocalDate());
            tutorial.setDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
            break; 
            
          case 1:
              System.out.println("heure: "+currentCell.getNumericCellValue());
            tutorial.setHeure(currentCell.getNumericCellValue());
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
          case 2:
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
}
