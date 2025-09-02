package com.j.infra.configuration.filter;

import com.j.application.model.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.nio.file.AccessDeniedException;

import static com.j.infra.support.ManualResponseSupport.doJsonResponse;

/**
 * @author Jinx
 */
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authenticationException) {
            doJsonResponse(response, () -> Result.fail(403, authenticationException.getMessage()));
        } catch (AccessDeniedException accessDeniedException) {
            doJsonResponse(response, () -> Result.fail(401, accessDeniedException.getMessage()));
        } catch (Exception e) {
            doJsonResponse(response, () -> Result.fail(500, e.getMessage()));
        }
    }
}
