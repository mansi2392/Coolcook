package com.coolcook.app.repository;

import com.coolcook.app.domain.RecipeMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RecipeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeMasterRepository extends JpaRepository<RecipeMaster,Long> {
    
    @Query("select distinct recipe_master from RecipeMaster recipe_master left join fetch recipe_master.ingredientQtyMaps")
    List<RecipeMaster> findAllWithEagerRelationships();

    @Query("select recipe_master from RecipeMaster recipe_master left join fetch recipe_master.ingredientQtyMaps where recipe_master.id =:id")
    RecipeMaster findOneWithEagerRelationships(@Param("id") Long id);
    
}
