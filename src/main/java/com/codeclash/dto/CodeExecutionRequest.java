package com.codeclash.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CodeExecutionRequest {

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    private Long matchId;

    @NotBlank(message = "Code cannot be empty")
    private String code;

    private String language = "JAVA";

    public CodeExecutionRequest() {}

    public CodeExecutionRequest(Long problemId, Long matchId, String code, String language) {
        this.problemId = problemId;
        this.matchId = matchId;
        this.code = code;
        this.language = language != null ? language : "JAVA";
    }

    public Long getProblemId() { return problemId; }
    public void setProblemId(Long problemId) { this.problemId = problemId; }
    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
