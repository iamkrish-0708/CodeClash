package com.codeclash.dto;

import java.util.ArrayList;
import java.util.List;

public class ExecutionResult {
    private String status;
    private boolean success;
    private int passedTestCases;
    private int totalTestCases;
    private long executionTimeMs;
    private String compileOutput;
    private List<TestCaseResult> testCaseResults = new ArrayList<>();

    public ExecutionResult() {}

    public ExecutionResult(String status, boolean success, int passedTestCases, int totalTestCases, long executionTimeMs, String compileOutput, List<TestCaseResult> testCaseResults) {
        this.status = status;
        this.success = success;
        this.passedTestCases = passedTestCases;
        this.totalTestCases = totalTestCases;
        this.executionTimeMs = executionTimeMs;
        this.compileOutput = compileOutput;
        this.testCaseResults = testCaseResults != null ? testCaseResults : new ArrayList<>();
    }

    public static ExecutionResultBuilder builder() { return new ExecutionResultBuilder(); }

    public static class ExecutionResultBuilder {
        private String status;
        private boolean success;
        private int passedTestCases;
        private int totalTestCases;
        private long executionTimeMs;
        private String compileOutput;
        private List<TestCaseResult> testCaseResults = new ArrayList<>();

        public ExecutionResultBuilder status(String status) { this.status = status; return this; }
        public ExecutionResultBuilder success(boolean success) { this.success = success; return this; }
        public ExecutionResultBuilder passedTestCases(int passedTestCases) { this.passedTestCases = passedTestCases; return this; }
        public ExecutionResultBuilder totalTestCases(int totalTestCases) { this.totalTestCases = totalTestCases; return this; }
        public ExecutionResultBuilder executionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; return this; }
        public ExecutionResultBuilder compileOutput(String compileOutput) { this.compileOutput = compileOutput; return this; }
        public ExecutionResultBuilder testCaseResults(List<TestCaseResult> testCaseResults) { this.testCaseResults = testCaseResults; return this; }

        public ExecutionResult build() {
            return new ExecutionResult(status, success, passedTestCases, totalTestCases, executionTimeMs, compileOutput, testCaseResults);
        }
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public int getPassedTestCases() { return passedTestCases; }
    public void setPassedTestCases(int passedTestCases) { this.passedTestCases = passedTestCases; }
    public int getTotalTestCases() { return totalTestCases; }
    public void setTotalTestCases(int totalTestCases) { this.totalTestCases = totalTestCases; }
    public long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; }
    public String getCompileOutput() { return compileOutput; }
    public void setCompileOutput(String compileOutput) { this.compileOutput = compileOutput; }
    public List<TestCaseResult> getTestCaseResults() { return testCaseResults; }
    public void setTestCaseResults(List<TestCaseResult> testCaseResults) { this.testCaseResults = testCaseResults; }

    public static class TestCaseResult {
        private int testCaseIndex;
        private boolean passed;
        private String input;
        private String expectedOutput;
        private String actualOutput;
        private String error;
        private boolean isHidden;

        public TestCaseResult() {}

        public TestCaseResult(int testCaseIndex, boolean passed, String input, String expectedOutput, String actualOutput, String error, boolean isHidden) {
            this.testCaseIndex = testCaseIndex;
            this.passed = passed;
            this.input = input;
            this.expectedOutput = expectedOutput;
            this.actualOutput = actualOutput;
            this.error = error;
            this.isHidden = isHidden;
        }

        public static TestCaseResultBuilder builder() { return new TestCaseResultBuilder(); }

        public static class TestCaseResultBuilder {
            private int testCaseIndex;
            private boolean passed;
            private String input;
            private String expectedOutput;
            private String actualOutput;
            private String error;
            private boolean isHidden;

            public TestCaseResultBuilder testCaseIndex(int testCaseIndex) { this.testCaseIndex = testCaseIndex; return this; }
            public TestCaseResultBuilder passed(boolean passed) { this.passed = passed; return this; }
            public TestCaseResultBuilder input(String input) { this.input = input; return this; }
            public TestCaseResultBuilder expectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; return this; }
            public TestCaseResultBuilder actualOutput(String actualOutput) { this.actualOutput = actualOutput; return this; }
            public TestCaseResultBuilder error(String error) { this.error = error; return this; }
            public TestCaseResultBuilder isHidden(boolean isHidden) { this.isHidden = isHidden; return this; }

            public TestCaseResult build() {
                return new TestCaseResult(testCaseIndex, passed, input, expectedOutput, actualOutput, error, isHidden);
            }
        }

        public int getTestCaseIndex() { return testCaseIndex; }
        public void setTestCaseIndex(int testCaseIndex) { this.testCaseIndex = testCaseIndex; }
        public boolean isPassed() { return passed; }
        public void setPassed(boolean passed) { this.passed = passed; }
        public String getInput() { return input; }
        public void setInput(String input) { this.input = input; }
        public String getExpectedOutput() { return expectedOutput; }
        public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }
        public String getActualOutput() { return actualOutput; }
        public void setActualOutput(String actualOutput) { this.actualOutput = actualOutput; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public boolean isHidden() { return isHidden; }
        public void setHidden(boolean hidden) { isHidden = hidden; }
    }
}
