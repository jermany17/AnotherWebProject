package com.koyeb.AnotherWebProject.ingredients.controller;

import com.koyeb.AnotherWebProject.ingredients.db.IngredientsDTO;
import com.koyeb.AnotherWebProject.ingredients.db.IngredientsEntity;
import com.koyeb.AnotherWebProject.ingredients.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientsController {
    @Autowired
    private IngredientsService ingredientsService;

    // create
    @PostMapping()
    public ResponseEntity<IngredientsEntity> createIngredient(@RequestBody IngredientsDTO ingredientsDTO) {
        IngredientsEntity createdIngredient = ingredientsService.createIngredient(ingredientsDTO);
        return ResponseEntity.ok(createdIngredient); // HTTP 200
    }

    // read
    @GetMapping("/{id}")
    public ResponseEntity<IngredientsEntity> getIngredientById(@PathVariable Long id) {
        return ingredientsService.getIngredientById(id)
                .map(ResponseEntity::ok) // HTTP 200
                .orElse(ResponseEntity.notFound().build()); // HTTP 404
    }

    @GetMapping()
    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsService.getAllIngredients();
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<IngredientsEntity> updateIngredient(@PathVariable Long id, @RequestBody IngredientsEntity ingredientsDetails) {
        return ResponseEntity.ok(ingredientsService.updateIngredient(id, ingredientsDetails));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientsService.deleteIngredient(id);
        return ResponseEntity.noContent().build(); // HTTP 204 삭제 성공
    }
}
