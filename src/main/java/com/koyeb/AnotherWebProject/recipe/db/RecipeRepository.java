package com.koyeb.AnotherWebProject.recipe.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    // foodName, pageable
    Page<RecipeEntity> findByFoodNameContainingIgnoreCaseOrderByIdDesc(String foodName, Pageable pageable);

    // pageable
    Page<RecipeEntity> findAllByOrderByIdDesc(Pageable pageable);
}
