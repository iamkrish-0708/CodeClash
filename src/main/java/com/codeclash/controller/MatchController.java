package com.codeclash.controller;

import com.codeclash.dto.MatchStatusDto;
import com.codeclash.service.MatchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startMatch(@RequestBody Map<String, Object> payload, HttpSession session) {
        Long userId = (Long) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Please login to start a match"));
        }

        Long roomId = Long.valueOf(payload.get("roomId").toString());
        Long problemId = payload.get("problemId") != null ? Long.valueOf(payload.get("problemId").toString()) : null;

        try {
            MatchStatusDto match = matchService.startMatch(roomId, problemId, userId);
            return ResponseEntity.ok(match);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{matchId}/status")
    public ResponseEntity<?> getMatchStatus(@PathVariable Long matchId) {
        try {
            MatchStatusDto status = matchService.getMatchStatus(matchId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{matchId}/surrender")
    public ResponseEntity<?> surrender(@PathVariable Long matchId, HttpSession session) {
        Long userId = (Long) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
        matchService.surrender(matchId, userId);
        return ResponseEntity.ok(Map.of("message", "Surrendered"));
    }
}
