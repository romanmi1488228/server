package com.example.servercw.models.accountmodel;

import com.example.servercw.models.recipemodels.RecipeModel;
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
@Table(name = "account")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="id")
    private long id;

    @Column(nullable = false, name="login")
    private String login;

    @Column(nullable = false, name="password")
    private String password;

    @Column(nullable = false, name="firstName")
    private String firstName;

    @Column(nullable = false, name="lastName")
    private String lastName;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountModel")
    private List<RecipeModel> recipeModels;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountModel")
    private List<ReviewModel> reviewModels;
}
