package com.quotes.controller;

import com.quotes.dto.QuoteDto;
import com.quotes.service.AuthorizationService;
import com.quotes.service.QuotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/quotes")
public class QuotesController {
    private final QuotesService quotesService;
    private final AuthorizationService authorizationService;

    @PostMapping("add-quote")
    public ResponseEntity<QuoteDto> addQuote(@RequestBody QuoteDto quote,
                                             @RequestHeader("Authorization") String header) {
        log.info("Post /quotes/add-quote");
        String token = authorizationService.extractTokenFromHeader(header);
        String email = authorizationService.extractEmail(token);
        quote.setEmail(email);
        return ResponseEntity.ok(new QuoteDto(quotesService.addNewQuote(quote)));
    }

    @GetMapping("/get-all-quotes")
    public ResponseEntity<List<QuoteDto>> getAllQuotes(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("Get /quotes/get-all-quotes");
        return ResponseEntity.ok(quotesService.getAllQuotes(page, size).stream()
                .map(QuoteDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/get-quote-by-id")
    public ResponseEntity<QuoteDto> getQuoteById(@RequestParam UUID id) {
        log.info("Get /quotes/get-quote-by-id");
        return ResponseEntity.ok(new QuoteDto(quotesService.getQuoteById(id)));
    }

    @GetMapping("/delete-quote-by-id")
    public ResponseEntity deleteQuoteById(@RequestParam UUID id,
                                          @RequestHeader("Authorization") String header) {
        log.info("Get /quotes/delete-quote-by-id");
        String token = authorizationService.extractTokenFromHeader(header);
        String email = authorizationService.extractEmail(token);
        quotesService.deleteQuoteById(id, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/flop-ten")
    public ResponseEntity<List<QuoteDto>> getFlopQuotes() {
        log.info("Get /quotes/flop-ten");
        return ResponseEntity.ok(quotesService.getFlopQuotes().stream()
                .map(QuoteDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/top-ten")
    public ResponseEntity<List<QuoteDto>> getTopQuotes() {
        log.info("Get /quotes/top-ten");
        return ResponseEntity.ok(quotesService.getTopQuotes().stream()
                .map(QuoteDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/last")
    public ResponseEntity<QuoteDto> lastQuote() {
        log.info("Get /quotes/last");
        return ResponseEntity.ok(new QuoteDto(quotesService.lastQuote()));
    }

    @PutMapping("/quote-change-by-id")
    public ResponseEntity<QuoteDto> quoteChangeById(@RequestBody QuoteDto quote,
                                                    @RequestHeader("Authorization") String header) {
        log.info("Put /quotes/quote-change-by-id");
        String token = authorizationService.extractTokenFromHeader(header);
        String email = authorizationService.extractEmail(token);
        quote.setEmail(email);
        return ResponseEntity.ok(new QuoteDto(quotesService.changeQuote(quote)));
    }
}



