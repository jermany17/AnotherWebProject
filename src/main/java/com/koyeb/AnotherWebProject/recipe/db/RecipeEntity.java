package com.koyeb.AnotherWebProject.recipe.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koyeb.AnotherWebProject.review.db.ReviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "recipe")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String foodname;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer cookingtime;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String howtocook;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonIgnore, 자식 안 뜨게하려면 지정
    private List<ReviewEntity> reviews;
}
