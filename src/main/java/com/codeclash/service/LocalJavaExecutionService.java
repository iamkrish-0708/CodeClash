package com.codeclash.service;

import com.codeclash.dto.ExecutionResult;
import com.codeclash.model.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LocalJavaExecutionService implements CodeExecutionService {

    private static final Logger log = LoggerFactory.getLogger(LocalJavaExecutionService.class);
    private static final int PER_TEST_CASE_TIMEOUT_MS = 3000;
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("public\\s+class\\s+([A-Za-z0-9_]+)");

    @Override
    public ExecutionResult execute(String code, List<TestCase> testCases, int timeLimitSeconds) {
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("codeclash_exec_");

            String className = "Solution";
            Matcher matcher = CLASS_NAME_PATTERN.matcher(code);
            if (matcher.find()) {
                className = matcher.group(1);
            }

            Path javaFile = tempDir.resolve(className + ".java");
            Files.writeString(javaFile, code, StandardCharsets.UTF_8);

            // Step 1: Compile with javac
            ProcessBuilder compilePb = new ProcessBuilder("javac", javaFile.getFileName().toString());
            compilePb.directory(tempDir.toFile());
            compilePb.redirectErrorStream(true);

            Process compileProcess = compilePb.start();
            String compileOutput = readProcessOutput(compileProcess);
            boolean compiled = compileProcess.waitFor(10, TimeUnit.SECONDS);

            if (!compiled || compileProcess.exitValue() != 0) {
                return ExecutionResult.builder()
                        .status("COMPILATION_ERROR")
                        .success(false)
                        .passedTestCases(0)
                        .totalTestCases(testCases.size())
                        .compileOutput(cleanCompilerOutput(compileOutput, javaFile.getFileName().toString()))
                        .testCaseResults(new ArrayList<>())
                        .build();
            }

            // Step 2: Execute against test cases
            List<ExecutionResult.TestCaseResult> results = new ArrayList<>();
            int passedCount = 0;
            long totalTimeMs = 0;
            String overallStatus = "ACCEPTED";

            for (int i = 0; i < testCases.size(); i++) {
                TestCase tc = testCases.get(i);
                long startTime = System.currentTimeMillis();

                ProcessBuilder runPb = new ProcessBuilder("java", "-Xmx128m", className);
                runPb.directory(tempDir.toFile());
                Process runProcess = runPb.start();

                try (OutputStream os = runProcess.getOutputStream()) {
                    if (tc.getInputData() != null) {
                        os.write(tc.getInputData().getBytes(StandardCharsets.UTF_8));
                        os.flush();
                    }
                }

                boolean finished = runProcess.waitFor(PER_TEST_CASE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                long elapsed = System.currentTimeMillis() - startTime;
                totalTimeMs += elapsed;

                if (!finished) {
                    runProcess.destroyForcibly();
                    overallStatus = "TIME_LIMIT_EXCEEDED";
                    results.add(ExecutionResult.TestCaseResult.builder()
                            .testCaseIndex(i + 1)
                            .passed(false)
                            .input(tc.getIsHidden() ? "[Hidden Test Case]" : tc.getInputData())
                            .expectedOutput(tc.getIsHidden() ? "[Hidden]" : tc.getExpectedOutput())
                            .actualOutput("Time Limit Exceeded (> 3000ms)")
                            .error("Time Limit Exceeded")
                            .isHidden(tc.getIsHidden())
                            .build());
                    break;
                }

                String actualOutput = readProcessOutput(runProcess).trim();
                String expectedOutput = tc.getExpectedOutput() != null ? tc.getExpectedOutput().trim() : "";

                if (runProcess.exitValue() != 0) {
                    overallStatus = "RUNTIME_ERROR";
                    results.add(ExecutionResult.TestCaseResult.builder()
                            .testCaseIndex(i + 1)
                            .passed(false)
                            .input(tc.getIsHidden() ? "[Hidden Test Case]" : tc.getInputData())
                            .expectedOutput(tc.getIsHidden() ? "[Hidden]" : expectedOutput)
                            .actualOutput(actualOutput)
                            .error("Runtime Error (Exit code: " + runProcess.exitValue() + ")")
                            .isHidden(tc.getIsHidden())
                            .build());
                    break;
                }

                boolean passed = normalizeOutput(actualOutput).equals(normalizeOutput(expectedOutput));
                if (passed) {
                    passedCount++;
                } else if (overallStatus.equals("ACCEPTED")) {
                    overallStatus = "WRONG_ANSWER";
                }

                results.add(ExecutionResult.TestCaseResult.builder()
                        .testCaseIndex(i + 1)
                        .passed(passed)
                        .input(tc.getIsHidden() ? "[Hidden Test Case]" : tc.getInputData())
                        .expectedOutput(tc.getIsHidden() ? "[Hidden]" : expectedOutput)
                        .actualOutput(tc.getIsHidden() && !passed ? "[Hidden Output]" : actualOutput)
                        .isHidden(tc.getIsHidden())
                        .build());
            }

            return ExecutionResult.builder()
                    .status(overallStatus)
                    .success("ACCEPTED".equals(overallStatus))
                    .passedTestCases(passedCount)
                    .totalTestCases(testCases.size())
                    .executionTimeMs(totalTimeMs)
                    .compileOutput("Compilation Succeeded.")
                    .testCaseResults(results)
                    .build();

        } catch (Exception e) {
            log.error("Execution error: ", e);
            return ExecutionResult.builder()
                    .status("RUNTIME_ERROR")
                    .success(false)
                    .passedTestCases(0)
                    .totalTestCases(testCases.size())
                    .compileOutput("Internal error: " + e.getMessage())
                    .testCaseResults(new ArrayList<>())
                    .build();
        } finally {
            if (tempDir != null) {
                cleanupTempDir(tempDir);
            }
        }
    }

    private String readProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }

    private String normalizeOutput(String s) {
        if (s == null) return "";
        return s.replace("\r\n", "\n").replaceAll("\\s+$", "").trim();
    }

    private String cleanCompilerOutput(String raw, String fileName) {
        return raw.replace(fileName + ":", "Line ");
    }

    private void cleanupTempDir(Path tempDir) {
        try {
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (Exception ignored) {
        }
    }
}
