/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controller;


//import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.message.request.LoginForm;
import com.example.demo.message.request.SignUpForm;
import com.example.demo.message.response.JwtResponse;
import com.example.demo.message.response.ResponseMessage;
import com.example.demo.models.JwtProvider;
import com.example.demo.models.RoleName;
import com.example.demo.models.RoleRepository;
import com.example.demo.models.Roles;
import com.example.demo.models.UserRepository;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UtilisateurRepository;
//import com.example.demo.security.jwt.JwtProvider;

//import com.example.demo.util.RoleEnum;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


/**
 *
 * @author Casimir
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestApi {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository utilisateurRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
 
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
 
    SecurityContextHolder.getContext().setAuthentication(authentication);
 
    String jwt = jwtProvider.generateJwtToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 
    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
  }
 
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
    if (utilisateurRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
              HttpStatus.BAD_REQUEST);
    }
 
    if (utilisateurRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
          HttpStatus.BAD_REQUEST);
    }
 
    // Creating user's account
      User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));
 
      Set<String> strRoles = signUpRequest.getRole();
    Set<Roles> roles = new HashSet<>();
 
    strRoles.forEach(role -> {
      switch (role) {
      case "admin":
        Roles adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(adminRole);
 
        break;
      case "pm":
        Roles pmRole = roleRepository.findByName(RoleName.ROLE_PM)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(pmRole);
 
        break;  
      case "responsable":
        Roles responsableRole = roleRepository.findByName(RoleName.ROLE_RESPONSABLE)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(responsableRole);
 
        break;
      case "super_admin":
        Roles super_adminRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(super_adminRole);
 
        break;
      default:
        Roles userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
      }
    });
 
    user.setRoles(roles);
    utilisateurRepository.save(user);
 
    return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
  }
 
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpForm signUpRequest, @PathVariable Long id) {
//    if (utilisateurRepository.existsByUsername(signUpRequest.getUsername())) {
//      return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
//              HttpStatus.BAD_REQUEST);
//    }
// 
//    if (utilisateurRepository.existsByEmail(signUpRequest.getEmail())) {
//      return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
//          HttpStatus.BAD_REQUEST);
//    }
 
    // Creating user's account
//      User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
//        encoder.encode(signUpRequest.getPassword()));
      User user1 = utilisateurRepository.findById(id).get();
      user1.setEmail(signUpRequest.getEmail());
      user1.setName(signUpRequest.getName());
      user1.setUsername(signUpRequest.getUsername());
      
      if(!user1.getPassword().equals(signUpRequest.getPassword())){
          user1.setPassword(encoder.encode(signUpRequest.getPassword()));
      }
 
      Set<String> strRoles = signUpRequest.getRole();
    Set<Roles> roles = new HashSet<>();
 
    strRoles.forEach(role -> {
      switch (role) {
      case "admin":
        Roles adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(adminRole);
 
        break;
      case "pm":
        Roles pmRole = roleRepository.findByName(RoleName.ROLE_PM)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(pmRole);
 
        break;  
      case "responsable":
        Roles responsableRole = roleRepository.findByName(RoleName.ROLE_RESPONSABLE)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(responsableRole);
 
        break;
      case "super_admin":
        Roles super_adminRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(super_adminRole);
 
        break;
      default:
        Roles userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
      }
    });
 
    user1.setRoles(roles);
    utilisateurRepository.save(user1);
 
    return new ResponseEntity<>(new ResponseMessage("User updated successfully!"), HttpStatus.OK);
  }
  
  @GetMapping
  public List<User> users(){      
      return utilisateurRepository.findAll();
  }
  
    @DeleteMapping("/{id}")
    public void deleDepartement(@PathVariable Long id) {
            utilisateurRepository.delete(utilisateurRepository.findById(id).get());
    }
}
