package com.codeclash.service;

import com.codeclash.model.Problem;
import com.codeclash.model.TestCase;
import com.codeclash.repository.ProblemRepository;
import com.codeclash.repository.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    public ProblemService(ProblemRepository problemRepository, TestCaseRepository testCaseRepository) {
        this.problemRepository = problemRepository;
        this.testCaseRepository = testCaseRepository;
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Optional<Problem> getProblemById(Long id) {
        return problemRepository.findById(id);
    }

    public Optional<Problem> getProblemBySlug(String slug) {
        return problemRepository.findBySlug(slug);
    }

    public List<TestCase> getSampleTestCases(Long problemId) {
        return testCaseRepository.findByProblemIdAndIsHiddenFalseOrderByOrderIndexAsc(problemId);
    }

    public List<TestCase> getAllTestCasesForGrading(Long problemId) {
        return testCaseRepository.findByProblemIdOrderByOrderIndexAsc(problemId);
    }
}
