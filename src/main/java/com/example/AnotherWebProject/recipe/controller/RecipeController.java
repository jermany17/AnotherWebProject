package com.example.AnotherWebProject.recipe.controller;

import com.example.AnotherWebProject.recipe.dto.RecipeDTO;
import com.example.AnotherWebProject.recipe.domain.RecipeEntity;
import com.example.AnotherWebProject.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // create
    @PostMapping()
    public ResponseEntity<?> createRecipe(@RequestBody RecipeDTO recipeDTO, Authentication authentication) {
        try {
            RecipeEntity createdRecipe = recipeService.createRecipe(recipeDTO, authentication);
            return ResponseEntity.ok(createdRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // read
    @GetMapping()
    public ResponseEntity<?> getRecipes(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(required = false) String search) {
        try {
            Page<RecipeEntity> recipes = recipeService.getRecipesByPageAndSearch(page, search);
            return ResponseEntity.ok(recipes.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipes() {
        try {
            List<RecipeEntity> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        try {
            return recipeService.getRecipeById(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(404).body(Map.of("message", "레시피를 찾을 수 없습니다.")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/difficulty")
    public ResponseEntity<?> getRecipesSortedByDifficulty(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "desc") String direction) {
        try {
            Page<RecipeEntity> recipes = recipeService.getRecipesByDifficulty(page, direction);
            return ResponseEntity.ok(recipes.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id,
                                          @RequestBody RecipeDTO recipeDTO,
                                          Authentication authentication) {
        try {
            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipeDTO, authentication);
            return ResponseEntity.ok(updatedRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message", e.getMessage()));
        }
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id, Authentication authentication) {
        try {
            recipeService.deleteRecipe(id, authentication);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message", e.getMessage()));
        }
    }
}
