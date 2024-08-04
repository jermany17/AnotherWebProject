package com.koyeb.AnotherWebProject.recipe.service;

import com.koyeb.AnotherWebProject.recipe.db.RecipeEntity;
import com.koyeb.AnotherWebProject.recipe.db.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    // create
    public RecipeEntity createRecipe(RecipeEntity recipe) {
        return recipeRepository.save(recipe);
    }

    // read
    public Optional<RecipeEntity> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }
    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // update
    public RecipeEntity updateRecipe(Long id, RecipeEntity recipeDetails) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setUserName(recipeDetails.getUserName());
        recipe.setFoodName(recipeDetails.getFoodName());
        recipe.setAmount(recipeDetails.getAmount());
        recipe.setCookingTime(recipeDetails.getCookingTime());
        recipe.setDifficulty(recipeDetails.getDifficulty());
        recipe.setHowToCook(recipeDetails.getHowToCook());
        recipe.setImagePath(recipeDetails.getImagePath());

        return recipeRepository.save(recipe);
    }

    // delete
    public void deleteRecipe(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipeRepository.delete(recipe);
    }
}
