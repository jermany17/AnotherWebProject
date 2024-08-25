package com.koyeb.AnotherWebProject.recipe.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    // foodName, pageable
    Page<RecipeEntity> findByFoodNameContainingIgnoreCaseOrderByIdDesc(String search, Pageable pageable);

    // pageable
    Page<RecipeEntity> findAllByOrderByIdDesc(Pageable pageable);

    // difficulty 기준으로 오름차순 정렬
    Page<RecipeEntity> findAllByOrderByDifficultyAsc(Pageable pageable);

    // difficulty 기준으로 내림차순 정렬
    Page<RecipeEntity> findAllByOrderByDifficultyDesc(Pageable pageable);
}