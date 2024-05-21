package co.com.icesi.Eshop.model.security;

import co.com.icesi.Eshop.model.Role;
import co.com.icesi.Eshop.model.UserPrincipal;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SecurityUser(UserPrincipal userPrincipal) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(userPrincipal).map(UserPrincipal::getRole).map(Role::getRoleName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //return null;
    }

    @Override
    public String getPassword() {
        return userPrincipal.getPassword();
    }

    @Override
    public String getUsername() {
        return userPrincipal.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}
