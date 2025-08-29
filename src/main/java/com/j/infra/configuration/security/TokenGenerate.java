package com.j.infra.configuration.security;

import com.j.domain.token.Token;
import com.j.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

/**
 * @author Jinx
 */
@Component
public class TokenGenerate {

    private final StringKeyGenerator tokenGenerator =
            new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96);

    public static final String TOKEN_TYPE = "Bearer";

    public Token generate(Authentication authentication) {
        Token token = new Token();
        token.setType(TOKEN_TYPE);
        token.setUser((User) authentication.getPrincipal());
        token.setAccessToken(tokenGenerator.generateKey());
        token.setRefreshToken(tokenGenerator.generateKey());
        token.setAccessExpireAt(Instant.now().plus(Duration.ofDays(1)));
        token.setRefreshExpireAt(Instant.now().plus(Duration.ofDays(7)));

        return token;
    }
}
