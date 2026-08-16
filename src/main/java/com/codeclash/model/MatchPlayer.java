package com.codeclash.model;

import jakarta.persistence.*;

@Entity
@Table(name = "match_players")
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
    @Column(nullable = false, length = 20)
    private PlayerResult result = PlayerResult.PENDING;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(name = "time_taken_seconds")
    private Integer timeTakenSeconds;

    public enum PlayerResult {
        WIN,
        LOSS,
        DRAW,
        PENDING
    }

    public MatchPlayer() {}

    public MatchPlayer(Long id, Match match, User user, Integer ratingBefore, Integer ratingAfter, Integer ratingChange, PlayerResult result, Integer score, Integer timeTakenSeconds) {
        this.id = id;
        this.match = match;
        this.user = user;
        this.ratingBefore = ratingBefore;
        this.ratingAfter = ratingAfter;
        this.ratingChange = ratingChange;
        this.result = result != null ? result : PlayerResult.PENDING;
        this.score = score != null ? score : 0;
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public static MatchPlayerBuilder builder() { return new MatchPlayerBuilder(); }

    public static class MatchPlayerBuilder {
        private Long id;
        private Match match;
        private User user;
        private Integer ratingBefore;
        private Integer ratingAfter;
        private Integer ratingChange;
        private PlayerResult result = PlayerResult.PENDING;
        private Integer score = 0;
        private Integer timeTakenSeconds;

        public MatchPlayerBuilder id(Long id) { this.id = id; return this; }
        public MatchPlayerBuilder match(Match match) { this.match = match; return this; }
        public MatchPlayerBuilder user(User user) { this.user = user; return this; }
        public MatchPlayerBuilder ratingBefore(Integer ratingBefore) { this.ratingBefore = ratingBefore; return this; }
        public MatchPlayerBuilder ratingAfter(Integer ratingAfter) { this.ratingAfter = ratingAfter; return this; }
        public MatchPlayerBuilder ratingChange(Integer ratingChange) { this.ratingChange = ratingChange; return this; }
        public MatchPlayerBuilder result(PlayerResult result) { this.result = result; return this; }
        public MatchPlayerBuilder score(Integer score) { this.score = score; return this; }
        public MatchPlayerBuilder timeTakenSeconds(Integer timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; return this; }

        public MatchPlayer build() {
            return new MatchPlayer(id, match, user, ratingBefore, ratingAfter, ratingChange, result, score, timeTakenSeconds);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Integer getRatingBefore() { return ratingBefore; }
    public void setRatingBefore(Integer ratingBefore) { this.ratingBefore = ratingBefore; }
    public Integer getRatingAfter() { return ratingAfter; }
    public void setRatingAfter(Integer ratingAfter) { this.ratingAfter = ratingAfter; }
    public Integer getRatingChange() { return ratingChange; }
    public void setRatingChange(Integer ratingChange) { this.ratingChange = ratingChange; }
    public PlayerResult getResult() { return result; }
    public void setResult(PlayerResult result) { this.result = result; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public Integer getTimeTakenSeconds() { return timeTakenSeconds; }
    public void setTimeTakenSeconds(Integer timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; }
}
