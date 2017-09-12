package com.coolcook.app.repository;

import com.coolcook.app.domain.IngredientMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IngredientMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientMasterRepository extends JpaRepository<IngredientMaster,Long> {
    
}
