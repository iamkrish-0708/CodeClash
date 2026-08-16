package com.codeclash.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(nullable = false, length = 30)
    private String language = "JAVA";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SubmissionStatus status;

    @Column(name = "passed_test_cases", nullable = false)
    private Integer passedTestCases = 0;

    @Column(name = "total_test_cases", nullable = false)
    private Integer totalTestCases = 0;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    @Column(name = "compile_output", columnDefinition = "TEXT")
    private String compileOutput;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    public enum SubmissionStatus {
        PENDING,
        ACCEPTED,
        WRONG_ANSWER,
        TIME_LIMIT_EXCEEDED,
        COMPILATION_ERROR,
        RUNTIME_ERROR,
        MEMORY_LIMIT_EXCEEDED
    }

    public Submission() {}

    public Submission(Long id, Match match, User user, Problem problem, String code, String language, SubmissionStatus status, Integer passedTestCases, Integer totalTestCases, Long executionTimeMs, String compileOutput, LocalDateTime submittedAt) {
        this.id = id;
        this.match = match;
        this.user = user;
        this.problem = problem;
        this.code = code;
        this.language = language != null ? language : "JAVA";
        this.status = status;
        this.passedTestCases = passedTestCases != null ? passedTestCases : 0;
        this.totalTestCases = totalTestCases != null ? totalTestCases : 0;
        this.executionTimeMs = executionTimeMs;
        this.compileOutput = compileOutput;
        this.submittedAt = submittedAt != null ? submittedAt : LocalDateTime.now();
    }

    public static SubmissionBuilder builder() { return new SubmissionBuilder(); }

    public static class SubmissionBuilder {
        private Long id;
        private Match match;
        private User user;
        private Problem problem;
        private String code;
        private String language = "JAVA";
        private SubmissionStatus status;
        private Integer passedTestCases = 0;
        private Integer totalTestCases = 0;
        private Long executionTimeMs;
        private String compileOutput;
        private LocalDateTime submittedAt = LocalDateTime.now();

        public SubmissionBuilder id(Long id) { this.id = id; return this; }
        public SubmissionBuilder match(Match match) { this.match = match; return this; }
        public SubmissionBuilder user(User user) { this.user = user; return this; }
        public SubmissionBuilder problem(Problem problem) { this.problem = problem; return this; }
        public SubmissionBuilder code(String code) { this.code = code; return this; }
        public SubmissionBuilder language(String language) { this.language = language; return this; }
        public SubmissionBuilder status(SubmissionStatus status) { this.status = status; return this; }
        public SubmissionBuilder passedTestCases(Integer passedTestCases) { this.passedTestCases = passedTestCases; return this; }
        public SubmissionBuilder totalTestCases(Integer totalTestCases) { this.totalTestCases = totalTestCases; return this; }
        public SubmissionBuilder executionTimeMs(Long executionTimeMs) { this.executionTimeMs = executionTimeMs; return this; }
        public SubmissionBuilder compileOutput(String compileOutput) { this.compileOutput = compileOutput; return this; }
        public SubmissionBuilder submittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; return this; }

        public Submission build() {
            return new Submission(id, match, user, problem, code, language, status, passedTestCases, totalTestCases, executionTimeMs, compileOutput, submittedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus status) { this.status = status; }
    public Integer getPassedTestCases() { return passedTestCases; }
    public void setPassedTestCases(Integer passedTestCases) { this.passedTestCases = passedTestCases; }
    public Integer getTotalTestCases() { return totalTestCases; }
    public void setTotalTestCases(Integer totalTestCases) { this.totalTestCases = totalTestCases; }
    public Long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(Long executionTimeMs) { this.executionTimeMs = executionTimeMs; }
    public String getCompileOutput() { return compileOutput; }
    public void setCompileOutput(String compileOutput) { this.compileOutput = compileOutput; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
