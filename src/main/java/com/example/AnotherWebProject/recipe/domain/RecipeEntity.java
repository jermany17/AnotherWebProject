package com.example.AnotherWebProject.recipe.domain;

import com.example.AnotherWebProject.review.domain.ReviewEntity;
import com.example.AnotherWebProject.ingredients.domain.IngredientsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "recipe")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "cooking_time", nullable = false)
    private Integer cookingTime;

    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @Column(name= "how_to_cook", columnDefinition = "TEXT", nullable = false)
    private String howToCook;

    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Convert(converter = StringListConverter.class)
    @Column(name = "images", columnDefinition = "TEXT")
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonIgnore, 자식 안 뜨게하려면 지정
    private List<IngredientsEntity> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonIgnore, 자식 안 뜨게하려면 지정
    private List<ReviewEntity> reviews;
}
