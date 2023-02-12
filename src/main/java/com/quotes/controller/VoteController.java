package com.quotes.controller;

import com.quotes.dto.VoteDto;
import com.quotes.service.AuthorizationService;
import com.quotes.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {
    private final VoteService voteService;
    private final AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity vote(@RequestBody VoteDto voteDto,
                               @RequestHeader("Authorization") String header) {
        log.info("Post /vote");
        String token = authorizationService.extractTokenFromHeader(header);
        String email = authorizationService.extractEmail(token);
        voteDto.setEmail(email);
        voteService.vote(voteDto);
        return ResponseEntity.ok().build();
    }
}
