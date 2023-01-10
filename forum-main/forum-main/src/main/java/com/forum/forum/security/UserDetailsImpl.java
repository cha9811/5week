package com.forum.forum.security;



import com.forum.forum.entity.Role;
import com.forum.forum.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;        //유저 데이터 저장용 임시변수
    private final String username;  //유저 id 저장용 임시변수

    public UserDetailsImpl(User user, String username) {    //유저데이터,유저id 받기
        this.user = user;
        this.username = username;
    }

    public User getUser() {
        return user;
    }   //유저 데이터 받기

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();     //유저 권한 받기
        String authority = role.getAuthority();     //유저 권한 받기

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
