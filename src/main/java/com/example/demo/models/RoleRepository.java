/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.models;



import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Casimir
 */
@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleName roleName);
    Boolean existsByName(RoleName roleName);
}
