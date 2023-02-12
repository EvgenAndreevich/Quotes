package com.quotes.service;

import com.quotes.domain.HistoryVote;
import com.quotes.domain.Quotes;
import com.quotes.domain.Users;
import com.quotes.dto.VoteDto;
import com.quotes.repository.HistoryVoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

import static com.quotes.enums.Grade.LIKE;

@Log4j2
@Service
@RequiredArgsConstructor
public class VoteService {
    private final HistoryVoteRepository historyVoteRepository;
    private final QuotesService quotesService;
    private final UsersService usersService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Quotes quotes = quotesService.getQuoteById(voteDto.getQuoteId());
        if (voteDto.getGrade().equals(LIKE)) {
            quotes.setVotes(quotes.getVotes() + 1);
        } else {
            quotes.setVotes(quotes.getVotes() - 1);
        }

        Users user = usersService.getUserByEmail(voteDto.getEmail());
        historyVoteRepository.save(new HistoryVote(
                UUID.randomUUID(),
                quotes,
                user,
                Instant.now(),
                voteDto.getGrade().equals(LIKE) ? 1 : -1)
        );
        log.info("проголосовал пользователь с email: " + user.getEmail() + " оценкой: " + voteDto.getGrade().name());
    }
}

