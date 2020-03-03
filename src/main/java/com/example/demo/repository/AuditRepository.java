package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Audit;

@org.springframework.stereotype.Repository
public interface AuditRepository extends Repository<Audit, Long> {

}
