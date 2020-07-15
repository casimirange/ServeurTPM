///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.demo.models;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// *
// * @author Casimir
// */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
// 
//  @Autowired
//  UserRepository userRepository;
// 
//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// 
//    User user = userRepository.findByUsername(username).orElseThrow(
//        () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
// 
//    return UserPrinciple.build(user);
//  }
//}
