package com.quotes.controller;

import com.quotes.repository.HistoryVoteRepository;
import com.quotes.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics-quote-by-id")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<List<HistoryVoteRepository.Statistics>> getStatisticsByQuoteId(@RequestParam UUID id) {
        log.info("Get /statistics-quote-by-id");
        return ResponseEntity.ok(statisticsService.getStatisticsByQuoteId(id));
    }
}
