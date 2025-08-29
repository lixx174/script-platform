package com.j.infra.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j.application.converter.TokenConverter;
import com.j.application.model.Result;
import com.j.application.model.security.TokenDto;
import com.j.domain.repository.TokenRepository;
import com.j.domain.token.Token;
import com.j.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Jinx
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationPostHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final ObjectMapper om;
    private final TokenGenerate tokenGenerate;
    private final TokenRepository tokenRepository;
    private final TokenConverter tokenConverter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        doResponse(response, () -> Result.fail(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof User user) {
            Optional<Token> token = tokenRepository.findByUserId(user.getId());
            if (token.isEmpty() || token.get().isAccessExpired()) {
                token = Optional.of(tokenGenerate.generate(authentication));
                tokenRepository.save(token.get());
            }

            TokenDto dto = tokenConverter.assemble(token.get());
            doResponse(response, () -> Result.succeed(dto));
        } else {
            throw new RuntimeException("Illegal principal");
        }
    }

    private void doResponse(HttpServletResponse response, Supplier<Result<?>> resultSupplier) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Result<?> result = resultSupplier.get();
        response.setStatus(result == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() : result.getCode());

        PrintWriter writer = response.getWriter();
        writer.write(om.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
