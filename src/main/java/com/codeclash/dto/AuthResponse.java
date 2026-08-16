package com.codeclash.dto;

public class AuthResponse {
    private boolean success;
    private String message;
    private Long userId;
    private String username;
    private String email;
    private Integer rating;

    public AuthResponse() {}

    public AuthResponse(boolean success, String message, Long userId, String username, String email, Integer rating) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.rating = rating;
    }

    public static AuthResponseBuilder builder() { return new AuthResponseBuilder(); }

    public static class AuthResponseBuilder {
        private boolean success;
        private String message;
        private Long userId;
        private String username;
        private String email;
        private Integer rating;

        public AuthResponseBuilder success(boolean success) { this.success = success; return this; }
        public AuthResponseBuilder message(String message) { this.message = message; return this; }
        public AuthResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public AuthResponseBuilder username(String username) { this.username = username; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder rating(Integer rating) { this.rating = rating; return this; }

        public AuthResponse build() {
            return new AuthResponse(success, message, userId, username, email, rating);
        }
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}
