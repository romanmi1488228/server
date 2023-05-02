package com.example.servercw.repositories;

import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.models.recipemodels.RecipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeModel, Long> {
    Optional<RecipeModel> findById(Long id);
    Optional<RecipeModel> findAllByAccountModel(AccountModel accountModel);
}
