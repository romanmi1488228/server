package com.example.servercw.models.recipemodels;

import com.example.servercw.models.accountmodel.AccountModel;

import com.example.servercw.models.reviewmodel.ReviewModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] recipePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable = false)
    private AccountModel accountModel;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recipeModel")
    private List<ReviewModel> reviewModels;
}
