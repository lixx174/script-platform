package com.j.infra.factory;

import com.j.application.model.authority.AuthorityCreateCommand;
import com.j.domain.entity.user.Authority;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.factory.EntityFactory;
import com.j.domain.primitive.AuthorityType;
import com.j.domain.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Jinx
 */
@Component
@RequiredArgsConstructor
public class AuthorityFactory implements EntityFactory<Authority> {

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority create(Object source, Consumer<Authority> callback) {
        if (source == null) {
            throw new UnprocessableException("Parameter source can't be null");
        }

        Authority entity = switch (source) {
            case AuthorityCreateCommand command -> createByAuthorityCreateCommand(command);
            case Authority authority -> createByAuthority(authority);
            default -> throw new UnprocessableException("Type {%s} not support".formatted(source.getClass()));
        };

        callback.accept(entity);

        return entity;
    }

    private Authority createByAuthorityCreateCommand(AuthorityCreateCommand command) {
        Authority entity = new Authority();
        entity.setName(command.getName());
        entity.setType(command.getType());
        entity.setPath(command.getType() == AuthorityType.BUTTON ? null : command.getPath());
        entity.setPermission(command.getPermission());
        entity.setSort(command.getSort() == null ? Integer.MAX_VALUE : command.getSort());

        if (command.getPid() != null) {
            Optional<Authority> parentAuthority = authorityRepository.findById(command.getPid());
            if (parentAuthority.isEmpty()) {
                throw new UnprocessableException("Authority：{%s} not found".formatted(command.getPid()));
            }

            entity.setParent(parentAuthority.get());
        }

        return entity;
    }

    private Authority createByAuthority(Authority authority) {
        return authority;
    }
}
