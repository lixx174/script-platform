package com.j.application.service;

import com.j.application.converter.RoleConverter;
import com.j.application.model.PageReply;
import com.j.application.model.role.RoleCreateCommand;
import com.j.application.model.role.RoleDto;
import com.j.application.model.role.RolePageQuery;
import com.j.domain.entity.user.Role;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public PageReply<RoleDto> page(RolePageQuery query) {
        Page<Role> page = roleRepository.findAll(query.getExample(), query.getPage());

        return PageReply.of(page, roleConverter::assemble);
    }

    public void create(RoleCreateCommand command) {
        Optional<Role> roleOptional = roleRepository.findByName(command.getName());
        if (roleOptional.isPresent()) {
            throw new UnprocessableException("Role：{%s} already existed".formatted(command.getName()));
        }

        Role role = new Role(command.getName(), command.getRemark());

        roleRepository.save(role);
    }
}
