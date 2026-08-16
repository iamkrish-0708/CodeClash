package com.codeclash.service;

import com.codeclash.dto.RoomDto;
import com.codeclash.model.Match;
import com.codeclash.model.Room;
import com.codeclash.model.User;
import com.codeclash.repository.MatchRepository;
import com.codeclash.repository.RoomRepository;
import com.codeclash.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional
    public RoomDto createRoom(Long hostUserId) {
        User host = userRepository.findById(hostUserId)
                .orElseThrow(() -> new IllegalArgumentException("Host user not found"));

        String roomCode;
        do {
            roomCode = generateRoomCode();
        } while (roomRepository.findByRoomCode(roomCode).isPresent());

        Room room = Room.builder()
                .roomCode(roomCode)
                .hostUser(host)
                .status(Room.RoomStatus.WAITING)
                .build();

        Room saved = roomRepository.save(room);
        return toDto(saved);
    }

    @Transactional
    public RoomDto joinRoom(String roomCode, Long guestUserId) {
        Room room = roomRepository.findByRoomCode(roomCode.trim().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with code: " + roomCode));

        if (room.getStatus() != Room.RoomStatus.WAITING) {
            throw new IllegalStateException("Room is already in progress or closed");
        }

        if (room.getHostUser().getId().equals(guestUserId)) {
            return toDto(room); // Host rejoining own room
        }

        if (room.getGuestUser() != null && !room.getGuestUser().getId().equals(guestUserId)) {
            throw new IllegalStateException("Room is already full");
        }

        User guest = userRepository.findById(guestUserId)
                .orElseThrow(() -> new IllegalArgumentException("Guest user not found"));

        room.setGuestUser(guest);
        Room saved = roomRepository.save(room);
        return toDto(saved);
    }

    public RoomDto getRoom(String roomCode) {
        Room room = roomRepository.findByRoomCode(roomCode.trim().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomCode));
        return toDto(room);
    }

    @Transactional
    public void leaveRoom(String roomCode, Long userId) {
        Room room = roomRepository.findByRoomCode(roomCode.trim().toUpperCase())
                .orElse(null);
        if (room == null) return;

        if (room.getHostUser().getId().equals(userId)) {
            room.setStatus(Room.RoomStatus.ABANDONED);
            roomRepository.save(room);
        } else if (room.getGuestUser() != null && room.getGuestUser().getId().equals(userId)) {
            room.setGuestUser(null);
            roomRepository.save(room);
        }
    }

    private RoomDto toDto(Room room) {
        Long matchId = matchRepository.findByRoomId(room.getId())
                .map(Match::getId)
                .orElse(null);

        return RoomDto.builder()
                .id(room.getId())
                .roomCode(room.getRoomCode())
                .hostUserId(room.getHostUser().getId())
                .hostUsername(room.getHostUser().getUsername())
                .hostRating(room.getHostUser().getRating())
                .guestUserId(room.getGuestUser() != null ? room.getGuestUser().getId() : null)
                .guestUsername(room.getGuestUser() != null ? room.getGuestUser().getUsername() : null)
                .guestRating(room.getGuestUser() != null ? room.getGuestUser().getRating() : null)
                .status(room.getStatus().name())
                .activeMatchId(matchId)
                .createdAt(room.getCreatedAt())
                .build();
    }

    private String generateRoomCode() {
        StringBuilder sb = new StringBuilder("CLASH-");
        for (int i = 0; i < 4; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
