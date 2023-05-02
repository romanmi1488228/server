package com.example.servercw.services;

import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.models.recipemodels.RecipeModel;
import com.example.servercw.repositories.AccountRepository;
import com.example.servercw.repositories.RecipeRepository;
import com.example.servercw.requests.PostRecipeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final RecipeRepository recipeRepository;
    public ResponseEntity<List<RecipeModel>> userGetAllRecipes() {
        try {
            return new ResponseEntity<>(recipeRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<RecipeModel> userGetRecipeById(long id) {
        Optional<RecipeModel> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
    }

    public ResponseEntity<RecipeModel> moderatorPostRecipe(PostRecipeRequest request) {
        if (accountRepository.findById(request.getUser_id()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var recipe = RecipeModel.builder()
                .ingredients(request.getIngredients())
                .steps(request.getSteps())
                .recipePicture(request.getPicture())
                .accountModel(accountRepository.findById(request.getUser_id()).get())
                .build();
        recipeRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<RecipeModel> moderatorChangeRecipe(PostRecipeRequest request, long id) {
        if (recipeRepository.findById(id).isEmpty() || accountRepository.findById(request.getUser_id()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var recipe = RecipeModel.builder()
                .id(id)
                .recipePicture(request.getPicture())
                .steps(request.getSteps())
                .ingredients(request.getIngredients())
                .accountModel(accountRepository.findById(request.getUser_id()).get())
                .build();
        recipeRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<RecipeModel> moderatorDeleteRecipeById(long id) {
        if (recipeRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recipeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<RecipeModel> meGetRecipes(String token) {
        String jwtToken = token.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipeRepository.findAllByAccountModel(accountModel.get()).get(), HttpStatus.OK);
    }

    public ResponseEntity<RecipeModel> meDeleteRecipeById(String token, long id) {
        String jwtToken = token.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty() || recipeRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!recipeRepository.findById(id).get().getAccountModel().equals(accountModel.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        recipeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
