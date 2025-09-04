package com.j.application.service;

import com.j.application.model.user.UserCreateCommand;
import com.j.domain.entity.user.Account;
import com.j.domain.entity.user.Role;
import com.j.domain.entity.user.User;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.AccountRepository;
import com.j.domain.repository.RoleRepository;
import com.j.domain.repository.UserRepository;
import com.j.domain.service.RoleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
        // TODO 跨领域
        Optional<Account> accountOptional = accountRepository.findByUsername(command.getUsername());
        if (accountOptional.isPresent()) {
            throw new UnprocessableException("Account：{%s} already existed".formatted(command.getUsername()));
        }
        Optional<User> userOptional = userRepository.findByName(command.getName());
        if (userOptional.isPresent()) {
            throw new UnprocessableException("User：{%s} already existed".formatted(command.getName()));
        }

        Account account = new Account(command.getUsername(), command.getPassword());
        Collection<Role> roles = roleEntityService.retrieveRoles(command.getRoleIds());

        User entity = new User(command.getName(), account, roles);

        userRepository.save(entity);
    }
}
