package com.codeclash.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Builder.Default
    @Column(nullable = false)
    private Integer rating = 1200;

    @Builder.Default
    @Column(name = "matches_played", nullable = false)
    private Integer matchesPlayed = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer wins = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer losses = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer draws = 0;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
