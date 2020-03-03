package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Maintenance;

@org.springframework.stereotype.Repository
public interface MaintenanceRepository extends Repository<Maintenance, Long> {

	
}
