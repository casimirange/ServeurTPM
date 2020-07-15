///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.demo.models;
//
///**
// *
// * @author Casimir
// */
//import com.example.demo.entity.Role;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
// 
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
// 
//import org.hibernate.annotations.NaturalId;
// 
//@Entity
//@Table(name = "users")
//public class User{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
// 
////    @NotBlank
////    @Size(min=3, max = 50)
//    private String name;
// 
////    @NotBlank
////    @Size(min=3, max = 50)
//    private String username;
// 
////    @NaturalId
////    @NotBlank
////    @Size(max = 50)
////    @Email
//    private String email;
// 
////    @NotBlank
////    @Size(min=6, max = 100)
//    private String password;
// 
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name="USER_ROLE",
//	joinColumns = {@JoinColumn(name="USER_ID")},
//	inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")} )
//	private Set<Role> role;
// 
//    public User() {}
// 
//    public User(String name, String username, String email, String password) {
//        this.name = name;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
// 
//    public Long getId() {
//        return id;
//    }
// 
//    public void setId(Long id) {
//        this.id = id;
//    }
// 
//    public String getUsername() {
//        return username;
//    }
// 
//    public void setUsername(String username) {
//        this.username = username;
//    }
// 
//    public String getName() {
//        return name;
//    }
// 
//    public void setName(String name) {
//        this.name = name;
//    }
// 
//    public String getEmail() {
//        return email;
//    }
// 
//    public void setEmail(String email) {
//        this.email = email;
//    }
// 
//    public String getPassword() {
//        return password;
//    }
// 
//    public void setPassword(String password) {
//        this.password = password;
//    }
// 
//    public Set<Role> getRoles() {
//        return role;
//    }
// 
//    public void setRoles(Set<Role> roles) {
//        this.role = roles;
//    }
//}
