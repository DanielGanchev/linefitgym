package com.appfitgym.repository;

import com.appfitgym.model.entities.FitnessProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessProgramRepository extends JpaRepository<FitnessProgram, Long> {
}
