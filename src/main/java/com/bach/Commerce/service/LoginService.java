package com.bach.Commerce.service;

import com.bach.Commerce.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class LoginService implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private User user;

    public LoginService(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }

    public int getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    @Override
    public String getPassword() {

        if (user.isOTPRequired()) {
            return user.getOneTimePassword();
        }

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    public User getUser() {
        // TODO Auto-generated method stub
        return user;
    }

}
