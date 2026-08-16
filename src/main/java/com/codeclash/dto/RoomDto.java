package com.codeclash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String roomCode;
    private Long hostUserId;
    private String hostUsername;
    private Integer hostRating;
    private Long guestUserId;
    private String guestUsername;
    private Integer guestRating;
    private String status; // WAITING, IN_PROGRESS, COMPLETED
    private Long activeMatchId;
    private LocalDateTime createdAt;
}
