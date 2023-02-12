package com.quotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String email;
    private String role;
    private Date expireAt;
    private String token;
}
