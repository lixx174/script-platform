package com.j.application.service;

import com.j.application.model.user.UserCreateCommand;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.AccountRepository;
import com.j.domain.repository.RoleRepository;
import com.j.domain.repository.UserRepository;
import com.j.domain.service.RoleEntityService;
import com.j.domain.user.Account;
import com.j.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final RoleEntityService roleEntityService;

    public void create(UserCreateCommand command) {
        if (accountRepository.findByUsername(command.getUsername()).isPresent()) {
            throw new UnprocessableException("Account：{%s} already existed".formatted(command.getUsername()));
        }
        if (userRepository.findByName(command.getName()).isPresent()) {
            throw new UnprocessableException("User：{%s} already existed".formatted(command.getName()));
        }

        User user = new User(
                command.getName(),
                new Account(command.getUsername(), command.getPassword()),
                roleEntityService.retrieveRoles(command.getRoleIds())
        );

        userRepository.save(user);
    }
}
