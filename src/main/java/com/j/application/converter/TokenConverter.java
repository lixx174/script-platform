package com.j.application.converter;

import com.j.application.model.security.TokenDto;
import com.j.domain.entity.token.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Jinx
 */
@Mapper
public interface TokenConverter {

    @Mappings({
            @Mapping(target = "accessExpireIn", source = "accessExpireAt", qualifiedByName = "instantToDuration"),
            @Mapping(target = "refreshExpireIn", source = "refreshExpireAt", qualifiedByName = "instantToDuration"),
    })
    TokenDto convert(Token token);

    @Named("instantToDuration")
    default Duration instantToDuration(LocalDateTime expireAt) {
        return Duration.between(LocalDateTime.now(), expireAt);
    }
}
