package com.quotes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history_vote")
public class HistoryVote {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quotes quote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "voted_at")
    private Instant votedAt;

    private Integer vote;
}

