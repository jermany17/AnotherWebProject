package com.koyeb.AnotherWebProject.review.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long recipeId;
    private String userName;
    private Integer recipeScore;
    private String review;
}
