package com.j.infra.configuration;

import com.j.infra.configuration.filter.TokenFilter;
import com.j.infra.configuration.security.AuthenticationPostHandler;
import com.j.infra.configuration.security.JdbcUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

/**
 * @author jinx
 */
@Import({
        AntPathMatcher.class
})
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          TokenFilter tokenFilter,
                                                          JdbcUserDetailService userDetailService,
                                                          AuthenticationPostHandler authenticationPostHandler) throws Exception {
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
