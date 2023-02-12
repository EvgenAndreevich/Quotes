package com.quotes.exceptions;

import com.quotes.dto.AppExceptionDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AppExceptionDto> handleNotFoundException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(NotFoundException.code)
                .body(new AppExceptionDto(NotFoundException.code, e.getMessage()));
    }

    @ExceptionHandler(NotHasAccessException.class)
    public ResponseEntity<AppExceptionDto> handleNotHasAccessException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(NotHasAccessException.code)
                .body(new AppExceptionDto(NotHasAccessException.code, e.getMessage()));
    }

    @ExceptionHandler(UsersAlreadyExistException.class)
    public ResponseEntity<AppExceptionDto> handleUsersAlreadyExistException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(UsersAlreadyExistException.code)
                .body(new AppExceptionDto(UsersAlreadyExistException.code, e.getMessage()));
    }
}
