package com.codeclash.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Builder.Default
    @Column(nullable = false, length = 30)
    private String language = "JAVA";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SubmissionStatus status;

    @Builder.Default
    @Column(name = "passed_test_cases", nullable = false)
    private Integer passedTestCases = 0;

    @Builder.Default
    @Column(name = "total_test_cases", nullable = false)
    private Integer totalTestCases = 0;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    @Column(name = "compile_output", columnDefinition = "TEXT")
    private String compileOutput;

    @Builder.Default
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
}
