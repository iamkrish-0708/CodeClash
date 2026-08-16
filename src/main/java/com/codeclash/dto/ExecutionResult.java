package com.codeclash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResult {
    private String status; // ACCEPTED, WRONG_ANSWER, TIME_LIMIT_EXCEEDED, COMPILATION_ERROR, RUNTIME_ERROR
    private boolean success;
    private int passedTestCases;
    private int totalTestCases;
    private long executionTimeMs;
    private String compileOutput;
    private List<TestCaseResult> testCaseResults;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestCaseResult {
        private int testCaseIndex;
        private boolean passed;
        private String input;
        private String expectedOutput;
        private String actualOutput;
        private String error;
        private boolean isHidden;
    }
}
