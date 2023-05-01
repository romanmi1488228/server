package com.example.servercw.repositories;

import com.example.servercw.models.reviewmodel.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReviewsRepository extends JpaRepository<ReviewModel, Long> {
}
