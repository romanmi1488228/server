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

/**
 * service for account rest-controller
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public ResponseEntity<List<AccountModel>> adminGetAllAccount() {
        try {
            return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id
     */
    public ResponseEntity<AccountModel> adminGetAccountById(long id) {
        Optional<AccountModel> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    /**
     * @param request
     */
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

    /**
     * @param id
     * @param request
     */
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
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id
     */
    public ResponseEntity<AccountModel> adminDeleteAccountById(long id) {
        if (accountRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * @param token
     */
    public ResponseEntity<AccountModel> meDeleteAccount(String token) {
        String jwtToken = token.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountRepository.deleteById(accountModel.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param token
     */
    public ResponseEntity<AccountModel> meGetAccount(String token) {
        String jwtToken = token.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountModel.get(), HttpStatus.OK);
    }

    /**
     * @param token
     * @param request
     */
    public ResponseEntity<AccountModel> meChangeAccount(String token, PostAccountMeRequest request) {
        String jwtToken = token.substring(7);
        String login = jwtService.extractUserLogin(jwtToken);
        Optional<AccountModel> accountModel = accountRepository.findByLogin(login);
        if (accountModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
        if (!accountModel.get().getLogin().equals(request.getLogin()) &&
                accountRepository.findByLogin(request.getLogin()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        var account = AccountModel.builder()
                .id(accountModel.get().getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(accountModel.get().getRole())
                .build();
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
