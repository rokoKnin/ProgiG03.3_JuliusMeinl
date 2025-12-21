package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.RezervirajSobu;
import com.juliusmeinl.backend.model.RezervirajSobuId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RezervirajSobuRepository extends JpaRepository<RezervirajSobu, RezervirajSobuId> {
}
