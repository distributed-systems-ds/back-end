package co.com.icesi.Eshop.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityContext {
    public static String getCurrentUserId(){
        return ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
                .getToken().getClaimAsString("userId");
    }

    public static   String getCurrentUserRole(){
        return ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getToken().getClaimAsString("scope");
    }
}
