package com.example.servercw.requests.controllers;

import com.example.servercw.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
}
