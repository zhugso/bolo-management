package com.zhugs.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NullMarked
@AllArgsConstructor
@Getter
public class UserDetailsDto implements UserDetails, CredentialsContainer {
    private long id;
    private String username;
    private String password;
//    private long deptId;
//    private long postId;
//    private List<String> roles;
//    private String status;

    @Override
    public void eraseCredentials() {
        this.password = "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
