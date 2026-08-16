package com.codeclash.controller;

import com.codeclash.model.Problem;
import com.codeclash.model.TestCase;
import com.codeclash.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
        return problemService.getProblemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/sample-test-cases")
    public ResponseEntity<List<TestCase>> getSampleTestCases(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getSampleTestCases(id));
    }
}
