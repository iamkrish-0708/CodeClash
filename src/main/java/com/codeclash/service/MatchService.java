package com.codeclash.service;

import com.codeclash.dto.MatchStatusDto;
import com.codeclash.model.*;
import com.codeclash.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchRepository matchRepository;
    private final RoomRepository roomRepository;
    private final ProblemRepository problemRepository;
    private final MatchPlayerRepository matchPlayerRepository;
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final EloService eloService;

    private static final Random RANDOM = new Random();

    @Transactional
    public MatchStatusDto startMatch(Long roomId, Long optionalProblemId, Long hostUserId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (!room.getHostUser().getId().equals(hostUserId)) {
            throw new IllegalStateException("Only the room host can start the match");
        }

        if (room.getGuestUser() == null) {
            throw new IllegalStateException("Cannot start match without an opponent. Wait for player 2 to join.");
        }

        Problem problem;
        if (optionalProblemId != null) {
            problem = problemRepository.findById(optionalProblemId)
                    .orElseThrow(() -> new IllegalArgumentException("Problem not found"));
        } else {
            List<Problem> allProblems = problemRepository.findAll();
            if (allProblems.isEmpty()) {
                throw new IllegalStateException("No coding problems available in database");
            }
            problem = allProblems.get(RANDOM.nextInt(allProblems.size()));
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endsAt = now.plusSeconds(problem.getTimeLimitSeconds());

        Match match = Match.builder()
                .room(room)
                .problem(problem)
                .status(Match.MatchStatus.ACTIVE)
                .startedAt(now)
                .endsAt(endsAt)
                .build();

        Match savedMatch = matchRepository.save(match);

        // Create MatchPlayer entries for Host and Guest
        MatchPlayer playerHost = MatchPlayer.builder()
                .match(savedMatch)
                .user(room.getHostUser())
                .ratingBefore(room.getHostUser().getRating())
                .result(MatchPlayer.PlayerResult.PENDING)
                .score(0)
                .build();

        MatchPlayer playerGuest = MatchPlayer.builder()
                .match(savedMatch)
                .user(room.getGuestUser())
                .ratingBefore(room.getGuestUser().getRating())
                .result(MatchPlayer.PlayerResult.PENDING)
                .score(0)
                .build();

        matchPlayerRepository.save(playerHost);
        matchPlayerRepository.save(playerGuest);

        room.setStatus(Room.RoomStatus.IN_PROGRESS);
        roomRepository.save(room);

        return getMatchStatus(savedMatch.getId());
    }

    @Transactional
    public MatchStatusDto getMatchStatus(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found: " + matchId));

        // Auto-finish if time expired and still active
        if (match.getStatus() == Match.MatchStatus.ACTIVE && LocalDateTime.now().isAfter(match.getEndsAt())) {
            finalizeMatch(match);
        }

        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(matchId);
        List<MatchStatusDto.PlayerMatchSummary> summaries = new ArrayList<>();

        for (MatchPlayer mp : players) {
            List<Submission> userSubmissions = submissionRepository.findByMatchIdAndUserIdOrderBySubmittedAtDesc(matchId, mp.getUser().getId());
            boolean hasSub = !userSubmissions.isEmpty();
            int passedTests = mp.getScore() != null ? mp.getScore() : 0;
            int totalTests = match.getProblem().getTestCases().size();

            summaries.add(MatchStatusDto.PlayerMatchSummary.builder()
                    .userId(mp.getUser().getId())
                    .username(mp.getUser().getUsername())
                    .ratingBefore(mp.getRatingBefore())
                    .ratingAfter(mp.getRatingAfter())
                    .ratingChange(mp.getRatingChange())
                    .result(mp.getResult().name())
                    .passedTestCases(passedTests)
                    .totalTestCases(totalTests)
                    .hasSubmitted(hasSub)
                    .timeTakenSeconds(mp.getTimeTakenSeconds())
                    .build());
        }

        long remainingSec = Math.max(0, Duration.between(LocalDateTime.now(), match.getEndsAt()).getSeconds());

        return MatchStatusDto.builder()
                .matchId(match.getId())
                .roomId(match.getRoom().getId())
                .problemId(match.getProblem().getId())
                .problemTitle(match.getProblem().getTitle())
                .problemDifficulty(match.getProblem().getDifficulty().name())
                .timeLimitSeconds(match.getProblem().getTimeLimitSeconds())
                .status(match.getStatus().name())
                .startedAt(match.getStartedAt())
                .endsAt(match.getEndsAt())
                .remainingSeconds(remainingSec)
                .winnerUserId(match.getWinnerUser() != null ? match.getWinnerUser().getId() : null)
                .winnerUsername(match.getWinnerUser() != null ? match.getWinnerUser().getUsername() : null)
                .players(summaries)
                .build();
    }

    @Transactional
    public void recordSubmission(Long matchId, Long userId, int passedTests, int totalTests, boolean isAccepted) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null || match.getStatus() != Match.MatchStatus.ACTIVE) return;

        MatchPlayer mp = matchPlayerRepository.findByMatchIdAndUserId(matchId, userId).orElse(null);
        if (mp == null) return;

        int timeTaken = (int) Duration.between(match.getStartedAt(), LocalDateTime.now()).getSeconds();
        mp.setScore(passedTests);
        mp.setTimeTakenSeconds(timeTaken);
        matchPlayerRepository.save(mp);

        // If player achieved 100% (ACCEPTED), they win immediately!
        if (isAccepted && passedTests == totalTests) {
            finalizeMatchWithWinner(match, mp.getUser());
        } else {
            // Check if both players have submitted
            List<MatchPlayer> allPlayers = matchPlayerRepository.findByMatchId(matchId);
            boolean allSubmitted = allPlayers.stream().allMatch(p -> p.getTimeTakenSeconds() != null);
            if (allSubmitted) {
                finalizeMatch(match);
            }
        }
    }

    @Transactional
    public void surrender(Long matchId, Long surrenderingUserId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null || match.getStatus() != Match.MatchStatus.ACTIVE) return;

        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(matchId);
        User opponent = players.stream()
                .map(MatchPlayer::getUser)
                .filter(u -> !u.getId().equals(surrenderingUserId))
                .findFirst()
                .orElse(null);

        if (opponent != null) {
            finalizeMatchWithWinner(match, opponent);
        } else {
            match.setStatus(Match.MatchStatus.CANCELLED);
            matchRepository.save(match);
        }
    }

    private void finalizeMatchWithWinner(Match match, User winner) {
        match.setStatus(Match.MatchStatus.FINISHED);
        match.setWinnerUser(winner);
        matchRepository.save(match);

        applyEloAndStats(match, winner.getId(), false);
    }

    private void finalizeMatch(Match match) {
        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(match.getId());
        if (players.size() < 2) return;

        MatchPlayer p1 = players.get(0);
        MatchPlayer p2 = players.get(1);

        int score1 = p1.getScore() != null ? p1.getScore() : 0;
        int score2 = p2.getScore() != null ? p2.getScore() : 0;

        if (score1 > score2) {
            match.setStatus(Match.MatchStatus.FINISHED);
            match.setWinnerUser(p1.getUser());
            applyEloAndStats(match, p1.getUser().getId(), false);
        } else if (score2 > score1) {
            match.setStatus(Match.MatchStatus.FINISHED);
            match.setWinnerUser(p2.getUser());
            applyEloAndStats(match, p2.getUser().getId(), false);
        } else {
            // Tie-breaker: Time taken
            int time1 = p1.getTimeTakenSeconds() != null ? p1.getTimeTakenSeconds() : Integer.MAX_VALUE;
            int time2 = p2.getTimeTakenSeconds() != null ? p2.getTimeTakenSeconds() : Integer.MAX_VALUE;

            if (time1 < time2) {
                match.setStatus(Match.MatchStatus.FINISHED);
                match.setWinnerUser(p1.getUser());
                applyEloAndStats(match, p1.getUser().getId(), false);
            } else if (time2 < time1) {
                match.setStatus(Match.MatchStatus.FINISHED);
                match.setWinnerUser(p2.getUser());
                applyEloAndStats(match, p2.getUser().getId(), false);
            } else {
                // Draw
                match.setStatus(Match.MatchStatus.DRAW);
                applyEloAndStats(match, null, true);
            }
        }
        matchRepository.save(match);
    }

    private void applyEloAndStats(Match match, Long winnerUserId, boolean isDraw) {
        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(match.getId());
        if (players.size() < 2) return;

        MatchPlayer p1 = players.get(0);
        MatchPlayer p2 = players.get(1);

        User u1 = p1.getUser();
        User u2 = p2.getUser();

        double score1 = isDraw ? 0.5 : (u1.getId().equals(winnerUserId) ? 1.0 : 0.0);
        EloService.EloResult eloResult = eloService.calculateNewRatings(p1.getRatingBefore(), p2.getRatingBefore(), score1);

        p1.setRatingAfter(eloResult.newRatingA());
        p1.setRatingChange(eloResult.changeA());
        p1.setResult(isDraw ? MatchPlayer.PlayerResult.DRAW : (u1.getId().equals(winnerUserId) ? MatchPlayer.PlayerResult.WIN : MatchPlayer.PlayerResult.LOSS));

        p2.setRatingAfter(eloResult.newRatingB());
        p2.setRatingChange(eloResult.changeB());
        p2.setResult(isDraw ? MatchPlayer.PlayerResult.DRAW : (u2.getId().equals(winnerUserId) ? MatchPlayer.PlayerResult.WIN : MatchPlayer.PlayerResult.LOSS));

        matchPlayerRepository.save(p1);
        matchPlayerRepository.save(p2);

        // Update User records
        updateUserStats(u1, p1.getResult(), eloResult.newRatingA());
        updateUserStats(u2, p2.getResult(), eloResult.newRatingB());

        // Update room status
        Room room = match.getRoom();
        room.setStatus(Room.RoomStatus.COMPLETED);
        roomRepository.save(room);
    }

    private void updateUserStats(User user, MatchPlayer.PlayerResult result, int newRating) {
        user.setRating(newRating);
        user.setMatchesPlayed(user.getMatchesPlayed() + 1);
        if (result == MatchPlayer.PlayerResult.WIN) user.setWins(user.getWins() + 1);
        else if (result == MatchPlayer.PlayerResult.LOSS) user.setLosses(user.getLosses() + 1);
        else if (result == MatchPlayer.PlayerResult.DRAW) user.setDraws(user.getDraws() + 1);
        userRepository.save(user);
    }
}
