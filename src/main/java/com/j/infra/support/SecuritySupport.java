package com.j.infra.support;

import com.j.domain.entity.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Jinx
 */
public class SecuritySupport {

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : (User) authentication.getPrincipal();
    }
}
