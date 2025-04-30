package com.example.AnotherWebProject.ingredients.controller;

import com.example.AnotherWebProject.ingredients.dto.IngredientsDTO;
import com.example.AnotherWebProject.ingredients.domain.IngredientsEntity;
import com.example.AnotherWebProject.ingredients.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
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
    @GetMapping()
    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsService.getAllIngredients();
    }
    @GetMapping("/{id}")
    public ResponseEntity<IngredientsEntity> getIngredientById(@PathVariable Long id) {
        return ingredientsService.getIngredientById(id)
                .map(ResponseEntity::ok) // HTTP 200
                .orElse(ResponseEntity.notFound().build()); // HTTP 404
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
