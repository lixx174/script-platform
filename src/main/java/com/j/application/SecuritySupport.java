package com.j.application;

import com.j.domain.user.User;
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
