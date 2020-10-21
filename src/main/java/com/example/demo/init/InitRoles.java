/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.init;

import com.example.demo.ServeurTpmApplication;
import com.example.demo.entity.Departement;
import com.example.demo.entity.User;
import com.example.demo.models.RoleName;
import com.example.demo.models.RoleRepository;
import com.example.demo.models.Roles;
import com.example.demo.models.UserRepository;
import com.example.demo.repository.DepartementRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Casimir
 */

@Component
@Order(1)
public class InitRoles implements ApplicationRunner{

    @Autowired
    DepartementRepository departementRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    UserRepository utilisateurRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {;
        System.out.println("initialisation des roles");        
        Roles roleUserAlpi = new Roles(RoleName.ROLE_USER_ALPI);
        Roles roleUserMindourou = new Roles(RoleName.ROLE_USER_MINDOUROU);
        Roles roleAdmin = new Roles(RoleName.ROLE_ADMIN);
        Roles roleSuperAdmin = new Roles(RoleName.ROLE_SUPER_ADMIN);
        Roles roleRespMAINT = new Roles(RoleName.ROLE_RESP_MAINTENANCE);
        Roles roleRespBRA = new Roles(RoleName.ROLE_RESP_BRAZIL);
        Roles roleRespCP = new Roles(RoleName.ROLE_RESP_CP);
        Roles roleRespSC = new Roles(RoleName.ROLE_RESP_SCIERIE);
        Roles roleRespPL = new Roles(RoleName.ROLE_RESP_PLACAGE);
        Roles roleRespMIND = new Roles(RoleName.ROLE_RESP_MINDOUROU);

        if (!roleRepository.existsByName(RoleName.ROLE_USER_ALPI)) {
            roleRepository.save(roleUserAlpi);
        }
        if (!roleRepository.existsByName(RoleName.ROLE_USER_MINDOUROU)) {
            roleRepository.save(roleUserMindourou);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
            roleRepository.save(roleAdmin);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_RESP_MAINTENANCE)) {
            roleRepository.save(roleRespMAINT);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_SUPER_ADMIN)) {
            roleRepository.save(roleSuperAdmin);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_RESP_BRAZIL)) {
            roleRepository.save(roleRespBRA);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_RESP_CP)) {
            roleRepository.save(roleRespCP);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_RESP_SCIERIE)) {
            roleRepository.save(roleRespSC);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_RESP_PLACAGE)) {
            roleRepository.save(roleRespPL);
        }

        if (!roleRepository.existsByName(RoleName.ROLE_RESP_MINDOUROU)) {
            roleRepository.save(roleRespMIND);
        }
               
    }
    
}
