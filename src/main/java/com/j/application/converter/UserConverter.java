package com.j.application.converter;

import com.j.application.model.user.UserProfileDto;
import com.j.domain.entity.user.Authority;
import com.j.domain.entity.user.Role;
import com.j.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jinx
 */
@Mapper(uses = {
        AuthorityConverter.class
})
public interface UserConverter {

    @Mappings({
            @Mapping(target = "authorities", source = "roles")
    })
    UserProfileDto profileConvert(User user);


    default Collection<Authority> roleToAuthority(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
