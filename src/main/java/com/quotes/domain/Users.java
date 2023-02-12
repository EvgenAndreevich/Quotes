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
@Table(name = "users")
public class Users {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    @Column(name = "create_at")
    private Instant createAt;

    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Quotes> quotes;

    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<HistoryVote> historyVote;

    public Users(UUID id, String name, String email, String password, Instant createAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
    }
}

