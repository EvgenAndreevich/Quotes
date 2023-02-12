package com.quotes.repository;

import com.quotes.domain.HistoryVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryVoteRepository extends JpaRepository<HistoryVote, UUID> {
    @Query(value = " SELECT h.voted_at AS date, "
            + " (SELECT SUM(vote) FROM history_vote AS hv WHERE hv.quote_id = :id AND hv.voted_at <= h.voted_at) AS grade "
            + " FROM history_vote AS h WHERE h.quote_id = :id ",
            nativeQuery = true)
    List<Statistics> getStatisticsByQuoteId(UUID id);

    public static interface Statistics {
        Instant getDate();
        Integer getGrade();
    }
}
