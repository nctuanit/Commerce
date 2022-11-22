package com.bach.Commerce.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;


@Getter
@Setter
public class UserPrincipal extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String role;
    private String phone;
    private String email;

    public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
                         boolean credentialsNonExpired, boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


}
