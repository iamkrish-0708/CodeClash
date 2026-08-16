package com.codeclash.controller;

import com.codeclash.dto.AuthResponse;
import com.codeclash.dto.LoginRequest;
import com.codeclash.dto.RegisterRequest;
import com.codeclash.dto.UserProfileDto;
import com.codeclash.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public static final String SESSION_USER_ID = "USER_ID";
    public static final String SESSION_USERNAME = "USERNAME";

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, HttpSession session) {
        AuthResponse response = authService.register(request);
        if (response.isSuccess()) {
            session.setAttribute(SESSION_USER_ID, response.getUserId());
            session.setAttribute(SESSION_USERNAME, response.getUsername());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        AuthResponse response = authService.login(request);
        if (response.isSuccess()) {
            session.setAttribute(SESSION_USER_ID, response.getUserId());
            session.setAttribute(SESSION_USERNAME, response.getUsername());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().body("{\"message\": \"Logged out successfully\"}");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body("{\"error\": \"Not authenticated\"}");
        }
        UserProfileDto profile = authService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
}
