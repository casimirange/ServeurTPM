///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.demo.service.impl;
//
//import com.example.demo.entity.Utilisateurs;
//import com.example.demo.models.User;
//import com.example.demo.models.UserRepository;
//import com.example.demo.repository.UtilisateurRepository;
//import com.example.demo.security.service.UserPrinciple;
//import javax.transaction.Transactional;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
///**
// *
// * @author Casimir
// */
//public class UserDetailsServiceImpl implements UserDetailsService{
//    @Autowired
//    UserRepository userRepository;
// 
//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// 
//      User user = userRepository.findByUsername(username).orElseThrow(
//        () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
// 
//    return UserPrinciple.build(user);
//  }
//}
