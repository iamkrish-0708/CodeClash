package com.codeclash.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    @JsonBackReference
    private Problem problem;

    @Column(name = "input_data", columnDefinition = "TEXT", nullable = false)
    private String inputData;

    @Column(name = "expected_output", columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden = false;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 0;

    public TestCase() {}

    public TestCase(Long id, Problem problem, String inputData, String expectedOutput, Boolean isHidden, Integer orderIndex) {
        this.id = id;
        this.problem = problem;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
        this.isHidden = isHidden != null ? isHidden : false;
        this.orderIndex = orderIndex != null ? orderIndex : 0;
    }

    public static TestCaseBuilder builder() { return new TestCaseBuilder(); }

    public static class TestCaseBuilder {
        private Long id;
        private Problem problem;
        private String inputData;
        private String expectedOutput;
        private Boolean isHidden = false;
        private Integer orderIndex = 0;

        public TestCaseBuilder id(Long id) { this.id = id; return this; }
        public TestCaseBuilder problem(Problem problem) { this.problem = problem; return this; }
        public TestCaseBuilder inputData(String inputData) { this.inputData = inputData; return this; }
        public TestCaseBuilder expectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; return this; }
        public TestCaseBuilder isHidden(Boolean isHidden) { this.isHidden = isHidden; return this; }
        public TestCaseBuilder orderIndex(Integer orderIndex) { this.orderIndex = orderIndex; return this; }

        public TestCase build() {
            return new TestCase(id, problem, inputData, expectedOutput, isHidden, orderIndex);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }
    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }
    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }
    public Boolean getIsHidden() { return isHidden; }
    public void setIsHidden(Boolean isHidden) { this.isHidden = isHidden; }
    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}
