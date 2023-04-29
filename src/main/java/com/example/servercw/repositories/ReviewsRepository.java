package com.example.servercw.repositories;

import com.example.servercw.models.reviewmodel.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<ReviewModel, Long> {
}
