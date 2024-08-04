package com.koyeb.AnotherWebProject.recipe.db;

import com.koyeb.AnotherWebProject.review.db.ReviewEntity;
import com.koyeb.AnotherWebProject.ingredients.db.IngredientsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    private String userName;

    @Column(length = 50, nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer cookingTime;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String howToCook;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(columnDefinition = "TEXT")
    private String imagePath;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonIgnore, 자식 안 뜨게하려면 지정
    private List<IngredientsEntity> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonIgnore, 자식 안 뜨게하려면 지정
    private List<ReviewEntity> reviews;
}
