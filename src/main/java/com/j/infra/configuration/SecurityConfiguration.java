package com.j.infra.configuration;

import com.j.application.converter.TokenConverter;
import com.j.domain.repository.TokenRepository;
import com.j.infra.configuration.filter.ExceptionHandlerFilter;
import com.j.infra.configuration.filter.TokenFilter;
import com.j.infra.configuration.filter.TokenRefreshFilter;
import com.j.infra.configuration.security.AuthenticationPostHandler;
import com.j.infra.configuration.security.JdbcUserDetailService;
import com.j.infra.configuration.security.TokenGenerate;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.util.AntPathMatcher;

/**
 * @author jinx
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AntPathMatcher matcher = new AntPathMatcher();

    private final TokenGenerate tokenGenerate;
    private final TokenRepository tokenRepository;
    private final TokenConverter tokenConverter;
    private final JdbcUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        var authenticationPostHandler = new AuthenticationPostHandler(tokenGenerate, tokenRepository, tokenConverter);
        Filter exceptionHandlerFilter = new ExceptionHandlerFilter();
        Filter tokenRefreshFilter = new TokenRefreshFilter(tokenGenerate, tokenRepository, tokenConverter, matcher);
        Filter tokenFilter = new TokenFilter(matcher, tokenRepository);


        return http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .formLogin(configurer -> configurer
                        .successHandler(authenticationPostHandler)
                        .failureHandler(authenticationPostHandler)
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .userDetailsService(userDetailService)
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenRefreshFilter, TokenFilter.class)
                .addFilterBefore(exceptionHandlerFilter, SecurityContextHolderFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/docs/**",
                "/ping",
                "/error"
        );
    }
}
