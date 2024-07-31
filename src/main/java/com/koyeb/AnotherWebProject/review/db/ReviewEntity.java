package com.koyeb.AnotherWebProject.review.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koyeb.AnotherWebProject.recipe.db.RecipeEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipeid", nullable = false)
    @JsonIgnore       // 무한 반복 방지
    @ToString.Exclude // 무한 반복 방지
    private RecipeEntity recipe;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(nullable = false)
    private Integer recipescore;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String review;
}
