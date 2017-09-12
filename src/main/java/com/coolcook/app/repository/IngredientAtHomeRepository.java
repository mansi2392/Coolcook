package com.coolcook.app.repository;

import com.coolcook.app.domain.IngredientAtHome;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IngredientAtHome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientAtHomeRepository extends JpaRepository<IngredientAtHome,Long> {
    
}
