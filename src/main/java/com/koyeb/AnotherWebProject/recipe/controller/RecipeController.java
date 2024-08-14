package com.koyeb.AnotherWebProject.recipe.controller;

import com.koyeb.AnotherWebProject.recipe.db.RecipeDTO;
import com.koyeb.AnotherWebProject.recipe.db.RecipeEntity;
import com.koyeb.AnotherWebProject.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    // create
    @PostMapping()
    public ResponseEntity<RecipeEntity> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeEntity createdRecipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity.ok(createdRecipe);
    }

    // read
    @GetMapping()
    public Page<RecipeEntity> getRecipes(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(required = false) String search) {
        return recipeService.getRecipesByPageAndSearch(page, search);
    }
    @GetMapping("/all")
    public List<RecipeEntity> getAllRecipes() {
        return recipeService.getAllRecipes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok) // HTTP 200
                .orElse(ResponseEntity.notFound().build()); // HTTP 404
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<RecipeEntity> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build(); // HTTP 204 삭제 성공
    }
}
