package com.koyeb.AnotherWebProject.review.repository;

import com.koyeb.AnotherWebProject.review.db.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
