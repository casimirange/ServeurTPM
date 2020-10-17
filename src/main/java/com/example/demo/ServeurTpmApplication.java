package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.entity.Departement;
import com.example.demo.entity.User;
import com.example.demo.message.response.ResponseMessage;
import com.example.demo.models.RoleName;
import com.example.demo.models.RoleRepository;
import com.example.demo.models.Roles;
//import com.example.demo.entity.Role;
//import com.example.demo.entity.Utilisateurs;
import com.example.demo.repository.DepartementRepository;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.util.RoleEnum;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ServeurTpmApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(ServeurTpmApplication.class, args);
//		
//                PasswordEncoder encoder = ctx.getBean(PasswordEncoder.class);
//                
////		initialiser la base de données
////		DepartementRepository departementRepository = ctx.getBean(DepartementRepository.class);
////		
////		departementRepository.save(new Departement("Brazil", "B350", "Ivan POMPEI"));
////		departementRepository.save(new Departement("Scierie", "B450", "Victor Sa'ah"));
////		departementRepository.save(new Departement("Contreplaqué", "B520", "Kevin Tchintcheu"));
////		departementRepository.save(new Departement("Placage", "B300", "Bassa Amanganga"));
////		
//                RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
//		
//                Roles roleUser = new Roles(RoleName.ROLE_USER);
//		Roles roleAdmin = new Roles(RoleName.ROLE_ADMIN);
//		Roles roleSuperAdmin = new Roles(RoleName.ROLE_SUPER_ADMIN);
//		Roles rolePm = new Roles(RoleName.ROLE_PM);
//		Roles roleResponsable = new Roles(RoleName.ROLE_RESPONSABLE);
//		
//                if (!roleRepository.existsByName(RoleName.ROLE_USER)) {
//                    roleRepository.save(roleUser);
//                }
//		
//                if (!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
//                    roleRepository.save(roleAdmin);
//                }
//		
//                if (!roleRepository.existsByName(RoleName.ROLE_PM)) {
//                    roleRepository.save(rolePm);
//                }
//		
//                if (!roleRepository.existsByName(RoleName.ROLE_SUPER_ADMIN)) {
//                    roleRepository.save(roleSuperAdmin);
//                }
//		
//                if (!roleRepository.existsByName(RoleName.ROLE_RESPONSABLE)) {
//                    roleRepository.save(roleResponsable);
//                }
//		
////		
//		UserRepository utilisateurRepository = ctx.getBean(UserRepository.class);
////		Utilisateurs user = new Utilisateurs("Casimir", "Ange", 4688, "casimir.ouandji@alpiwood.com", "4688", "Analyste", true);
////		user.setRole(Arrays.asList(roleSuperAdmin, roleAdmin, roleUser));
////		utilisateurRepository.save(user);
////		
////		Utilisateurs user2 = new Utilisateurs("Bellino", "Stefano", 3156, "stefano.bellino@alpiwood.com", "3156", "Directeur Technique", true);
////		user2.setRole(Arrays.asList(roleAdmin, roleUser));
////		utilisateurRepository.save(user2);
////		
////		Utilisateurs user3 = new Utilisateurs("Atonlikeu", "Debras", 4057, "debras.atonlikeu@alpiwood.com", "4057", "Responsable Maintenance", true);
////		user3.setRole(Arrays.asList(roleUser));
////		utilisateurRepository.save(user3);
//
//                String username = "ouandji casimir";
//                String email = "angecasimir41@gmail.com";
//
//                if (utilisateurRepository.existsByUsername(username)) {
//                    System.out.println("Fail -> Username is already taken!");
//                  }
// 
//                else if (utilisateurRepository.existsByEmail(email)) {
//                  System.out.println("Fail -> Email is already in use!");
//                } else{
//                    
//                
// 
//    // Creating user's account    
//        User user = new User();
//        user.setName("noumome");
//        user.setEmail(email);
//        user.setUsername(username);
//        user.setPassword(encoder.encode("ange3000"));
//        Set<Roles> roles = new HashSet<>();
//    
//        Roles super_adminRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
//            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//        roles.add(super_adminRole);
//        
//        user.setRoles(roles);
//                    System.out.println("userssss \n"+user);
//            utilisateurRepository.save(user);
//            System.out.println("Utilisateur enregistré");
//        }
        
		
	}

}
