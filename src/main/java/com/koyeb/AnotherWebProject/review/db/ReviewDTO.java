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
    private Long recipeid;
    private String username;
    private Integer recipescore;
    private String review;
}
