package com.j.application.converter;

import com.j.application.model.security.TokenDto;
import com.j.domain.token.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Duration;
import java.time.Instant;

/**
 * @author Jinx
 */
@Mapper
public interface TokenConverter {

    @Mappings({
            @Mapping(target = "accessExpireIn", source = "accessExpireAt", qualifiedByName = "instantToDuration"),
            @Mapping(target = "refreshExpireIn", source = "refreshExpireAt", qualifiedByName = "instantToDuration"),
    })
    TokenDto assemble(Token token);

    @Named("instantToDuration")
    default Duration instantToDuration(Instant expireAt) {
        return Duration.between(Instant.now(), expireAt);
    }
}
