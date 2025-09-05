package com.j.application.service;

import com.j.application.converter.UserConverter;
import com.j.application.model.user.UserCreateCommand;
import com.j.application.model.user.UserProfileDto;
import com.j.domain.entity.user.Account;
import com.j.domain.entity.user.Role;
import com.j.domain.entity.user.User;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.UserRepository;
import com.j.domain.service.AccountEntityService;
import com.j.domain.service.RoleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static com.j.application.UserContext.getUserSafely;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AccountEntityService accountEntityService;
    private final RoleEntityService roleEntityService;

    public void create(UserCreateCommand command) {
        Optional<User> userOptional = userRepository.findByName(command.getName());
        if (userOptional.isPresent()) {
            throw new UnprocessableException("User：{%s} already existed".formatted(command.getName()));
        }

        Account account = accountEntityService.create(command.getUsername(), command.getPassword());
        Collection<Role> roles = roleEntityService.retrieveRoles(command.getRoleIds());

        User entity = new User(command.getName(), account, roles);

        userRepository.save(entity);
    }


    public UserProfileDto profile() {
        User user = getUserSafely();

        return userConverter.profileConvert(user);
    }
}
