package com.example.servercw.controllers;

import com.example.servercw.models.recipemodels.RecipeModel;
import com.example.servercw.models.reviewmodel.ReviewModel;
import com.example.servercw.requests.PostReviewRequest;
import com.example.servercw.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * rest controller for review table
 */
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    // user part
    @GetMapping(value = "/user/reviews/get")
    public ResponseEntity<List<ReviewModel>> getAllReviews(){
        return reviewService.userGetAllReviews();
    }

    /**
     * @param id
     */
    @GetMapping(value = "/user/reviews/get/{id}")
    public ResponseEntity<ReviewModel> getReviewById(@PathVariable("id") long id){
        return reviewService.userGetReviewById(id);
    }

    /**
     * @param recipeId
     */
    @GetMapping(value = "/user/reviews/get/recipe/{id}")
    public ResponseEntity<List<ReviewModel>> getReviewsOnRecipe(@PathVariable("id") long recipeId){
        return reviewService.userGetAllReviewsByRecipe(recipeId);
    }

    /**
     * @param id
     */
    // moderator part
    @DeleteMapping(value = "/moderator/reviews/delete/{id}")
    public ResponseEntity<ReviewModel> deleteReviewById(@PathVariable("id") long id){
        return reviewService.moderatorDeleteReviewById(id);
    }

    /**
     * @param authHeader
     */
    // me part
    @GetMapping(value = "/me/reviews/get")
    public ResponseEntity<List<ReviewModel>> getAllMyReviews(@RequestHeader(value = "Authorization") String authHeader){
        return reviewService.meGetAllReviews(authHeader);
    }

    /**
     * @param authHeader
     * @param request
     */
    @PostMapping(value = "/me/reviews/post")
    public ResponseEntity<ReviewModel> postReviewOnRecipe(@RequestHeader(value = "Authorization") String authHeader,
                                                          @Valid @RequestBody PostReviewRequest request){
        return reviewService.mePostReviewOnRecipe(authHeader, request);
    }

    /**
     * @param authHeader
     * @param reviewId
     * @param request
     */
    @PutMapping(value = "/me/reviews/put/{id}")
    public ResponseEntity<ReviewModel> changeMyReview(@RequestHeader(value = "Authorization") String authHeader,
                                                      @PathVariable("id") long reviewId,
                                                      @Valid @RequestBody PostReviewRequest request){
        return reviewService.meChangeReviewById(authHeader, reviewId, request);
    }

    /**
     * @param authHeader
     * @param reviewId
     */
    @DeleteMapping(value = "/me/reviews/delete/{id}")
    public ResponseEntity<ReviewModel> deleteMyReviewById(@RequestHeader(value = "Authorization") String authHeader,
                                                          @PathVariable("id") long reviewId){
        return reviewService.meDeleteReviewById(authHeader, reviewId);
    }
}
