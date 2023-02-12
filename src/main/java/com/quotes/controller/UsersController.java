package com.quotes.controller;

import com.quotes.dto.JwtTokenDto;
import com.quotes.dto.UserDto;
import com.quotes.service.AuthorizationService;
import com.quotes.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;
    private final AuthorizationService authorizationService;

    @PostMapping("registration")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        log.info("Post /user/registration");
        return ResponseEntity.ok(new UserDto(usersService.registrationUser(user)));
    }

    @GetMapping("get-by-email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam String email) {
        log.info("Get /user/get-by-email");
        return ResponseEntity.ok(new UserDto(usersService.getUserByEmail(email)));
    }

    @PostMapping("authorization")
    public ResponseEntity<JwtTokenDto> authorization(@RequestBody UserDto user) {
        log.info("Post /user/authorization");
        return ResponseEntity.ok(authorizationService.authenticate(user));
    }
}
