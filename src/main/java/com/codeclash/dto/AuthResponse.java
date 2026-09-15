package com.codeclash.dto;

import java.util.List;

public class AuthResponse {
    private boolean success;
    private String message;
    private Long userId;
    private String username;
    private String email;
    private Integer rating;
    private boolean requireUsername;
    private String suggestedUsername;
    private String idToken;
    private List<String> validationErrors;

    public AuthResponse() {}

    public AuthResponse(boolean success, String message, Long userId, String username, String email, Integer rating,
                        boolean requireUsername, String suggestedUsername, String idToken, List<String> validationErrors) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.requireUsername = requireUsername;
        this.suggestedUsername = suggestedUsername;
        this.idToken = idToken;
        this.validationErrors = validationErrors;
    }

    public static AuthResponseBuilder builder() { return new AuthResponseBuilder(); }

    public static class AuthResponseBuilder {
        private boolean success;
        private String message;
        private Long userId;
        private String username;
        private String email;
        private Integer rating;
        private boolean requireUsername;
        private String suggestedUsername;
        private String idToken;
        private List<String> validationErrors;

        public AuthResponseBuilder success(boolean success) { this.success = success; return this; }
        public AuthResponseBuilder message(String message) { this.message = message; return this; }
        public AuthResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public AuthResponseBuilder username(String username) { this.username = username; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder rating(Integer rating) { this.rating = rating; return this; }
        public AuthResponseBuilder requireUsername(boolean requireUsername) { this.requireUsername = requireUsername; return this; }
        public AuthResponseBuilder suggestedUsername(String suggestedUsername) { this.suggestedUsername = suggestedUsername; return this; }
        public AuthResponseBuilder idToken(String idToken) { this.idToken = idToken; return this; }
        public AuthResponseBuilder validationErrors(List<String> validationErrors) { this.validationErrors = validationErrors; return this; }

        public AuthResponse build() {
            return new AuthResponse(success, message, userId, username, email, rating, requireUsername, suggestedUsername, idToken, validationErrors);
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
    public boolean isRequireUsername() { return requireUsername; }
    public void setRequireUsername(boolean requireUsername) { this.requireUsername = requireUsername; }
    public String getSuggestedUsername() { return suggestedUsername; }
    public void setSuggestedUsername(String suggestedUsername) { this.suggestedUsername = suggestedUsername; }
    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }
    public List<String> getValidationErrors() { return validationErrors; }
    public void setValidationErrors(List<String> validationErrors) { this.validationErrors = validationErrors; }
}
