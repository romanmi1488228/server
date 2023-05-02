package com.example.servercw.repositories;

import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.models.recipemodels.RecipeModel;
import com.example.servercw.models.reviewmodel.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<ReviewModel, Long> {
    List<ReviewModel> findAllByAccountModel(AccountModel accountModel);
    List<ReviewModel> findAllByRecipeModel(RecipeModel recipeModel);
    Optional<ReviewModel> findById(Long id);
}
