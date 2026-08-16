package com.codeclash.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CodeExecutionRequest {

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    private Long matchId; // Optional for practice/single-player runs

    @NotBlank(message = "Code cannot be empty")
    private String code;

    private String language = "JAVA";
}
