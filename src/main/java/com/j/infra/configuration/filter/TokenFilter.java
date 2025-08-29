package com.j.infra.configuration.filter;

import com.j.domain.repository.TokenRepository;
import com.j.domain.token.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static com.j.infra.configuration.security.TokenGenerate.TOKEN_TYPE;

/**
 * @author Jinx
 */
@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final AntPathMatcher matcher;
    private final TokenRepository tokenRepository;

    private final Collection<String> PERMIT_PATH = Arrays.asList(
            "/login",
            "/test/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (PERMIT_PATH.stream().noneMatch(url -> matcher.match(url, path))) {
            String accessToken = request.getHeader("Authorization");
            if (StringUtils.hasText(accessToken)) {
                accessToken = accessToken.substring(TOKEN_TYPE.length() + 1);
                Optional<Token> token = tokenRepository.findByAccessToken(accessToken);
                if (token.isPresent()) {
                    if (token.get().isAccessExpired()) {
                        throw new RuntimeException("to refresh token");
                    }

                    Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                            token.get(), "", null
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            throw new RuntimeException("403");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
