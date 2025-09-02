package com.j.infra.configuration;

import com.j.domain.entity.user.User;
import com.j.infra.support.SecuritySupport;
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
            User user = SecuritySupport.getUser();
            return Optional.of(user.getId() + "-" + user.getName());
        };
    }
}
