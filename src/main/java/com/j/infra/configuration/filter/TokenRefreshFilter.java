package com.j.infra.configuration.filter;

import com.j.application.converter.TokenConverter;
import com.j.application.model.Result;
import com.j.application.model.security.TokenDto;
import com.j.domain.entity.token.Token;
import com.j.domain.repository.TokenRepository;
import com.j.infra.configuration.security.TokenGenerate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.j.infra.support.ManualResponseSupport.doJsonResponse;

/**
 * @author Jinx
 */
@RequiredArgsConstructor
public class TokenRefreshFilter extends OncePerRequestFilter {
    public static final String REFRESH_TOKEN_PARAMETER = "refreshToken";
    public static final String PATTERN = "/token/refresh";

    private final TokenGenerate tokenGenerate;
    private final TokenRepository tokenRepository;
    private final TokenConverter tokenConverter;
    private final AntPathMatcher matcher;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (matcher.match(PATTERN, request.getRequestURI())) {
            String refreshToken = request.getParameter(REFRESH_TOKEN_PARAMETER);
            if (StringUtils.hasText(refreshToken)) {
                Token token = tokenRepository.findByRefreshToken(refreshToken)
                        .map(t -> {
                            if (t.isRefreshExpired()) {
                                throw new AccountExpiredException("Refresh token has expired");
                            }

                            Token newToken = tokenGenerate.generate(t.getUser());
                            return t.replace(newToken);
                        })
                        .orElseThrow(() -> new InvalidBearerTokenException("Invalid refresh token"));

                tokenRepository.save(token);

                TokenDto dto = tokenConverter.assemble(token);
                doJsonResponse(response, () -> Result.succeed(dto));
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
