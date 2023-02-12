package com.quotes.service;

import com.quotes.domain.Quotes;
import com.quotes.dto.QuoteDto;
import com.quotes.exceptions.NotFoundException;
import com.quotes.exceptions.NotHasAccessException;
import com.quotes.repository.QuotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class QuotesService {
    private final QuotesRepository quotesRepository;
    private final UsersService usersService;

    @Transactional
    public Quotes addNewQuote(QuoteDto quote) {
        Quotes quotes = quotesRepository.save(new Quotes(UUID.randomUUID(), usersService.getUserByEmail(quote.getEmail()),
                quote.getQuote(), 0, Instant.now(), Instant.now()));
        log.info("добавили новую цитату с id: " + quotes.getId());
        return quotes;
    }

    public Page<Quotes> getAllQuotes(Integer page, Integer size) {
        Pageable sortedByVote = PageRequest.of(page, size, Sort.by("votes"));
        Page<Quotes> quotesPage = quotesRepository.findAll(sortedByVote);
        log.info("получили все цитаты");
        return quotesPage;
    }

    @Transactional
    public void deleteQuoteById(UUID id, String email) {
        Quotes quotes = getQuoteById(id);
        if (email.equals(quotes.getUser().getEmail())) {
            quotesRepository.delete(getQuoteById(id));
            log.info("удалили цитату по id: " + id);
        } else {
            throw new NotHasAccessException("пользователь с email: "
                    + email + " не имеет прав удалять цитату с id: " + id);
        }
    }

    public Quotes getQuoteById(UUID id) {
        Quotes quotes = quotesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("цитата с id: " + id + " не найдена"));
        log.info("получили цитату по id: " + id);
        return quotes;
    }

    public List<Quotes> getFlopQuotes() {
        List<Quotes> quotesList = quotesRepository.findTop10ByOrderByVotesAsc();
        log.info("получили 10 цитат, с самым низким рейтингом");
        return quotesList;
    }

    public List<Quotes> getTopQuotes() {
        List<Quotes> quotesList = quotesRepository.findTop10ByOrderByVotesDesc();
        log.info("получили 10 цитат, с самым высоким рейтингом");
        return quotesList;
    }

    public Quotes lastQuote() {
        Quotes quotes = quotesRepository.findTop1ByOrderByCreateAtDesc();
        log.info("получили последнию цитату");
        return quotes;
    }

    @Transactional
    public Quotes changeQuote(QuoteDto quoteDto) {
        Quotes quotes = getQuoteById(quoteDto.getId());
        if (quotes.getUser().getEmail().equals(quoteDto.getEmail())) {
            quotes.setQuote(quoteDto.getQuote());
            quotes.setUpdateAt(Instant.now());
            quotes.setVotes(0);
        } else {
            throw new NotHasAccessException("пользователь с email: "
                    + quoteDto.getEmail() + " не имеет прав изменять цитату: " + quoteDto.getId());
        }
        log.info("изменили цитату c id: " + quoteDto.getId());
        return quotes;
    }
}
