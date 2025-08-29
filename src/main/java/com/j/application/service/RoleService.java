package com.j.application.service;

import com.j.application.model.role.RoleCreateCommand;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.RoleRepository;
import com.j.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void create(RoleCreateCommand command) {
        Optional<Role> role = roleRepository.findByName(command.getName());
        if (role.isPresent()) {
            throw new UnprocessableException("Role：{%s} already existed".formatted(command.getName()));
        }

        roleRepository.save(
                new Role(command.getName(), command.getRemark())
        );
    }
}
