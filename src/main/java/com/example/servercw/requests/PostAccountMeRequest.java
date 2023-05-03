package com.example.servercw.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request for user to change info about him
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostAccountMeRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
