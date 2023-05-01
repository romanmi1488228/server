package com.example.servercw.repositories;

import com.example.servercw.models.accountmodel.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccountRepository  extends JpaRepository<AccountModel, Long> {

    Optional<AccountModel> findById(Long id);

    Optional<AccountModel> findByLogin(String login);
}
