package com.j.infra.configuration;

import com.j.infra.configuration.security.UserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author jinx
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(defaultUserDetailsService())
                .build();
    }

    public UserDetailsService defaultUserDetailsService() {
        return username -> {
            UserDetails userDetails = new UserDetails();

            return userDetails;
        };
    }


    static class PlatformUser {
    }

    // TODO user 领域
    static class JwtTokenFilter extends OncePerRequestFilter {

        private static final String[] PERMIT_URLS = {"/token", "/docs/**"};
        private final AntPathMatcher matcher = new AntPathMatcher();

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        @Nullable HttpServletResponse response,
                                        @NotNull FilterChain filterChain)
                throws ServletException, IOException {
            String uri = request.getRequestURI();

            if (Arrays.stream(PERMIT_URLS).noneMatch(i -> matcher.match(i, uri))) {
                String token = request.getHeader("Authorization");
                if (StringUtils.hasText(token)) {
                    token = token.substring(7);
                    UserDetail detail = JwtUtil.decode(token);

                    if (detail != null) {
                        request.setAttribute("userId", detail.getId());

                        // 添加认证通过信息
                        AbstractAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(detail, "", null);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        filterChain.doFilter(request, response);
                        return;
                    }
                }
                ResponseUtil.response(response, Result.error(HttpStatus.UNAUTHORIZED));
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
