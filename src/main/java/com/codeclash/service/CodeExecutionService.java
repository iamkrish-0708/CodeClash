package com.codeclash.service;

import com.codeclash.dto.ExecutionResult;
import com.codeclash.model.TestCase;

import java.util.List;

public interface CodeExecutionService {
    ExecutionResult execute(String code, List<TestCase> testCases, int timeLimitSeconds);
}
