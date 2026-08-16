package com.codeclash.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
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

    @Column(name = "time_limit_seconds", nullable = false)
    private Integer timeLimitSeconds = 900;

    @Column(name = "memory_limit_mb", nullable = false)
    private Integer memoryLimitMb = 128;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TestCase> testCases = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public Problem() {}

    public Problem(Long id, String title, String slug, String description, Difficulty difficulty, String starterCodeJava, Integer timeLimitSeconds, Integer memoryLimitMb, List<TestCase> testCases, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.difficulty = difficulty;
        this.starterCodeJava = starterCodeJava;
        this.timeLimitSeconds = timeLimitSeconds != null ? timeLimitSeconds : 900;
        this.memoryLimitMb = memoryLimitMb != null ? memoryLimitMb : 128;
        this.testCases = testCases != null ? testCases : new ArrayList<>();
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public void addTestCase(TestCase testCase) {
        if (testCases == null) testCases = new ArrayList<>();
        testCases.add(testCase);
        testCase.setProblem(this);
    }

    // Builder
    public static ProblemBuilder builder() { return new ProblemBuilder(); }

    public static class ProblemBuilder {
        private Long id;
        private String title;
        private String slug;
        private String description;
        private Difficulty difficulty;
        private String starterCodeJava;
        private Integer timeLimitSeconds = 900;
        private Integer memoryLimitMb = 128;
        private List<TestCase> testCases = new ArrayList<>();
        private LocalDateTime createdAt = LocalDateTime.now();

        public ProblemBuilder id(Long id) { this.id = id; return this; }
        public ProblemBuilder title(String title) { this.title = title; return this; }
        public ProblemBuilder slug(String slug) { this.slug = slug; return this; }
        public ProblemBuilder description(String description) { this.description = description; return this; }
        public ProblemBuilder difficulty(Difficulty difficulty) { this.difficulty = difficulty; return this; }
        public ProblemBuilder starterCodeJava(String starterCodeJava) { this.starterCodeJava = starterCodeJava; return this; }
        public ProblemBuilder timeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; return this; }
        public ProblemBuilder memoryLimitMb(Integer memoryLimitMb) { this.memoryLimitMb = memoryLimitMb; return this; }
        public ProblemBuilder testCases(List<TestCase> testCases) { this.testCases = testCases; return this; }
        public ProblemBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Problem build() {
            return new Problem(id, title, slug, description, difficulty, starterCodeJava, timeLimitSeconds, memoryLimitMb, testCases, createdAt);
        }
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
    public String getStarterCodeJava() { return starterCodeJava; }
    public void setStarterCodeJava(String starterCodeJava) { this.starterCodeJava = starterCodeJava; }
    public Integer getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }
    public Integer getMemoryLimitMb() { return memoryLimitMb; }
    public void setMemoryLimitMb(Integer memoryLimitMb) { this.memoryLimitMb = memoryLimitMb; }
    public List<TestCase> getTestCases() { return testCases; }
    public void setTestCases(List<TestCase> testCases) { this.testCases = testCases; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
