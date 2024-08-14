package com.koyeb.AnotherWebProject.ingredients.db;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Long> {
    @Modifying // 데이터가 변경되는 퀴리이면 지정
    @Transactional
    @Query("DELETE FROM ingredients ie WHERE ie.id = :id")
    void deleteIngredientById(@Param("id") Long id);
}
