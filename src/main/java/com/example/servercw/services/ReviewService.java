package com.example.servercw.services;

import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.models.recipemodels.RecipeModel;
import com.example.servercw.models.reviewmodel.ReviewModel;
import com.example.servercw.repositories.AccountRepository;
import com.example.servercw.repositories.RecipeRepository;
import com.example.servercw.repositories.ReviewsRepository;
import com.example.servercw.requests.PostReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service for review rest-controller
 */
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final RecipeRepository recipeRepository;
    private final ReviewsRepository reviewsRepository;
    public ResponseEntity<List<ReviewModel>> userGetAllReviews() {
        try {
            return new ResponseEntity<>(reviewsRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id
     */
    public ResponseEntity<ReviewModel> userGetReviewById(long id) {
        Optional<ReviewModel> review = reviewsRepository.findById(id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    /**
     * @param recipeId
     */
    public ResponseEntity<List<ReviewModel>> userGetAllReviewsByRecipe(long recipeId) {
        Optional<RecipeModel> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(reviewsRepository.findAllByRecipeModel(recipe.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id
     */
    public ResponseEntity<ReviewModel> moderatorDeleteReviewById(long id) {
        Optional<ReviewModel> review = reviewsRepository.findById(id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        reviewsRepository.delete(review.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param authHeader
     */
    public ResponseEntity<List<ReviewModel>> meGetAllReviews(String authHeader) {
        String jwtToken = authHeader.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            return new ResponseEntity<>(reviewsRepository.findAllByAccountModel(accountModel.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param authHeader
     * @param request
     */
    public ResponseEntity<ReviewModel> mePostReviewOnRecipe(String authHeader, PostReviewRequest request) {
        String jwtToken = authHeader.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (recipeRepository.findById(request.getRecipeId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var review = ReviewModel.builder()
                .rating(request.getRating())
                .accountModel(accountModel.get())
                .recipeModel(recipeRepository.findById(request.getRecipeId()).get())
                .text(request.getText())
                .build();
        reviewsRepository.save(review);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * @param authHeader
     * @param reviewId
     * @param request
     */
    public ResponseEntity<ReviewModel> meChangeReviewById(String authHeader, long reviewId, PostReviewRequest request) {
        String jwtToken = authHeader.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<ReviewModel> review = reviewsRepository.findById(reviewId);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!review.get().getAccountModel().equals(accountModel.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (recipeRepository.findById(request.getRecipeId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var newReview = ReviewModel.builder()
                .accountModel(accountModel.get())
                .id(review.get().getId())
                .recipeModel(recipeRepository.findById(request.getRecipeId()).get())
                .text(request.getText())
                .rating(request.getRating())
                .build();
        reviewsRepository.save(newReview);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param authHeader
     * @param reviewId
     */
    public ResponseEntity<ReviewModel> meDeleteReviewById(String authHeader, long reviewId) {
        String jwtToken = authHeader.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        Optional<ReviewModel> reviewModel = reviewsRepository.findById(reviewId);
        if (accountModel.isEmpty() ||
            reviewModel.isEmpty() ||
            !reviewModel.get().getAccountModel().equals(accountModel.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        reviewsRepository.delete(reviewModel.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
