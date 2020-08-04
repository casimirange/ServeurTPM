package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.entity.Departement;
//import com.example.demo.entity.Role;
//import com.example.demo.entity.Utilisateurs;
import com.example.demo.repository.DepartementRepository;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.util.RoleEnum;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class ServeurTpmApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ServeurTpmApplication.class, args);
		
//		initialiser la base de données
//		DepartementRepository departementRepository = ctx.getBean(DepartementRepository.class);
//		
//		departementRepository.save(new Departement("Brazil", "B350", "Ivan POMPEI"));
//		departementRepository.save(new Departement("Scierie", "B450", "Victor Sa'ah"));
//		departementRepository.save(new Departement("Contreplaqué", "B520", "Kevin Tchintcheu"));
//		departementRepository.save(new Departement("Placage", "B300", "Bassa Amanganga"));
//		
//		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
//		
//		Role roleUser = new Role(RoleEnum.ROLE_USER);
//		Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
//		Role roleSuperAdmin = new Role(RoleEnum.ROLE_SUPER_ADMIN);
//		
//		roleRepository.save(roleUser);
//		roleRepository.save(roleAdmin);
//		roleRepository.save(roleSuperAdmin);
//		
//		UtilisateurRepository utilisateurRepository = ctx.getBean(UtilisateurRepository.class);
//		Utilisateurs user = new Utilisateurs("Casimir", "Ange", 4688, "casimir.ouandji@alpiwood.com", "4688", "Analyste", true);
//		user.setRole(Arrays.asList(roleSuperAdmin, roleAdmin, roleUser));
//		utilisateurRepository.save(user);
//		
//		Utilisateurs user2 = new Utilisateurs("Bellino", "Stefano", 3156, "stefano.bellino@alpiwood.com", "3156", "Directeur Technique", true);
//		user2.setRole(Arrays.asList(roleAdmin, roleUser));
//		utilisateurRepository.save(user2);
//		
//		Utilisateurs user3 = new Utilisateurs("Atonlikeu", "Debras", 4057, "debras.atonlikeu@alpiwood.com", "4057", "Responsable Maintenance", true);
//		user3.setRole(Arrays.asList(roleUser));
//		utilisateurRepository.save(user3);
		
	}

}
