package com.example.servercw.requests;

import com.example.servercw.models.accountmodel.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request which can only be completed by admin for posting accounts
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostAccountAdminRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
