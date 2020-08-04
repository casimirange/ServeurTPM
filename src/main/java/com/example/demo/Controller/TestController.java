/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;

//import com.example.demo.entity.Utilisateurs;
//import com.example.demo.model.User;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casimir
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestController {
    @GetMapping("/api/test/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
      return ">>> User Contents!";
    }

    @GetMapping("/api/test/responsable")
    @PreAuthorize("hasRole('RESPONSABLE') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public String responsableAccess() {
      return ">>> Responsable contents!";
    }

    @GetMapping("/api/test/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public String adminAccess() {
      return ">>> Admin Contents";
    }
    
    @GetMapping("/api/test/super_admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String super_adminAccess() {
      return ">>> Super Admin Contents";
    }
    
    @GetMapping("/api/test/pm")
  @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
  public String projectManagementAccess() {
    return ">>> Board Management Project";
  }
    
}
