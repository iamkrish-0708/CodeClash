package com.codeclash.service;

import com.codeclash.dto.CodeExecutionRequest;
import com.codeclash.dto.ExecutionResult;
import com.codeclash.model.*;
import com.codeclash.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmissionService {

    private final CodeExecutionService executionService;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

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
