package com.codeclash.service;

import com.codeclash.dto.CodeExecutionRequest;
import com.codeclash.dto.ExecutionResult;
import com.codeclash.model.*;
import com.codeclash.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {

    private static final Logger log = LoggerFactory.getLogger(SubmissionService.class);

    private final CodeExecutionService executionService;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final MatchService matchService;

    public SubmissionService(CodeExecutionService executionService, ProblemRepository problemRepository, TestCaseRepository testCaseRepository, SubmissionRepository submissionRepository, UserRepository userRepository, MatchRepository matchRepository, MatchService matchService) {
        this.executionService = executionService;
        this.problemRepository = problemRepository;
        this.testCaseRepository = testCaseRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
        this.matchService = matchService;
    }

    public ExecutionResult runSample(CodeExecutionRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("Problem not found with ID: " + request.getProblemId()));

        List<TestCase> sampleTestCases = testCaseRepository.findByProblemIdAndIsHiddenFalseOrderByOrderIndexAsc(problem.getId());
        return executionService.execute(request.getCode(), sampleTestCases, problem.getTimeLimitSeconds());
    }

    @Transactional
    public ExecutionResult submit(CodeExecutionRequest request, Long userId) {
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("Problem not found with ID: " + request.getProblemId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        List<TestCase> allTestCases = testCaseRepository.findByProblemIdOrderByOrderIndexAsc(problem.getId());
        ExecutionResult result = executionService.execute(request.getCode(), allTestCases, problem.getTimeLimitSeconds());

        Match match = null;
        if (request.getMatchId() != null) {
            match = matchRepository.findById(request.getMatchId()).orElse(null);
        }

        Submission.SubmissionStatus status = mapStatus(result.getStatus());

        Submission submission = Submission.builder()
                .problem(problem)
                .user(user)
                .match(match)
                .code(request.getCode())
                .language("JAVA")
                .status(status)
                .passedTestCases(result.getPassedTestCases())
                .totalTestCases(result.getTotalTestCases())
                .executionTimeMs(result.getExecutionTimeMs())
                .compileOutput(result.getCompileOutput())
                .submittedAt(LocalDateTime.now())
                .build();

        submissionRepository.save(submission);

        if (request.getMatchId() != null) {
            matchService.recordSubmission(
                    request.getMatchId(),
                    userId,
                    result.getPassedTestCases(),
                    result.getTotalTestCases(),
                    result.isSuccess()
            );
        }

        return result;
    }

    private Submission.SubmissionStatus mapStatus(String raw) {
        if (raw == null) return Submission.SubmissionStatus.RUNTIME_ERROR;
        switch (raw) {
            case "ACCEPTED": return Submission.SubmissionStatus.ACCEPTED;
            case "WRONG_ANSWER": return Submission.SubmissionStatus.WRONG_ANSWER;
            case "TIME_LIMIT_EXCEEDED": return Submission.SubmissionStatus.TIME_LIMIT_EXCEEDED;
            case "COMPILATION_ERROR": return Submission.SubmissionStatus.COMPILATION_ERROR;
            default: return Submission.SubmissionStatus.RUNTIME_ERROR;
        }
    }
}
