package com.example.servercw.controllers;

import com.example.servercw.models.recipemodels.RecipeModel;
import com.example.servercw.requests.PostRecipeRequest;
import com.example.servercw.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    // user part
    @GetMapping(value = "/user/recipes/get")
    public ResponseEntity<List<RecipeModel>> getAllRecipes(){
        return recipeService.userGetAllRecipes();
    }

    @GetMapping(value = "/user/recipes/get/{id}")
    public ResponseEntity<RecipeModel> getRecipeById(@PathVariable("id") long id){
        return recipeService.userGetRecipeById(id);
    }

    // moderator part
    @PostMapping(value = "/moderator/recipes/post")
    public ResponseEntity<RecipeModel> postRecipe(@RequestBody PostRecipeRequest request){
        return recipeService.moderatorPostRecipe(request);
    }

    @PutMapping(value = "/moderator/recipes/put/{id}")
    public ResponseEntity<RecipeModel> changeRecipeById(@PathVariable("id") long id, @RequestBody PostRecipeRequest request){
        return recipeService.moderatorChangeRecipe(request, id);
    }

    @DeleteMapping(value = "/moderator/recipes/delete/{id}")
    public ResponseEntity<RecipeModel> deleteRecipeById(@PathVariable("id") long id){
        return recipeService.moderatorDeleteRecipeById(id);
    }

    // me part
    @GetMapping(value = "/me/recipes/get")
    public ResponseEntity<RecipeModel> getMyRecipes(@RequestHeader(value = "Authorization") String authHeader){
        return recipeService.meGetRecipes(authHeader);
    }

    @DeleteMapping(value = "/me/recipes/delete/{id}")
    public ResponseEntity<RecipeModel> deleteMyRecipeById(@RequestHeader(value = "Authorization") String authHeader, @PathVariable("id") long id){
        return recipeService.meDeleteRecipeById(authHeader, id);
    }


}
