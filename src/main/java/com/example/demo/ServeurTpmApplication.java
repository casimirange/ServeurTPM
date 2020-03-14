package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.entity.Departement;
import com.example.demo.repository.DepartementRepository;

@SpringBootApplication
public class ServeurTpmApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ServeurTpmApplication.class, args);
		
		//initialiser la base de données
		//DepartementRepository departementRepository = ctx.getBean(DepartementRepository.class);
		
		//departementRepository.save(new Departement("Brazil", "B350", "Ivan POMPEI"));
		//departementRepository.save(new Departement("Scierie", "B450", "Victor Sa'ah"));
		//departementRepository.save(new Departement("Contreplaqué", "B520", "Kevin Tchintcheu"));
		//departementRepository.save(new Departement("Placage", "B300", "Bassa Amanganga"));
	}

}
