package com.codeclash.controller;

import com.codeclash.model.User;
import com.codeclash.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<User>> getLeaderboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<User> leaderboard = userRepository.findAllByOrderByRatingDesc(PageRequest.of(page, size));
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/top")
    public ResponseEntity<List<User>> getTop10() {
        return ResponseEntity.ok(userRepository.findTop10ByOrderByRatingDesc());
    }
}
