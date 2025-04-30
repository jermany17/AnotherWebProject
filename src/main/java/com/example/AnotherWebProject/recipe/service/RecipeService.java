package com.example.AnotherWebProject.recipe.service;

import com.example.AnotherWebProject.auth.domain.User;
import com.example.AnotherWebProject.ingredients.dto.IngredientsDTO;
import com.example.AnotherWebProject.ingredients.domain.IngredientsEntity;
import com.example.AnotherWebProject.ingredients.repository.IngredientsRepository;
import com.example.AnotherWebProject.recipe.dto.RecipeDTO;
import com.example.AnotherWebProject.recipe.domain.RecipeEntity;
import com.example.AnotherWebProject.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    // create
    public RecipeEntity createRecipe(RecipeDTO recipeDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        RecipeEntity recipe = RecipeEntity.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .foodName(recipeDTO.getFoodName())
                .amount(Integer.parseInt(recipeDTO.getAmount()))
                .cookingTime(Integer.parseInt(recipeDTO.getCookingTime()))
                .difficulty(Integer.parseInt(recipeDTO.getDifficulty()))
                .howToCook(recipeDTO.getHowToCook())
                .images(recipeDTO.getImages())
                .build();

        RecipeEntity savedRecipe = recipeRepository.save(recipe);

        // RecipeDTO에서 전달된 ingredients 리스트를 IngredientsEntity 객체 리스트로 변환
        List<IngredientsEntity> ingredientsEntities = recipeDTO.getIngredients().stream().map(ingredientDTO -> {
            // 각 ingredientDTO를 IngredientsEntity 객체로 변환
            return IngredientsEntity.builder()
                    .recipe(savedRecipe) // 저장된 RecipeEntity와 연결
                    .ingredient(ingredientDTO.getIngredient())
                    .weight(ingredientDTO.getWeight())
                    .build();
        }).collect(Collectors.toList()); // 변환된 여러 개의 IngredientsEntity 객체들을 리스트로 수집

        // 변환된 IngredientsEntity 리스트를 데이터베이스에 저장
        ingredientsRepository.saveAll(ingredientsEntities);

        // 새로 저장된 레시피 엔티티에 ingredients 설정
        savedRecipe.setIngredients(ingredientsEntities);

        return savedRecipe;
    }

    // read
    public Page<RecipeEntity> getRecipesByPageAndSearch(int page, String search) {
        Pageable pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.DESC, "id"));
        if (search != null && !search.isEmpty()) {
            return recipeRepository.findByFoodNameContainingIgnoreCaseOrderByIdDesc(search, pageable);
        } else {
            return recipeRepository.findAllByOrderByIdDesc(pageable);
        }
    }

    public Optional<RecipeEntity> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Page<RecipeEntity> getRecipesByDifficulty(int page, String direction) {
        Pageable pageable = PageRequest.of(page - 1, 12);
        if (direction.equalsIgnoreCase("asc")) {
            return recipeRepository.findAllByOrderByDifficultyAsc(pageable);
        } else {
            return recipeRepository.findAllByOrderByDifficultyDesc(pageable);
        }
    }

    // update
    public RecipeEntity updateRecipe(Long id, RecipeDTO recipeDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if (!recipe.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("해당 레시피에 대한 수정 권한이 없습니다.");
        }

        recipe.setFoodName(recipeDTO.getFoodName());
        recipe.setAmount(Integer.parseInt(recipeDTO.getAmount()));
        recipe.setCookingTime(Integer.parseInt(recipeDTO.getCookingTime()));
        recipe.setDifficulty(Integer.parseInt(recipeDTO.getDifficulty()));
        recipe.setHowToCook(recipeDTO.getHowToCook());
        recipe.setImages(recipeDTO.getImages());

        RecipeEntity updatedRecipe = recipeRepository.save(recipe);

        // 기존 레시피의 모든 재료 ID 목록
        List<Long> existingIngredientIds = updatedRecipe.getIngredients().stream()
                .map(IngredientsEntity::getId)
                .collect(Collectors.toList());

        // 업데이트 요청에서 오는 재료 중 ID가 있는 재료의 ID 목록
        List<Long> incomingIngredientIds = recipeDTO.getIngredients().stream()
                .filter(ingredientDTO -> ingredientDTO.getId() != null)
                .map(IngredientsDTO::getId)
                .collect(Collectors.toList());

        // 로깅 추가
        System.out.println("Existing Ingredient IDs: " + existingIngredientIds);
        System.out.println("Incoming Ingredient IDs: " + incomingIngredientIds);

        // 기존 재료 목록에서 요청된 재료 목록에 없는 재료들을 삭제
        existingIngredientIds.forEach(ingredientId -> {
            if (!incomingIngredientIds.contains(ingredientId)) {
                System.out.println("Incoming Ingredient IDs: " + ingredientId);
                ingredientsRepository.deleteIngredientById(ingredientId);
                // 실제 삭제 확인을 위한 로깅 추가
                if (!ingredientsRepository.existsById(ingredientId)) {
                    System.out.println("Successfully deleted Ingredient ID: " + ingredientId);
                } else {
                    System.out.println("Failed to delete Ingredient ID: " + ingredientId);
                }
            }
        });

        // 새로운 재료를 추가하거나 기존 재료를 업데이트
        List<IngredientsEntity> updatedIngredients = recipeDTO.getIngredients().stream().map(ingredientDTO -> {
            if (ingredientDTO.getId() == null) {
                // New ingredient, add it
                return IngredientsEntity.builder()
                        .recipe(updatedRecipe)
                        .ingredient(ingredientDTO.getIngredient())
                        .weight(ingredientDTO.getWeight())
                        .build();
            } else {
                // Existing ingredient, update it
                IngredientsEntity ingredientsEntity = ingredientsRepository.findById(ingredientDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found"));

                ingredientsEntity.setIngredient(ingredientDTO.getIngredient());
                ingredientsEntity.setWeight(ingredientDTO.getWeight());
                return ingredientsRepository.save(ingredientsEntity);
            }
        }).collect(Collectors.toList());

        // 변환된 IngredientsEntity 리스트를 데이터베이스에 저장
        ingredientsRepository.saveAll(updatedIngredients);

        // 업데이트된 레시피 엔티티에 새로운 재료 엔티티 설정
        updatedRecipe.setIngredients(updatedIngredients);

        return updatedRecipe;
    }

    // delete
    public void deleteRecipe(Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if (!recipe.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("해당 레시피에 대한 삭제 권한이 없습니다.");
        }

        recipeRepository.delete(recipe);
    }
}
