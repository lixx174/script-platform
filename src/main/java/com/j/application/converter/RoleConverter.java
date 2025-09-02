package com.j.application.converter;

import com.j.application.model.role.RoleDto;
import com.j.domain.entity.user.Role;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author Jinx
 */
@Mapper
public interface RoleConverter {

    RoleDto assemble(Role role);

    Collection<RoleDto> assemble(Collection<Role> roles);
}
