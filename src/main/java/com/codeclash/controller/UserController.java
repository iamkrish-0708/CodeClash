package com.codeclash.controller;

import com.codeclash.dto.UserProfileDto;
import com.codeclash.model.MatchPlayer;
import com.codeclash.repository.MatchPlayerRepository;
import com.codeclash.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final MatchPlayerRepository matchPlayerRepository;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String username) {
        UserProfileDto profile = authService.getProfileByUsername(username);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{userId}/matches")
    public ResponseEntity<List<MatchPlayer>> getUserMatchHistory(@PathVariable Long userId) {
        List<MatchPlayer> matches = matchPlayerRepository.findRecentMatchesByUserId(userId);
        return ResponseEntity.ok(matches);
    }
}
