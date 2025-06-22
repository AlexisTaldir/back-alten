package me.taldir.alten.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("adminCheck")
public class AdminCheck {
    public boolean check(Authentication authentication) {
        return authentication != null &&
               authentication.getName().equals("admin@admin.com");
    }
}
