package com.quotes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quotes.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Instant createAt;

    public UserDto(Users user) {
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        createAt = user.getCreateAt();
    }
}
