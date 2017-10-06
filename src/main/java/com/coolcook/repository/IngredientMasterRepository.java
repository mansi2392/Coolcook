package com.coolcook.repository;

import com.coolcook.domain.IngredientMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IngredientMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientMasterRepository extends JpaRepository<IngredientMaster,Long> {
    
}
