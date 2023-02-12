package com.quotes.service;

import com.quotes.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AllUserDetailsService implements UserDetailsService {
    private final UsersService usersService;
    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user;
        try {
            user = usersService.getUserByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority("user"))
        );
    }
}
