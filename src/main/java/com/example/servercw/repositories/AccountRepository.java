package com.example.servercw.repositories;

import com.example.servercw.models.accountmodel.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<AccountModel, Long> {
}
