package com.example.AnotherWebProject.review.controller;

import com.example.AnotherWebProject.review.dto.ReviewDTO;
import com.example.AnotherWebProject.review.domain.ReviewEntity;
import com.example.AnotherWebProject.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 리뷰 생성
    @PostMapping()
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO, Authentication authentication) {
        try {
            ReviewEntity createdReview = reviewService.createReview(reviewDTO, authentication);
            return ResponseEntity.ok(createdReview); // HTTP 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // 전체 조회
    @GetMapping()
    public ResponseEntity<?> getAllReviews() {
        try {
            return ResponseEntity.ok(reviewService.getAllReviews());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        try {
            return reviewService.getReviewById(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(404).body(Map.of("message", "해당 리뷰를 찾을 수 없습니다.")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id,
                                          @RequestBody ReviewDTO reviewDTO,
                                          Authentication authentication) {
        try {
            ReviewEntity updatedReview = reviewService.updateReview(id, reviewDTO, authentication);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message", e.getMessage()));
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id,
                                          Authentication authentication) {
        try {
            reviewService.deleteReview(id, authentication);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message", e.getMessage()));
        }
    }
}
