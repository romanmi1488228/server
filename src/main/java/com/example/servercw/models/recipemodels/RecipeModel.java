package com.example.servercw.models.recipemodels;

import com.example.servercw.models.accountmodel.AccountModel;

import com.example.servercw.models.reviewmodel.ReviewModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * recipes table model
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "recipes")
public class RecipeModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="id")
    private long id;

    @Column(nullable = false, name="ingredients")
    private String ingredients;

    @Column(nullable = false, name="steps")
    private String steps;

    @Column(name = "recipePicture", length = 100000000)
    private String recipePicture;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable = false)
    private AccountModel accountModel;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recipeModel")
    private List<ReviewModel> reviewModels;
}
