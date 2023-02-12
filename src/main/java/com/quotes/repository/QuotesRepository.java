package com.quotes.repository;

import com.quotes.domain.Quotes;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuotesRepository extends PagingAndSortingRepository<Quotes, UUID> {
    List<Quotes> findTop10ByOrderByVotesDesc();

    List<Quotes> findTop10ByOrderByVotesAsc();

    Quotes findTop1ByOrderByCreateAtDesc();
}
