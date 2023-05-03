package com.example.servercw.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request for posting recipe
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRecipeRequest {
    private String ingredients;
    private String steps;
    private String picture;
    private long user_id;
}
