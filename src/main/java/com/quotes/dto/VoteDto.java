package com.quotes.dto;

import com.quotes.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class VoteDto {
    private UUID quoteId;
    private String email;
    private Grade grade;
}
