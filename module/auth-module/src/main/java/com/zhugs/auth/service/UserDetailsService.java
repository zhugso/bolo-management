package com.zhugs.auth.service;

import com.zhugs.auth.model.dto.UserDetailsDto;
import com.zhugs.auth.repository.SystemClient;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@NullMarked
@AllArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final SystemClient systemClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> map = systemClient.getUserPasswordByUsername(username);
        return new UserDetailsDto(
                Long.parseLong(map.get("id").toString()) ,
                (String) map.get("username"),
                (String) map.get("password"));
    }
}
