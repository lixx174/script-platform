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

    RoleDto convert(Role role);

    Collection<RoleDto> convert(Collection<Role> roles);
}
