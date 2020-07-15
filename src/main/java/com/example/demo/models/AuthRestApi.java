///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.demo.models;
//
//import com.example.demo.message.request.LoginForm;
//import com.example.demo.message.request.SignUpForm;
//import com.example.demo.message.response.JwtResponse;
//import com.example.demo.message.response.ResponseMessage;
//import java.util.HashSet;
//import java.util.Set;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.core.userdetails.UserDetails;
//
//
///**
// *
// * @author Casimir
// */
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
//public class AuthRestApi {
//    
//    @Autowired
//    AuthenticationManager authenticationManager;
//    
//    @Autowired
//    UserRepository utilisateurRepository;
//    
//    @Autowired
//    RoleRepository roleRepository;
//    
//    @Autowired
//    PasswordEncoder encoder;
//    
//    @Autowired
//    JwtProvider jwtProvider;
//    
//    @PostMapping("/signin")
//  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
// 
//    Authentication authentication = authenticationManager.authenticate(
//        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
// 
//    SecurityContextHolder.getContext().setAuthentication(authentication);
// 
//    String jwt = jwtProvider.generateJwtToken(authentication);
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
// 
//    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
//  }
// 
//  @PostMapping("/signup")
//  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
//    if (utilisateurRepository.existsByUsername(signUpRequest.getUsername())) {
//      return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
//              HttpStatus.BAD_REQUEST);
//    }
// 
//    if (utilisateurRepository.existsByEmail(signUpRequest.getEmail())) {
//      return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
//          HttpStatus.BAD_REQUEST);
//    }
// 
//    // Creating user's account
//      User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
//        encoder.encode(signUpRequest.getPassword()));
// 
//      Set<String> strRoles = signUpRequest.getRole();
////    Set<Role> roles = new HashSet<>();
//    Set<Roles> roless = new HashSet<>();
// 
//    strRoles.forEach(role -> {
//      switch (role) {
//      case "admin":
//          Roles adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//        roless.add(adminRole);
// 
//        break;
//      case "responsable":
//        Roles responsableRole = roleRepository.findByName(RoleName.ROLE_PM)
//            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//        roless.add(responsableRole);
// 
//        break;
//      default:
//        Roles userRole = roleRepository.findByName(RoleName.ROLE_USER)
//            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//        roless.add(userRole);
//      }
//    });
// 
//    user.setRoles(user.getRoles());
//    utilisateurRepository.save(user);
// 
//    return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
//  }
//}
