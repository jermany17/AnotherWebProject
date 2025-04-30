package com.example.AnotherWebProject.review.repository;

import com.example.AnotherWebProject.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
