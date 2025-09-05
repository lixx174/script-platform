package com.j.application;

import com.j.domain.entity.user.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Jinx
 */
public class UserContext {

    public static User getUserSafely() {
        User user = getUser();
        if (user == null) {
            throw new AccessDeniedException("Access Denied");
        }

        return user;
    }

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : (User) authentication.getPrincipal();
    }
}
