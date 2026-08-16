package com.codeclash.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, unique = true, length = 150)
    private String slug;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Column(name = "starter_code_java", columnDefinition = "TEXT")
    private String starterCodeJava;

    @Builder.Default
    @Column(name = "time_limit_seconds", nullable = false)
    private Integer timeLimitSeconds = 900; // 15 mins default

    @Builder.Default
    @Column(name = "memory_limit_mb", nullable = false)
    private Integer memoryLimitMb = 128;

    @Builder.Default
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TestCase> testCases = new ArrayList<>();

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
        testCase.setProblem(this);
    }
}
