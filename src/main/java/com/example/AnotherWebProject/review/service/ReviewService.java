package com.example.AnotherWebProject.review.service;

import com.example.AnotherWebProject.auth.domain.User;
import com.example.AnotherWebProject.recipe.domain.RecipeEntity;
import com.example.AnotherWebProject.recipe.repository.RecipeRepository;
import com.example.AnotherWebProject.review.dto.ReviewDTO;
import com.example.AnotherWebProject.review.domain.ReviewEntity;
import com.example.AnotherWebProject.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public ReviewEntity createReview(ReviewDTO reviewDTO, Authentication authentication) {
        // 레시피를 찾기, recipeid 존재 확인
        RecipeEntity recipe = recipeRepository.findById(reviewDTO.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + reviewDTO.getRecipeId()));

        User user = (User) authentication.getPrincipal();

        // 찾은 레시피를 사용하여 새로운 ReviewEntity를 생성
        ReviewEntity review = ReviewEntity.builder()
                .recipe(recipe)
                .userId(user.getUserId())
                .userName(user.getUserName())
                .recipeScore(Integer.parseInt(reviewDTO.getRecipeScore()))
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
    public ReviewEntity updateReview(Long id, ReviewDTO reviewDTO, Authentication authentication) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        User user = (User) authentication.getPrincipal();

        if (!review.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("리뷰 수정 권한이 없습니다.");
        }

        review.setRecipeScore(Integer.parseInt(reviewDTO.getRecipeScore()));
        review.setReview(reviewDTO.getReview());

        return reviewRepository.save(review);
    }

    // delete
    public void deleteReview(Long id, Authentication authentication) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        User user = (User) authentication.getPrincipal();

        if (!review.getUserId().equals(user.getUserId())) { // userId로 권한 확인
            throw new RuntimeException("리뷰 삭제 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }
}
