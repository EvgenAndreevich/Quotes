package com.quotes.service;

import com.quotes.repository.HistoryVoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final HistoryVoteRepository historyVoteRepository;

    public List<HistoryVoteRepository.Statistics> getStatisticsByQuoteId(UUID id) {
        List<HistoryVoteRepository.Statistics> list = historyVoteRepository.getStatisticsByQuoteId(id);
        log.info("получили статистику цитаты с id: " + id);
        return list;
    }
}
