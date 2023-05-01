package com.example.servercw.repositories;

import com.example.servercw.models.recipemodels.RecipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RecipeRepository extends JpaRepository<RecipeModel, Long> {
}
