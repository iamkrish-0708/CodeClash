package com.codeclash.dto;

import java.time.LocalDateTime;

public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private Integer rating;
    private Integer matchesPlayed;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Double winRate;
    private LocalDateTime createdAt;

    public UserProfileDto() {}

    public UserProfileDto(Long id, String username, String email, Integer rating, Integer matchesPlayed, Integer wins, Integer losses, Integer draws, Double winRate, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.winRate = winRate;
        this.createdAt = createdAt;
    }

    public static UserProfileDtoBuilder builder() { return new UserProfileDtoBuilder(); }

    public static class UserProfileDtoBuilder {
        private Long id;
        private String username;
        private String email;
        private Integer rating;
        private Integer matchesPlayed;
        private Integer wins;
        private Integer losses;
        private Integer draws;
        private Double winRate;
        private LocalDateTime createdAt;

        public UserProfileDtoBuilder id(Long id) { this.id = id; return this; }
        public UserProfileDtoBuilder username(String username) { this.username = username; return this; }
        public UserProfileDtoBuilder email(String email) { this.email = email; return this; }
        public UserProfileDtoBuilder rating(Integer rating) { this.rating = rating; return this; }
        public UserProfileDtoBuilder matchesPlayed(Integer matchesPlayed) { this.matchesPlayed = matchesPlayed; return this; }
        public UserProfileDtoBuilder wins(Integer wins) { this.wins = wins; return this; }
        public UserProfileDtoBuilder losses(Integer losses) { this.losses = losses; return this; }
        public UserProfileDtoBuilder draws(Integer draws) { this.draws = draws; return this; }
        public UserProfileDtoBuilder winRate(Double winRate) { this.winRate = winRate; return this; }
        public UserProfileDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserProfileDto build() {
            return new UserProfileDto(id, username, email, rating, matchesPlayed, wins, losses, draws, winRate, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public Integer getMatchesPlayed() { return matchesPlayed; }
    public void setMatchesPlayed(Integer matchesPlayed) { this.matchesPlayed = matchesPlayed; }
    public Integer getWins() { return wins; }
    public void setWins(Integer wins) { this.wins = wins; }
    public Integer getLosses() { return losses; }
    public void setLosses(Integer losses) { this.losses = losses; }
    public Integer getDraws() { return draws; }
    public void setDraws(Integer draws) { this.draws = draws; }
    public Double getWinRate() { return winRate; }
    public void setWinRate(Double winRate) { this.winRate = winRate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
