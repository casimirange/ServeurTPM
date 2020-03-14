package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

}
