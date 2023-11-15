package com.appfitgym.repository;

import com.appfitgym.model.entities.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository  extends JpaRepository<Diet, Long> {
}
