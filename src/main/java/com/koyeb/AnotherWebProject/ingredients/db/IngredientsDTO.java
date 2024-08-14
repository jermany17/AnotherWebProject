package com.koyeb.AnotherWebProject.ingredients.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientsDTO {
    private Long id; // Ingredient ID for update
    private Long recipeId;
    private String ingredient;
    private String weight;
}
