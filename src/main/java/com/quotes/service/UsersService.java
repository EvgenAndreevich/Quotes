package com.quotes.service;

import com.quotes.domain.Users;
import com.quotes.dto.UserDto;
import com.quotes.exceptions.NotFoundException;
import com.quotes.exceptions.UsersAlreadyExistException;
import com.quotes.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users registrationUser(UserDto user) {
        if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsersAlreadyExistException("пользователь с таким email: " + user.getEmail() + " уже существует");
        }
        Users users = usersRepository.save(new Users(UUID.randomUUID(),
                user.getName(),
                user.getEmail(),
                bCryptPasswordEncoder.encode(user.getPassword()),
                Instant.now()));
        log.info("зарегистрировался новый пользователь с email: " + users.getEmail());
        return users;
    }

    public Users getUserById(UUID id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("пользователь с id: " + id + " не найден"));
        log.info("получили пользователя по id: " + id);
        return users;
    }

    public Users getUserByEmail(String email) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("пользователь с email: " + email + " не найден"));
        log.info("получили пользователя по email: " + email);
        return users;
    }
}

