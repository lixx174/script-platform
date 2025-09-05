package com.j.infra.configuration.security;

import com.j.application.converter.TokenConverter;
import com.j.application.model.Result;
import com.j.application.model.security.TokenDto;
import com.j.domain.entity.token.Token;
import com.j.domain.entity.user.User;
import com.j.domain.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.j.infra.support.ManualResponseSupport.doJsonResponse;

/**
 * @author Jinx
 */
@RequiredArgsConstructor
public class AuthenticationPostHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final TokenGenerate tokenGenerate;
    private final TokenRepository tokenRepository;
    private final TokenConverter tokenConverter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) {
        throw exception;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        if (authentication.getPrincipal() instanceof User user) {
            Token token = tokenRepository.findByUserId(user.getId())
                    .map(t -> {
                        if (t.isAccessExpired()) {
                            Token newToken = tokenGenerate.generate(authentication);
                            t.replace(newToken);
                        }
                        return t;
                    })
                    .orElseGet(() -> tokenGenerate.generate(authentication));

            tokenRepository.save(token);

            TokenDto dto = tokenConverter.convert(token);
            doJsonResponse(response, () -> Result.succeed(dto));
        } else {
            throw new AuthenticationServiceException("Illegal principal");
        }
    }
}
