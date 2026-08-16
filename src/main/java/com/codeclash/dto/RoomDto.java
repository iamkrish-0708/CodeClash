package com.codeclash.dto;

import java.time.LocalDateTime;

public class RoomDto {
    private Long id;
    private String roomCode;
    private Long hostUserId;
    private String hostUsername;
    private Integer hostRating;
    private Long guestUserId;
    private String guestUsername;
    private Integer guestRating;
    private String status;
    private Long activeMatchId;
    private LocalDateTime createdAt;

    public RoomDto() {}

    public RoomDto(Long id, String roomCode, Long hostUserId, String hostUsername, Integer hostRating, Long guestUserId, String guestUsername, Integer guestRating, String status, Long activeMatchId, LocalDateTime createdAt) {
        this.id = id;
        this.roomCode = roomCode;
        this.hostUserId = hostUserId;
        this.hostUsername = hostUsername;
        this.hostRating = hostRating;
        this.guestUserId = guestUserId;
        this.guestUsername = guestUsername;
        this.guestRating = guestRating;
        this.status = status;
        this.activeMatchId = activeMatchId;
        this.createdAt = createdAt;
    }

    public static RoomDtoBuilder builder() { return new RoomDtoBuilder(); }

    public static class RoomDtoBuilder {
        private Long id;
        private String roomCode;
        private Long hostUserId;
        private String hostUsername;
        private Integer hostRating;
        private Long guestUserId;
        private String guestUsername;
        private Integer guestRating;
        private String status;
        private Long activeMatchId;
        private LocalDateTime createdAt;

        public RoomDtoBuilder id(Long id) { this.id = id; return this; }
        public RoomDtoBuilder roomCode(String roomCode) { this.roomCode = roomCode; return this; }
        public RoomDtoBuilder hostUserId(Long hostUserId) { this.hostUserId = hostUserId; return this; }
        public RoomDtoBuilder hostUsername(String hostUsername) { this.hostUsername = hostUsername; return this; }
        public RoomDtoBuilder hostRating(Integer hostRating) { this.hostRating = hostRating; return this; }
        public RoomDtoBuilder guestUserId(Long guestUserId) { this.guestUserId = guestUserId; return this; }
        public RoomDtoBuilder guestUsername(String guestUsername) { this.guestUsername = guestUsername; return this; }
        public RoomDtoBuilder guestRating(Integer guestRating) { this.guestRating = guestRating; return this; }
        public RoomDtoBuilder status(String status) { this.status = status; return this; }
        public RoomDtoBuilder activeMatchId(Long activeMatchId) { this.activeMatchId = activeMatchId; return this; }
        public RoomDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public RoomDto build() {
            return new RoomDto(id, roomCode, hostUserId, hostUsername, hostRating, guestUserId, guestUsername, guestRating, status, activeMatchId, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }
    public Long getHostUserId() { return hostUserId; }
    public void setHostUserId(Long hostUserId) { this.hostUserId = hostUserId; }
    public String getHostUsername() { return hostUsername; }
    public void setHostUsername(String hostUsername) { this.hostUsername = hostUsername; }
    public Integer getHostRating() { return hostRating; }
    public void setHostRating(Integer hostRating) { this.hostRating = hostRating; }
    public Long getGuestUserId() { return guestUserId; }
    public void setGuestUserId(Long guestUserId) { this.guestUserId = guestUserId; }
    public String getGuestUsername() { return guestUsername; }
    public void setGuestUsername(String guestUsername) { this.guestUsername = guestUsername; }
    public Integer getGuestRating() { return guestRating; }
    public void setGuestRating(Integer guestRating) { this.guestRating = guestRating; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getActiveMatchId() { return activeMatchId; }
    public void setActiveMatchId(Long activeMatchId) { this.activeMatchId = activeMatchId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
