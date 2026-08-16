package com.codeclash.controller;

import com.codeclash.dto.RoomDto;
import com.codeclash.service.RoomService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(HttpSession session) {
        Long userId = (Long) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Please login to create a room"));
        }
        RoomDto room = roomService.createRoom(userId);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinRoom(@RequestBody Map<String, String> payload, HttpSession session) {
        Long userId = (Long) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Please login to join a room"));
        }
        String roomCode = payload.get("roomCode");
        if (roomCode == null || roomCode.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Room code is required"));
        }

        try {
            RoomDto room = roomService.joinRoom(roomCode, userId);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{roomCode}")
    public ResponseEntity<?> getRoom(@PathVariable String roomCode) {
        try {
            RoomDto room = roomService.getRoom(roomCode);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{roomCode}/leave")
    public ResponseEntity<?> leaveRoom(@PathVariable String roomCode, HttpSession session) {
        Long userId = (Long) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId != null) {
            roomService.leaveRoom(roomCode, userId);
        }
        return ResponseEntity.ok(Map.of("message", "Left room"));
    }
}
