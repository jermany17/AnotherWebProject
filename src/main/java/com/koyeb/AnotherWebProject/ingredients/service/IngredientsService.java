package com.koyeb.AnotherWebProject.ingredients.service;

import com.koyeb.AnotherWebProject.ingredients.db.IngredientsDTO;
import com.koyeb.AnotherWebProject.ingredients.db.IngredientsEntity;
import com.koyeb.AnotherWebProject.ingredients.db.IngredientsRepository;
import com.koyeb.AnotherWebProject.recipe.db.RecipeEntity;
import com.koyeb.AnotherWebProject.recipe.db.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsService {
    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // create
    public IngredientsEntity createIngredient(IngredientsDTO ingredientsDTO) {
        // 레시피를 찾기, recipeid 존재 확인
        RecipeEntity recipe = recipeRepository.findById(ingredientsDTO.getRecipeid())
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + ingredientsDTO.getRecipeid()));

        // 찾은 레시피를 사용하여 새로운 IngredientsEntity를 생성
        IngredientsEntity ingredient = IngredientsEntity.builder()
                .recipe(recipe)
                .ingredient(ingredientsDTO.getIngredient())
                .build();

        return ingredientsRepository.save(ingredient);
    }

    // read
    public Optional<IngredientsEntity> getIngredientById(Long id) {
        return ingredientsRepository.findById(id);
    }
    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsRepository.findAll();
    }

    // update
    public IngredientsEntity updateIngredient(Long id, IngredientsEntity ingredientsDetails) {
        IngredientsEntity ingredient = ingredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        ingredient.setIngredient(ingredientsDetails.getIngredient());

        return ingredientsRepository.save(ingredient);
    }

    // delete
    public void deleteIngredient(Long id) {
        IngredientsEntity ingredient = ingredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        ingredientsRepository.delete(ingredient);
    }
}
