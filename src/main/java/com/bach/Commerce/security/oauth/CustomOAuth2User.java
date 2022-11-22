package com.bach.Commerce.security.oauth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    final OAuth2User oauth2User;
    @Override
    public Map<String, Object> getAttributes() {
        // TODO Auto-generated method stub
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return oauth2User.getAttribute("name");
    }

    public String getFullName() {

        return oauth2User.getAttribute("name");
    }

    public String getEmail() {

        return oauth2User.getAttribute("email");

    }

    public String getId() {
        return oauth2User.getAttribute("id");
    }

}
