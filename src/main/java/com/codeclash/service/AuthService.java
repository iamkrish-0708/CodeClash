package com.codeclash.service;

import com.codeclash.dto.AuthResponse;
import com.codeclash.dto.LoginRequest;
import com.codeclash.dto.RegisterRequest;
import com.codeclash.dto.UserProfileDto;
import com.codeclash.model.User;
import com.codeclash.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Username is already taken")
                    .build();
        }

        if (userRepository.existsByEmail(email)) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Email is already registered")
                    .build();
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .rating(1200)
                .matchesPlayed(0)
                .wins(0)
                .losses(0)
                .draws(0)
                .build();

        User savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .success(true)
                .message("User registered successfully")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .rating(savedUser.getRating())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        String identifier = request.getUsernameOrEmail().trim();

        User user = userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier.toLowerCase()))
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Invalid username/email or password")
                    .build();
        }

        return AuthResponse.builder()
                .success(true)
                .message("Login successful")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .rating(user.getRating())
                .build();
    }

    public UserProfileDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return toProfileDto(user);
    }

    public UserProfileDto getProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return toProfileDto(user);
    }

    private UserProfileDto toProfileDto(User user) {
        double winRate = 0.0;
        if (user.getMatchesPlayed() > 0) {
            winRate = Math.round(((double) user.getWins() / user.getMatchesPlayed()) * 1000.0) / 10.0;
        }

        return UserProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .rating(user.getRating())
                .matchesPlayed(user.getMatchesPlayed())
                .wins(user.getWins())
                .losses(user.getLosses())
                .draws(user.getDraws())
                .winRate(winRate)
                .createdAt(user.getCreatedAt())
                .build();
    }
}
