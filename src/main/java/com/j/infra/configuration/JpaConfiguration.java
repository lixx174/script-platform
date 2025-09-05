package com.j.infra.configuration;

import com.j.application.UserContext;
import com.j.domain.entity.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * @author jinx
 */
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            User user = UserContext.getUser();
            return Optional.of(user == null ? "anonymous" : user.getId() + "-" + user.getName());
        };
    }
}
