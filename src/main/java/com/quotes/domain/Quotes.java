package com.quotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "quotes")
public class Quotes {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String quote;

    private Integer votes;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "update_at")
    private Instant updateAt;

    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quote")
//    @JoinColumn(name = "quote_id")
    private List<HistoryVote> historyVote;

    public Quotes(UUID id, Users user, String quote, Integer votes, Instant createAt, Instant updateAt) {
        this.id = id;
        this.user = user;
        this.quote = quote;
        this.votes = votes;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
