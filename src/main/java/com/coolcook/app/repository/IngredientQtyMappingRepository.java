package com.coolcook.app.repository;

import com.coolcook.app.domain.IngredientQtyMapping;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IngredientQtyMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientQtyMappingRepository extends JpaRepository<IngredientQtyMapping,Long> {
    
}
