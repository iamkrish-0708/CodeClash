package com.codeclash.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
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
    @Column(nullable = false, length = 20)
    private RoomStatus status = RoomStatus.WAITING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum RoomStatus {
        WAITING,
        IN_PROGRESS,
        COMPLETED,
        ABANDONED
    }

    public Room() {}

    public Room(Long id, String roomCode, User hostUser, User guestUser, RoomStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.roomCode = roomCode;
        this.hostUser = hostUser;
        this.guestUser = guestUser;
        this.status = status != null ? status : RoomStatus.WAITING;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public static RoomBuilder builder() { return new RoomBuilder(); }

    public static class RoomBuilder {
        private Long id;
        private String roomCode;
        private User hostUser;
        private User guestUser;
        private RoomStatus status = RoomStatus.WAITING;
        private LocalDateTime createdAt = LocalDateTime.now();

        public RoomBuilder id(Long id) { this.id = id; return this; }
        public RoomBuilder roomCode(String roomCode) { this.roomCode = roomCode; return this; }
        public RoomBuilder hostUser(User hostUser) { this.hostUser = hostUser; return this; }
        public RoomBuilder guestUser(User guestUser) { this.guestUser = guestUser; return this; }
        public RoomBuilder status(RoomStatus status) { this.status = status; return this; }
        public RoomBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Room build() {
            return new Room(id, roomCode, hostUser, guestUser, status, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }
    public User getHostUser() { return hostUser; }
    public void setHostUser(User hostUser) { this.hostUser = hostUser; }
    public User getGuestUser() { return guestUser; }
    public void setGuestUser(User guestUser) { this.guestUser = guestUser; }
    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
