package com.example.servercw.services;

import com.example.servercw.exceptions.UserExistException;
import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.models.accountmodel.Role;
import com.example.servercw.repositories.AccountRepository;
import com.example.servercw.requests.PostAccountAdminRequest;
import com.example.servercw.requests.PostAccountMeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<List<AccountModel>> adminGetAllAccount() {
        try {
            return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<AccountModel> adminGetAccountById(long id) {
        Optional<AccountModel> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> adminPostAccount(PostAccountAdminRequest request) {
        if (accountRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new UserExistException();
        }
        var account = AccountModel.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole().equals("ADMIN") ? Role.ADMIN: request.getRole().equals("MODERATOR") ? Role.MODERATOR:Role.USER)
                .build();
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> adminChangeAccount(long id, PostAccountAdminRequest request) {
        if (accountRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!accountRepository.findById(id).get().getLogin().equals(request.getLogin()) &&
        accountRepository.findByLogin(request.getLogin()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        var account = AccountModel.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().equals("ADMIN") ? Role.ADMIN: request.getRole().equals("MODERATOR") ? Role.MODERATOR:Role.USER)
                .build();
        accountRepository.findById(id);
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> adminDeleteAccountById(long id) {
        if (accountRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AccountModel> meDeleteAccount(String authHeader) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> meGetAccount(String authHeader) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> meChangeAccount(String authHeader, PostAccountMeRequest request) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
