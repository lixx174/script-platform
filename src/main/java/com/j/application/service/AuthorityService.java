package com.j.application.service;

import com.j.application.converter.AuthorityConverter;
import com.j.application.model.PageReply;
import com.j.application.model.authority.AuthorityCreateCommand;
import com.j.application.model.authority.AuthorityDto;
import com.j.application.model.authority.AuthorityPageQuery;
import com.j.domain.entity.user.Authority;
import com.j.domain.exception.UnprocessableException;
import com.j.domain.factory.EntityFactory;
import com.j.domain.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityConverter authorityConverter;
    private final EntityFactory<Authority> authorityEntityFactory;

    public PageReply<AuthorityDto> page(AuthorityPageQuery query) {
        Page<Authority> page = authorityRepository.findAll(query.getSpecification(), query.getPage());

        return PageReply.of(page, authorityConverter::convert);
    }

    public void create(AuthorityCreateCommand command) {
        Optional<Authority> authorityOptional = authorityRepository.findByPermission(command.getPermission());
        if (authorityOptional.isPresent()) {
            throw new UnprocessableException("Authority：{%s} already existed".formatted(command.getPermission()));
        }

        Authority authority = authorityEntityFactory.create(command);

        authorityRepository.save(authority);
    }
}
