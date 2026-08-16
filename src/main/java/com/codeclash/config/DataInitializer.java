package com.codeclash.config;

import com.codeclash.model.Problem;
import com.codeclash.model.TestCase;
import com.codeclash.repository.ProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ProblemRepository problemRepository;

    public DataInitializer(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (problemRepository.count() > 0) {
            log.info("Problems already seeded in database.");
            return;
        }

        log.info("Seeding initial algorithmic problems and test cases...");

        // Problem 1: Two Sum
        Problem twoSum = Problem.builder()
                .title("Two Sum")
                .slug("two-sum")
                .difficulty(Problem.Difficulty.EASY)
                .timeLimitSeconds(900)
                .memoryLimitMb(128)
                .description("### Description\n" +
                        "Given an array of integers `nums` and an integer `target`, return the indices of the two numbers such that they add up to `target`.\n\n" +
                        "**Input Format:**\n" +
                        "First line contains integer `N`.\n" +
                        "Second line contains `N` space-separated integers representing `nums`.\n" +
                        "Third line contains single integer `target`.\n\n" +
                        "**Output Format:**\n" +
                        "Print the two 0-based indices separated by a space (e.g. `0 1`).")
                .starterCodeJava("import java.util.*;\n\npublic class Solution {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        if (!sc.hasNextInt()) return;\n        int n = sc.nextInt();\n        int[] nums = new int[n];\n        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();\n        int target = sc.nextInt();\n        \n        // Write your solution here\n        for (int i = 0; i < n; i++) {\n            for (int j = i + 1; j < n; j++) {\n                if (nums[i] + nums[j] == target) {\n                    System.out.println(i + \" \" + j);\n                    return;\n                }\n            }\n        }\n    }\n}")
                .build();

        twoSum.addTestCase(TestCase.builder().inputData("4\n2 7 11 15\n9").expectedOutput("0 1").isHidden(false).orderIndex(1).build());
        twoSum.addTestCase(TestCase.builder().inputData("3\n3 2 4\n6").expectedOutput("1 2").isHidden(false).orderIndex(2).build());
        twoSum.addTestCase(TestCase.builder().inputData("2\n3 3\n6").expectedOutput("0 1").isHidden(true).orderIndex(3).build());
        twoSum.addTestCase(TestCase.builder().inputData("5\n1 5 8 10 14\n18").expectedOutput("2 3").isHidden(true).orderIndex(4).build());

        // Problem 2: Palindrome Number
        Problem palindrome = Problem.builder()
                .title("Palindrome Number")
                .slug("palindrome-number")
                .difficulty(Problem.Difficulty.EASY)
                .timeLimitSeconds(600)
                .memoryLimitMb(128)
                .description("### Description\n" +
                        "Given an integer `x`, print `true` if `x` is a palindrome, and `false` otherwise.\n\n" +
                        "**Input Format:**\n" +
                        "A single integer `x`.\n\n" +
                        "**Output Format:**\n" +
                        "`true` or `false`")
                .starterCodeJava("import java.util.*;\n\npublic class Solution {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        if (!sc.hasNext()) return;\n        String s = sc.next().trim();\n        \n        // Write your solution here\n        String rev = new StringBuilder(s).reverse().toString();\n        System.out.println(s.equals(rev) ? \"true\" : \"false\");\n    }\n}")
                .build();

        palindrome.addTestCase(TestCase.builder().inputData("121").expectedOutput("true").isHidden(false).orderIndex(1).build());
        palindrome.addTestCase(TestCase.builder().inputData("-121").expectedOutput("false").isHidden(false).orderIndex(2).build());
        palindrome.addTestCase(TestCase.builder().inputData("10").expectedOutput("false").isHidden(true).orderIndex(3).build());
        palindrome.addTestCase(TestCase.builder().inputData("1234321").expectedOutput("true").isHidden(true).orderIndex(4).build());

        // Problem 3: Valid Parentheses
        Problem validParentheses = Problem.builder()
                .title("Valid Parentheses")
                .slug("valid-parentheses")
                .difficulty(Problem.Difficulty.MEDIUM)
                .timeLimitSeconds(900)
                .memoryLimitMb(128)
                .description("### Description\n" +
                        "Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.\n\n" +
                        "**Input Format:**\n" +
                        "A single string `s`.\n\n" +
                        "**Output Format:**\n" +
                        "`true` or `false`")
                .starterCodeJava("import java.util.*;\n\npublic class Solution {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        if (!sc.hasNext()) return;\n        String s = sc.next().trim();\n        \n        // Write your solution here\n        Stack<Character> stack = new Stack<>();\n        boolean valid = true;\n        for (char c : s.toCharArray()) {\n            if (c == '(' || c == '{' || c == '[') stack.push(c);\n            else {\n                if (stack.isEmpty()) { valid = false; break; }\n                char top = stack.pop();\n                if (c == ')' && top != '(') { valid = false; break; }\n                if (c == '}' && top != '{') { valid = false; break; }\n                if (c == ']' && top != '[') { valid = false; break; }\n            }\n        }\n        if (!stack.isEmpty()) valid = false;\n        System.out.println(valid ? \"true\" : \"false\");\n    }\n}")
                .build();

        validParentheses.addTestCase(TestCase.builder().inputData("()[]{}").expectedOutput("true").isHidden(false).orderIndex(1).build());
        validParentheses.addTestCase(TestCase.builder().inputData("(]").expectedOutput("false").isHidden(false).orderIndex(2).build());
        validParentheses.addTestCase(TestCase.builder().inputData("([{}])").expectedOutput("true").isHidden(true).orderIndex(3).build());
        validParentheses.addTestCase(TestCase.builder().inputData("(((").expectedOutput("false").isHidden(true).orderIndex(4).build());

        problemRepository.saveAll(List.of(twoSum, palindrome, validParentheses));
        log.info("Initial problems seeded successfully.");
    }
}
