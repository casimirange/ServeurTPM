/////*
//// * To change this license header, choose License Headers in Project Properties.
//// * To change this template file, choose Tools | Templates
//// * and open the template in the editor.
//// */
////package com.example.demo.service;
////
//////import com.example.demo.entity.Utilisateurs;
//////import com.example.demo.repository.UtilisateurRepository;
////import com.example.demo.service.inter.IUtilisateurService;
////import java.util.List;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
/////**
//// *
//// * @author Casimir
//// */
////@Service
////public class UtilisateurService implements IUtilisateurService{
////
////
////    @Autowired
////    private UtilisateurRepository utilisateurRepository;
////
////    public UtilisateurRepository getUtilisateurRepository() {
////        return utilisateurRepository;
////    }
////
////    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
////        this.utilisateurRepository = utilisateurRepository;
////    }
////    
////    
////    @Override
////    public List<Utilisateurs> getAllUsers() {
////        return utilisateurRepository.findAll();
////    }
////    
////    @Override
////    public Utilisateurs findBy(int matricule) {
////        return utilisateurRepository.findByMatricule(matricule);
////    }
////
////    @Override
////    public void addUser(Utilisateurs user) {
////        utilisateurRepository.save(user);
////    }
////
////    @Override
////    public void updateUser(Utilisateurs user) {
////        utilisateurRepository.save(user);
////    }
////
////    @Override
////    public void deleteUser(Long id) {
////        Utilisateurs user = new Utilisateurs();
////        user.setId(id);
////        utilisateurRepository.delete(user);
////    }
////
////    @Override
////    public Utilisateurs search(String username) {
////        return utilisateurRepository.findByUsername(username);
////    }
////
////    @Override
////    public void desactivatedUser(int matricule) {
////        Utilisateurs user = new Utilisateurs();
////        user = utilisateurRepository.findByMatricule(matricule);
////        if(user.isEnable()){
////            user.setEnable(false);
////        }else{
////            user.setEnable(true);
////        }
////        utilisateurRepository.save(user);
////    }
////
////    @Override
////    public void activatedUser(int matricule) {
////        
////    }
////    
////}
