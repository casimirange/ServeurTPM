/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.inter.IRoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Casimir
 */
@Service
public class RoleService implements IRoleService{

    @Autowired
	private RoleRepository roleRepository ;
    
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
    
}
