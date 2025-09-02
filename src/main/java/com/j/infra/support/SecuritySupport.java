package com.j.infra.support;

import com.j.domain.entity.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Jinx
 */
public class SecuritySupport {

    public static Long getUserId() {
        return getUser().getId();
    }

    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
