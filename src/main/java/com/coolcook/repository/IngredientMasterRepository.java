package com.coolcook.repository;

import com.coolcook.domain.Category;
import com.coolcook.domain.IngredientMaster;
import com.coolcook.service.dto.IngredientMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the IngredientMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientMasterRepository extends JpaRepository<IngredientMaster, Long> {

    /**
     * Find by Category 
     *
     * @param category
     * @param pageable
     * @return
     */
    public Page<IngredientMaster> findByCategory(Category category, Pageable pageable);
}
