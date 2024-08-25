package com.koyeb.AnotherWebProject.recipe.db;

import com.koyeb.AnotherWebProject.ingredients.db.IngredientsDTO;
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
    private String userName;
    private String foodName;
    private String amount;
    private String cookingTime;
    private String difficulty;
    private String howToCook;
    private String imagePath;
    private List<IngredientsDTO> ingredients;
}
