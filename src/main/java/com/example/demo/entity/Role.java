//package com.example.demo.entity;
//
//import com.example.demo.models.RoleName;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.example.demo.util.RoleEnum;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import org.hibernate.annotations.NaturalId;
//
//@Entity
//@Table(name = "roles")
//public class Role {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
// 
//    @Enumerated(EnumType.STRING)
//    @NaturalId
//    @Column(length = 60)
//    private RoleName name;
// 
//    public Role() {}
// 
//    public Role(RoleName name) {
//        this.name = name;
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
//    public RoleName getName() {
//        return name;
//    }
// 
//    public void setName(RoleName name) {
//        this.name = name;
//    }
//}
