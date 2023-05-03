package com.example.servercw.controllers;

import com.example.servercw.models.accountmodel.AccountModel;
import com.example.servercw.requests.PostAccountAdminRequest;
import com.example.servercw.requests.PostAccountMeRequest;
import com.example.servercw.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * rest controller for account table
 */
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //admin part
    @GetMapping(value = "admin/accounts/get")
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        return accountService.adminGetAllAccount();
    }

    /**
     * @param id
     */
    @GetMapping(value = "admin/accounts/get/{id}")
    public ResponseEntity<AccountModel> getAccountById(@PathVariable("id") long id) {
        return accountService.adminGetAccountById(id);
    }

    /**
     * @param request
     */
    @PostMapping(value = "admin/accounts/post")
    public ResponseEntity<AccountModel> postAccount(@RequestBody PostAccountAdminRequest request) {
        return accountService.adminPostAccount(request);
    }

    /**
     * @param id
     * @param request
     */
    @PutMapping(value = "admin/accounts/put/{id}")
    public ResponseEntity<AccountModel> changeAccountById(
            @PathVariable("id") long id,
            @RequestBody PostAccountAdminRequest request) {
        return accountService.adminChangeAccount(id, request);
    }

    /**
     * @param id
     */
    @DeleteMapping(value = "admin/accounts/delete/{id}")
    public ResponseEntity<AccountModel> deleteAccountById(@PathVariable("id") long id) {
        return accountService.adminDeleteAccountById(id);
    }


    /**
     * @param authHeader
     */
    //me part
    @DeleteMapping(value = "me/account/delete")
    public ResponseEntity<AccountModel> deleteMyAccount(@RequestHeader(value = "Authorization") String authHeader) {
        return accountService.meDeleteAccount(authHeader);
    }

    /**
     * @param authHeader
     */
    @GetMapping(value = "me/account/get")
    public ResponseEntity<AccountModel> getMyAccount(@RequestHeader(value = "Authorization") String authHeader) {
        return accountService.meGetAccount(authHeader);
    }

    /**
     * @param authHeader
     * @param request
     */
    @PutMapping(value = "me/account/put")
    public ResponseEntity<AccountModel> changeMyAccount(
            @RequestHeader(value = "Authorization") String authHeader,
            @RequestBody PostAccountMeRequest request) {
        return accountService.meChangeAccount(authHeader, request);
    }
}
