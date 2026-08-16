package com.codeclash.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code", nullable = false, unique = true, length = 20)
    private String roomCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_user_id", nullable = false)
    private User hostUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_user_id")
    private User guestUser;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private RoomStatus status = RoomStatus.WAITING;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum RoomStatus {
        WAITING,
        IN_PROGRESS,
        COMPLETED,
        ABANDONED
    }
}
