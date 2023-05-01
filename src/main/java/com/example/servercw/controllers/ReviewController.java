package com.example.servercw.controllers;

import com.example.servercw.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
}
