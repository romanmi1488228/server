package com.example.servercw.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request for posting review
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReviewRequest {
    @Min(0)
    @Max(10)
    private int rating;
    private String text;
    private long recipeId;

}
