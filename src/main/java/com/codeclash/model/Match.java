package com.codeclash.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    @Column(nullable = false, length = 20)
    private MatchStatus status = MatchStatus.ACTIVE;

    @Builder.Default
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_user_id")
    private User winnerUser;

    @Builder.Default
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchPlayer> players = new ArrayList<>();

    public enum MatchStatus {
        ACTIVE,
        FINISHED,
        DRAW,
        CANCELLED
    }
}
