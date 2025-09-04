package com.j.domain.service;

import com.j.domain.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class AuthorityEntityService {

    private final AuthorityRepository authorityRepository;

    public boolean isExisted(@Nullable Long id) {
        return false;
    }
}
