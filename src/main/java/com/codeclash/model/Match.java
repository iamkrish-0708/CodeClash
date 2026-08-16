package com.codeclash.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MatchStatus status = MatchStatus.ACTIVE;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_user_id")
    private User winnerUser;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchPlayer> players = new ArrayList<>();

    public enum MatchStatus {
        ACTIVE,
        FINISHED,
        DRAW,
        CANCELLED
    }

    public Match() {}

    public Match(Long id, Room room, Problem problem, MatchStatus status, LocalDateTime startedAt, LocalDateTime endsAt, User winnerUser, List<MatchPlayer> players) {
        this.id = id;
        this.room = room;
        this.problem = problem;
        this.status = status != null ? status : MatchStatus.ACTIVE;
        this.startedAt = startedAt != null ? startedAt : LocalDateTime.now();
        this.endsAt = endsAt;
        this.winnerUser = winnerUser;
        this.players = players != null ? players : new ArrayList<>();
    }

    public static MatchBuilder builder() { return new MatchBuilder(); }

    public static class MatchBuilder {
        private Long id;
        private Room room;
        private Problem problem;
        private MatchStatus status = MatchStatus.ACTIVE;
        private LocalDateTime startedAt = LocalDateTime.now();
        private LocalDateTime endsAt;
        private User winnerUser;
        private List<MatchPlayer> players = new ArrayList<>();

        public MatchBuilder id(Long id) { this.id = id; return this; }
        public MatchBuilder room(Room room) { this.room = room; return this; }
        public MatchBuilder problem(Problem problem) { this.problem = problem; return this; }
        public MatchBuilder status(MatchStatus status) { this.status = status; return this; }
        public MatchBuilder startedAt(LocalDateTime startedAt) { this.startedAt = startedAt; return this; }
        public MatchBuilder endsAt(LocalDateTime endsAt) { this.endsAt = endsAt; return this; }
        public MatchBuilder winnerUser(User winnerUser) { this.winnerUser = winnerUser; return this; }
        public MatchBuilder players(List<MatchPlayer> players) { this.players = players; return this; }

        public Match build() {
            return new Match(id, room, problem, status, startedAt, endsAt, winnerUser, players);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }
    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalDateTime endsAt) { this.endsAt = endsAt; }
    public User getWinnerUser() { return winnerUser; }
    public void setWinnerUser(User winnerUser) { this.winnerUser = winnerUser; }
    public List<MatchPlayer> getPlayers() { return players; }
    public void setPlayers(List<MatchPlayer> players) { this.players = players; }
}
