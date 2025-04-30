package com.example.AnotherWebProject.recipe.dto;

import com.example.AnotherWebProject.ingredients.dto.IngredientsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {
    private String foodName;
    private String amount;
    private String cookingTime;
    private String difficulty;
    private String howToCook;
    private List<String> images;
    private List<IngredientsDTO> ingredients;
}
