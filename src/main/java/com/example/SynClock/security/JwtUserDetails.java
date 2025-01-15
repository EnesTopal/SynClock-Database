package com.example.SynClock.security;

import com.example.SynClock.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
public class JwtUserDetails implements UserDetails {
    public long uuid;
    public String userName;
    public String userPassword;
    private Collection<? extends GrantedAuthority>authorities;

    public JwtUserDetails(Long uuid,
                          String userName,
                          String userPassword,
                          Collection<? extends GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.userName = userName;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("user"));
        return new JwtUserDetails(
                user.getUuid(),
                user.getUsername(),
                user.getUserpassword(),
                authorityList
        );
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
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
