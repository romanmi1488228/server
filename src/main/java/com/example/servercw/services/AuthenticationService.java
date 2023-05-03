package com.example.servercw.services;

import com.example.servercw.exceptions.UserExistException;
import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.models.accountmodel.Role;
import com.example.servercw.repositories.AccountRepository;
import com.example.servercw.requests.AuthenticationRequest;
import com.example.servercw.requests.RegisterRequest;
import com.example.servercw.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * service for authentication rest-controller
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * @param request
     */
    public AuthenticationResponse register(RegisterRequest request) {
        if (accountRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new UserExistException();
        }
        var account = AccountModel.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .role(Role.USER)
                .build();
        accountRepository.save(account);
        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * @param request
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var account = accountRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
