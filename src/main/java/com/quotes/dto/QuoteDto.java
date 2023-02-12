package com.quotes.dto;

import com.quotes.domain.Quotes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class QuoteDto {
    private UUID id;
    private String email;
    private String quote;
    private Integer votes;
    private Instant createAt;
    private Instant updateAt;

    public QuoteDto(Quotes q) {
        id = q.getId();
        email = q.getUser().getEmail();
        quote = q.getQuote();
        votes = q.getVotes();
        createAt = q.getCreateAt();
        updateAt = q.getUpdateAt();
    }
}
