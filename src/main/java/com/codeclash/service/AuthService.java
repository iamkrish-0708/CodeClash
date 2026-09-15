package com.codeclash.service;

import com.codeclash.dto.AuthResponse;
import com.codeclash.dto.GoogleAuthRequest;
import com.codeclash.dto.LoginRequest;
import com.codeclash.dto.RegisterRequest;
import com.codeclash.dto.UserProfileDto;
import com.codeclash.model.User;
import com.codeclash.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${google.client.id:258922497438-asurq360ieu36s8i39b78c13avpmab5c.apps.googleusercontent.com}")
    private String googleClientId;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{2,19}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = request.getUsername() != null ? request.getUsername().trim() : "";
        String email = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : "";
        String password = request.getPassword() != null ? request.getPassword() : "";

        List<String> errors = validateRegistrationDetails(username, email, password);

        if (userRepository.existsByUsername(username)) {
            errors.add("Username is already taken by another duelist");
        }

        if (userRepository.existsByEmail(email)) {
            errors.add("Email address is already registered");
        }

        if (!errors.isEmpty()) {
            return AuthResponse.builder()
                    .success(false)
                    .message(errors.get(0))
                    .validationErrors(errors)
                    .build();
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .authProvider("LOCAL")
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
        String identifier = request.getUsernameOrEmail() != null ? request.getUsernameOrEmail().trim() : "";

        User user = userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier.toLowerCase()))
                .orElse(null);

        if (user == null || user.getPasswordHash() == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
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

    @Transactional
    public AuthResponse authenticateGoogleUser(GoogleAuthRequest request) {
        if (request.getIdToken() == null || request.getIdToken().trim().isEmpty()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Missing Google ID Token")
                    .build();
        }

        GoogleIdToken.Payload payload = verifyGoogleIdToken(request.getIdToken().trim());
        if (payload == null) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Invalid or expired Google authentication token")
                    .build();
        }

        String email = payload.getEmail().toLowerCase().trim();
        String name = (String) payload.get("name");

        // Check if user with this email already exists
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser != null) {
            return AuthResponse.builder()
                    .success(true)
                    .message("Google login successful")
                    .userId(existingUser.getId())
                    .username(existingUser.getUsername())
                    .email(existingUser.getEmail())
                    .rating(existingUser.getRating())
                    .build();
        }

        // New Google User Onboarding: Check if username was provided
        String requestedUsername = request.getUsername() != null ? request.getUsername().trim() : "";
        if (requestedUsername.isEmpty()) {
            // Suggest a username from email prefix or name
            String rawSeed = name != null && !name.isBlank() ? name : email.split("@")[0];
            String sanitized = rawSeed.replaceAll("[^a-zA-Z0-9_]", "_");
            if (sanitized.length() > 18) sanitized = sanitized.substring(0, 18);
            if (!sanitized.matches("^[a-zA-Z].*")) sanitized = "duelist_" + sanitized;
            if (sanitized.length() < 3) sanitized = "coder_" + (int)(Math.random() * 900 + 100);

            String suggestedUsername = sanitized;
            int counter = 1;
            while (userRepository.existsByUsername(suggestedUsername)) {
                suggestedUsername = sanitized + counter;
                counter++;
            }

            return AuthResponse.builder()
                    .success(false)
                    .requireUsername(true)
                    .email(email)
                    .suggestedUsername(suggestedUsername)
                    .idToken(request.getIdToken().trim())
                    .message("Please confirm or customize your Duelist Handle to complete registration")
                    .build();
        }

        // Validate the provided username
        List<String> usernameErrors = validateUsername(requestedUsername);
        if (userRepository.existsByUsername(requestedUsername)) {
            usernameErrors.add("Username is already taken by another duelist");
        }

        if (!usernameErrors.isEmpty()) {
            return AuthResponse.builder()
                    .success(false)
                    .requireUsername(true)
                    .email(email)
                    .suggestedUsername(requestedUsername)
                    .idToken(request.getIdToken().trim())
                    .message(usernameErrors.get(0))
                    .validationErrors(usernameErrors)
                    .build();
        }

        // Create new Google User
        User newUser = User.builder()
                .username(requestedUsername)
                .email(email)
                .passwordHash(null)
                .authProvider("GOOGLE")
                .rating(1200)
                .matchesPlayed(0)
                .wins(0)
                .losses(0)
                .draws(0)
                .build();

        User savedUser = userRepository.save(newUser);

        return AuthResponse.builder()
                .success(true)
                .message("Google account registered successfully")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .rating(savedUser.getRating())
                .build();
    }

    private GoogleIdToken.Payload verifyGoogleIdToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
            .setAudience(Collections.singletonList(googleClientId))
            .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            }
        } catch (Exception e) {
            System.err.println("Google ID Token verification failed: " + e.getMessage());
        }
        return null;
    }

    private List<String> validateRegistrationDetails(String username, String email, String password) {
        List<String> errors = new ArrayList<>(validateUsername(username));

        // Email validation
        if (email.isEmpty()) {
            errors.add("Email address is required");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.add("Email address must be in a valid format (e.g., player@example.com)");
        }

        // Password validation
        if (password.isEmpty()) {
            errors.add("Password is required");
        } else {
            if (password.length() < 8) {
                errors.add("Password must be at least 8 characters long");
            }
            if (!password.matches(".*[A-Z].*")) {
                errors.add("Password must contain at least one uppercase letter (A-Z)");
            }
            if (!password.matches(".*[a-z].*")) {
                errors.add("Password must contain at least one lowercase letter (a-z)");
            }
            if (!password.matches(".*[0-9].*")) {
                errors.add("Password must contain at least one numeric digit (0-9)");
            }
            if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                errors.add("Password must contain at least one special character (!@#$%^&*...)");
            }
        }

        return errors;
    }

    private List<String> validateUsername(String username) {
        List<String> errors = new ArrayList<>();
        if (username.isEmpty()) {
            errors.add("Username is required");
            return errors;
        }
        if (username.length() < 3 || username.length() > 20) {
            errors.add("Username must be between 3 and 20 characters");
        }
        if (!Character.isLetter(username.charAt(0))) {
            errors.add("Username must start with a letter (A-Z or a-z)");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            errors.add("Username can only contain letters, numbers, and underscores");
        }
        return errors;
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
