package com.koyeb.AnotherWebProject.review.controller;

import com.koyeb.AnotherWebProject.review.db.ReviewDTO;
import com.koyeb.AnotherWebProject.review.db.ReviewEntity;
import com.koyeb.AnotherWebProject.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // create
    @PostMapping()
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewEntity createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(createdReview); // HTTP 200
    }

    // read
    @GetMapping()
    public List<ReviewEntity> getAllReviews() {
        return reviewService.getAllReviews();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReviewEntity> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok) // HTTP 200
                .orElse(ResponseEntity.notFound().build()); // HTTP 404
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<ReviewEntity> updateReview(@PathVariable Long id, @RequestBody ReviewEntity reviewDetails) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDetails));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); // HTTP 204 삭제 성공
    }
}
