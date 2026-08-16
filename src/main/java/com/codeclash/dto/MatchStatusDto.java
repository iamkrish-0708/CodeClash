package com.codeclash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchStatusDto {
    private Long matchId;
    private Long roomId;
    private Long problemId;
    private String problemTitle;
    private String problemDifficulty;
    private Integer timeLimitSeconds;
    private String status; // ACTIVE, FINISHED, DRAW
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private Long remainingSeconds;
    private Long winnerUserId;
    private String winnerUsername;
    private List<PlayerMatchSummary> players;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayerMatchSummary {
        private Long userId;
        private String username;
        private Integer ratingBefore;
        private Integer ratingAfter;
        private Integer ratingChange;
        private String result; // WIN, LOSS, DRAW, PENDING
        private Integer passedTestCases;
        private Integer totalTestCases;
        private boolean hasSubmitted;
        private Integer timeTakenSeconds;
    }
}
