package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.Machines;

@org.springframework.stereotype.Repository
public interface MachineRepository extends Repository<Machines, Long> {

}
