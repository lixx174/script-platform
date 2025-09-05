package com.j.domain.service;

import com.j.domain.entity.user.Account;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class AccountEntityService {

    private final AccountRepository accountRepository;

    public Account create(String username, String password) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            throw new UnprocessableException("Account：{%s} already existed".formatted(username));
        }

        return new Account(username, password);
    }
}
