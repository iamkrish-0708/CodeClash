package com.codeclash.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "match_players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rating_before", nullable = false)
    private Integer ratingBefore;

    @Column(name = "rating_after")
    private Integer ratingAfter;

    @Column(name = "rating_change")
    private Integer ratingChange;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private PlayerResult result = PlayerResult.PENDING;

    @Builder.Default
    @Column(nullable = false)
    private Integer score = 0; // Test cases passed

    @Column(name = "time_taken_seconds")
    private Integer timeTakenSeconds;

    public enum PlayerResult {
        WIN,
        LOSS,
        DRAW,
        PENDING
    }
}
