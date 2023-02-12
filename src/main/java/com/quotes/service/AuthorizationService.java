package com.quotes.service;

import com.quotes.dto.JwtTokenDto;
import com.quotes.dto.UserDto;
import com.quotes.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class AuthorizationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AllUserDetailsService allUsersDetailService;

    public JwtTokenDto authenticate(UserDto user) {
        log.info("AuthorizationService: authenticat userEmail: " + user.getEmail());
        UserDetails userDetails = allUsersDetailService.loadUserByUsername(user.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        userDetails.getAuthorities())
        );

        return jwtUtil.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities(),
                userDetails.isEnabled(),
                userDetails.isAccountNonLocked()
        );
    }

    public String extractEmail(String token) {
        return jwtUtil.extractUsername(token);
    }

    public String extractTokenFromHeader(String header) {
        return header.substring(7);
    }
}
