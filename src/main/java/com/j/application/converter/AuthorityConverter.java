package com.j.application.converter;

import com.j.application.model.authority.AuthorityDto;
import com.j.domain.entity.user.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;

/**
 * @author Jinx
 */
@Mapper
public interface AuthorityConverter {

    @Mappings({
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "modifyAt", ignore = true),
            @Mapping(target = "modifyBy", ignore = true),
    })
    AuthorityDto assemble(Authority authority);

    Collection<AuthorityDto> assemble(Collection<Authority> authorities);
}
