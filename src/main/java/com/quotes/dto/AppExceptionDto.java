package com.quotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppExceptionDto {
    private Integer code;
    private String message;
}
