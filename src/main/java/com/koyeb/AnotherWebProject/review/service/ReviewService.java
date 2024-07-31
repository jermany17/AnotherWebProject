package com.koyeb.AnotherWebProject.review.service;

import com.koyeb.AnotherWebProject.recipe.db.RecipeEntity;
import com.koyeb.AnotherWebProject.recipe.db.RecipeRepository;
import com.koyeb.AnotherWebProject.review.db.ReviewDTO;
import com.koyeb.AnotherWebProject.review.db.ReviewEntity;
import com.koyeb.AnotherWebProject.review.db.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // create
    public ReviewEntity createReview(ReviewDTO reviewDTO) {
        // 레시피를 찾기, recipeid 존재 확인
        RecipeEntity recipe = recipeRepository.findById(reviewDTO.getRecipeid())
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + reviewDTO.getRecipeid()));

        // 찾은 레시피를 사용하여 새로운 ReviewEntity를 생성
        ReviewEntity review = ReviewEntity.builder()
                .recipe(recipe)
                .username(reviewDTO.getUsername())
                .recipescore(reviewDTO.getRecipescore())
                .review(reviewDTO.getReview())
                .build();

        return reviewRepository.save(review);
    }

    // read
    public Optional<ReviewEntity> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

    // update
    public ReviewEntity updateReview(Long id, ReviewEntity reviewDetails) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setUsername(reviewDetails.getUsername());
        review.setRecipescore(reviewDetails.getRecipescore());
        review.setReview(reviewDetails.getReview());

        return reviewRepository.save(review);
    }

    // delete
    public void deleteReview(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }
}
