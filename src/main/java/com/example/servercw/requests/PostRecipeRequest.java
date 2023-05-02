package com.example.servercw.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRecipeRequest {
    private String ingredients;
    private String steps;
    private byte[] picture;
    private long user_id;
}
