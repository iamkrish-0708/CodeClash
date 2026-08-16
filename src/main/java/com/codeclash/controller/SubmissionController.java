package com.codeclash.controller;

import com.codeclash.dto.CodeExecutionRequest;
import com.codeclash.dto.ExecutionResult;
import com.codeclash.service.SubmissionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/run-sample")
    public ResponseEntity<ExecutionResult> runSampleTestCases(@Valid @RequestBody CodeExecutionRequest request) {
        ExecutionResult result = submissionService.runSample(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitSolution(@Valid @RequestBody CodeExecutionRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body("{\"error\": \"Please login to submit solutions\"}");
        }

        ExecutionResult result = submissionService.submit(request, userId);
        return ResponseEntity.ok(result);
    }
}
