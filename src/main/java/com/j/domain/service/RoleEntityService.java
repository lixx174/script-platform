package com.j.domain.service;

import com.j.domain.entity.user.Role;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class RoleEntityService {

    private final RoleRepository roleRepository;

    public Collection<Role> retrieveRoles(@NonNull Collection<Long> roleIds) {
        return roleIds.stream()
                .filter(Objects::nonNull)
                .map(roleId -> {
                    Optional<Role> role = roleRepository.findById(roleId);
                    if (role.isEmpty()) {
                        throw new UnprocessableException("role：{%s} isn't exist".formatted(roleId));
                    }
                    return role.get();
                })
                .toList();
    }
}
