package com.codeclash.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchStatusDto {
    private Long matchId;
    private Long roomId;
    private Long problemId;
    private String problemTitle;
    private String problemDifficulty;
    private Integer timeLimitSeconds;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private Long remainingSeconds;
    private Long winnerUserId;
    private String winnerUsername;
    private List<PlayerMatchSummary> players = new ArrayList<>();

    public MatchStatusDto() {}

    public MatchStatusDto(Long matchId, Long roomId, Long problemId, String problemTitle, String problemDifficulty, Integer timeLimitSeconds, String status, LocalDateTime startedAt, LocalDateTime endsAt, Long remainingSeconds, Long winnerUserId, String winnerUsername, List<PlayerMatchSummary> players) {
        this.matchId = matchId;
        this.roomId = roomId;
        this.problemId = problemId;
        this.problemTitle = problemTitle;
        this.problemDifficulty = problemDifficulty;
        this.timeLimitSeconds = timeLimitSeconds;
        this.status = status;
        this.startedAt = startedAt;
        this.endsAt = endsAt;
        this.remainingSeconds = remainingSeconds;
        this.winnerUserId = winnerUserId;
        this.winnerUsername = winnerUsername;
        this.players = players != null ? players : new ArrayList<>();
    }

    public static MatchStatusDtoBuilder builder() { return new MatchStatusDtoBuilder(); }

    public static class MatchStatusDtoBuilder {
        private Long matchId;
        private Long roomId;
        private Long problemId;
        private String problemTitle;
        private String problemDifficulty;
        private Integer timeLimitSeconds;
        private String status;
        private LocalDateTime startedAt;
        private LocalDateTime endsAt;
        private Long remainingSeconds;
        private Long winnerUserId;
        private String winnerUsername;
        private List<PlayerMatchSummary> players = new ArrayList<>();

        public MatchStatusDtoBuilder matchId(Long matchId) { this.matchId = matchId; return this; }
        public MatchStatusDtoBuilder roomId(Long roomId) { this.roomId = roomId; return this; }
        public MatchStatusDtoBuilder problemId(Long problemId) { this.problemId = problemId; return this; }
        public MatchStatusDtoBuilder problemTitle(String problemTitle) { this.problemTitle = problemTitle; return this; }
        public MatchStatusDtoBuilder problemDifficulty(String problemDifficulty) { this.problemDifficulty = problemDifficulty; return this; }
        public MatchStatusDtoBuilder timeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; return this; }
        public MatchStatusDtoBuilder status(String status) { this.status = status; return this; }
        public MatchStatusDtoBuilder startedAt(LocalDateTime startedAt) { this.startedAt = startedAt; return this; }
        public MatchStatusDtoBuilder endsAt(LocalDateTime endsAt) { this.endsAt = endsAt; return this; }
        public MatchStatusDtoBuilder remainingSeconds(Long remainingSeconds) { this.remainingSeconds = remainingSeconds; return this; }
        public MatchStatusDtoBuilder winnerUserId(Long winnerUserId) { this.winnerUserId = winnerUserId; return this; }
        public MatchStatusDtoBuilder winnerUsername(String winnerUsername) { this.winnerUsername = winnerUsername; return this; }
        public MatchStatusDtoBuilder players(List<PlayerMatchSummary> players) { this.players = players; return this; }

        public MatchStatusDto build() {
            return new MatchStatusDto(matchId, roomId, problemId, problemTitle, problemDifficulty, timeLimitSeconds, status, startedAt, endsAt, remainingSeconds, winnerUserId, winnerUsername, players);
        }
    }

    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public Long getProblemId() { return problemId; }
    public void setProblemId(Long problemId) { this.problemId = problemId; }
    public String getProblemTitle() { return problemTitle; }
    public void setProblemTitle(String problemTitle) { this.problemTitle = problemTitle; }
    public String getProblemDifficulty() { return problemDifficulty; }
    public void setProblemDifficulty(String problemDifficulty) { this.problemDifficulty = problemDifficulty; }
    public Integer getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalDateTime endsAt) { this.endsAt = endsAt; }
    public Long getRemainingSeconds() { return remainingSeconds; }
    public void setRemainingSeconds(Long remainingSeconds) { this.remainingSeconds = remainingSeconds; }
    public Long getWinnerUserId() { return winnerUserId; }
    public void setWinnerUserId(Long winnerUserId) { this.winnerUserId = winnerUserId; }
    public String getWinnerUsername() { return winnerUsername; }
    public void setWinnerUsername(String winnerUsername) { this.winnerUsername = winnerUsername; }
    public List<PlayerMatchSummary> getPlayers() { return players; }
    public void setPlayers(List<PlayerMatchSummary> players) { this.players = players; }

    public static class PlayerMatchSummary {
        private Long userId;
        private String username;
        private Integer ratingBefore;
        private Integer ratingAfter;
        private Integer ratingChange;
        private String result;
        private Integer passedTestCases;
        private Integer totalTestCases;
        private boolean hasSubmitted;
        private Integer timeTakenSeconds;

        public PlayerMatchSummary() {}

        public PlayerMatchSummary(Long userId, String username, Integer ratingBefore, Integer ratingAfter, Integer ratingChange, String result, Integer passedTestCases, Integer totalTestCases, boolean hasSubmitted, Integer timeTakenSeconds) {
            this.userId = userId;
            this.username = username;
            this.ratingBefore = ratingBefore;
            this.ratingAfter = ratingAfter;
            this.ratingChange = ratingChange;
            this.result = result;
            this.passedTestCases = passedTestCases;
            this.totalTestCases = totalTestCases;
            this.hasSubmitted = hasSubmitted;
            this.timeTakenSeconds = timeTakenSeconds;
        }

        public static PlayerMatchSummaryBuilder builder() { return new PlayerMatchSummaryBuilder(); }

        public static class PlayerMatchSummaryBuilder {
            private Long userId;
            private String username;
            private Integer ratingBefore;
            private Integer ratingAfter;
            private Integer ratingChange;
            private String result;
            private Integer passedTestCases;
            private Integer totalTestCases;
            private boolean hasSubmitted;
            private Integer timeTakenSeconds;

            public PlayerMatchSummaryBuilder userId(Long userId) { this.userId = userId; return this; }
            public PlayerMatchSummaryBuilder username(String username) { this.username = username; return this; }
            public PlayerMatchSummaryBuilder ratingBefore(Integer ratingBefore) { this.ratingBefore = ratingBefore; return this; }
            public PlayerMatchSummaryBuilder ratingAfter(Integer ratingAfter) { this.ratingAfter = ratingAfter; return this; }
            public PlayerMatchSummaryBuilder ratingChange(Integer ratingChange) { this.ratingChange = ratingChange; return this; }
            public PlayerMatchSummaryBuilder result(String result) { this.result = result; return this; }
            public PlayerMatchSummaryBuilder passedTestCases(Integer passedTestCases) { this.passedTestCases = passedTestCases; return this; }
            public PlayerMatchSummaryBuilder totalTestCases(Integer totalTestCases) { this.totalTestCases = totalTestCases; return this; }
            public PlayerMatchSummaryBuilder hasSubmitted(boolean hasSubmitted) { this.hasSubmitted = hasSubmitted; return this; }
            public PlayerMatchSummaryBuilder timeTakenSeconds(Integer timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; return this; }

            public PlayerMatchSummary build() {
                return new PlayerMatchSummary(userId, username, ratingBefore, ratingAfter, ratingChange, result, passedTestCases, totalTestCases, hasSubmitted, timeTakenSeconds);
            }
        }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public Integer getRatingBefore() { return ratingBefore; }
        public void setRatingBefore(Integer ratingBefore) { this.ratingBefore = ratingBefore; }
        public Integer getRatingAfter() { return ratingAfter; }
        public void setRatingAfter(Integer ratingAfter) { this.ratingAfter = ratingAfter; }
        public Integer getRatingChange() { return ratingChange; }
        public void setRatingChange(Integer ratingChange) { this.ratingChange = ratingChange; }
        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }
        public Integer getPassedTestCases() { return passedTestCases; }
        public void setPassedTestCases(Integer passedTestCases) { this.passedTestCases = passedTestCases; }
        public Integer getTotalTestCases() { return totalTestCases; }
        public void setTotalTestCases(Integer totalTestCases) { this.totalTestCases = totalTestCases; }
        public boolean isHasSubmitted() { return hasSubmitted; }
        public void setHasSubmitted(boolean hasSubmitted) { this.hasSubmitted = hasSubmitted; }
        public Integer getTimeTakenSeconds() { return timeTakenSeconds; }
        public void setTimeTakenSeconds(Integer timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; }
    }
}
