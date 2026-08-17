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
import java.util.Optional;

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
        String twoSumBoilerplate = 
            "import java.util.*;\n\n" +
            "public class Solution {\n" +
            "    public static void main(String[] args) {\n" +
            "        Scanner sc = new Scanner(System.in);\n" +
            "        if (!sc.hasNextInt()) return;\n" +
            "        int n = sc.nextInt();\n" +
            "        int[] nums = new int[n];\n" +
            "        for (int i = 0; i < n; i++) {\n" +
            "            nums[i] = sc.nextInt();\n" +
            "        }\n" +
            "        int target = sc.nextInt();\n\n" +
            "        // TODO: Write your solution logic here\n" +
            "        // Print the two 0-based indices separated by a space (e.g. \"0 1\")\n" +
            "        \n" +
            "    }\n" +
            "}";

        String palindromeBoilerplate = 
            "import java.util.*;\n\n" +
            "public class Solution {\n" +
            "    public static void main(String[] args) {\n" +
            "        Scanner sc = new Scanner(System.in);\n" +
            "        if (!sc.hasNext()) return;\n" +
            "        String s = sc.next().trim();\n\n" +
            "        // TODO: Write your solution logic here\n" +
            "        // Print \"true\" if s is a palindrome, or \"false\" otherwise\n" +
            "        \n" +
            "    }\n" +
            "}";

        String parenthesesBoilerplate = 
            "import java.util.*;\n\n" +
            "public class Solution {\n" +
            "    public static void main(String[] args) {\n" +
            "        Scanner sc = new Scanner(System.in);\n" +
            "        if (!sc.hasNext()) return;\n" +
            "        String s = sc.next().trim();\n\n" +
            "        // TODO: Write your solution logic here\n" +
            "        // Print \"true\" if the string parentheses are valid, or \"false\" otherwise\n" +
            "        \n" +
            "    }\n" +
            "}";

        if (problemRepository.count() > 0) {
            log.info("Updating existing problem starter codes to clean boilerplate templates...");
            Optional<Problem> p1 = problemRepository.findBySlug("two-sum");
            p1.ifPresent(p -> { p.setStarterCodeJava(twoSumBoilerplate); problemRepository.save(p); });

            Optional<Problem> p2 = problemRepository.findBySlug("palindrome-number");
            p2.ifPresent(p -> { p.setStarterCodeJava(palindromeBoilerplate); problemRepository.save(p); });

            Optional<Problem> p3 = problemRepository.findBySlug("valid-parentheses");
            p3.ifPresent(p -> { p.setStarterCodeJava(parenthesesBoilerplate); problemRepository.save(p); });

            log.info("Starter code templates updated successfully.");
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
                .starterCodeJava(twoSumBoilerplate)
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
                .starterCodeJava(palindromeBoilerplate)
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
                .starterCodeJava(parenthesesBoilerplate)
                .build();

        validParentheses.addTestCase(TestCase.builder().inputData("()[]{}").expectedOutput("true").isHidden(false).orderIndex(1).build());
        validParentheses.addTestCase(TestCase.builder().inputData("(]").expectedOutput("false").isHidden(false).orderIndex(2).build());
        validParentheses.addTestCase(TestCase.builder().inputData("([{}])").expectedOutput("true").isHidden(true).orderIndex(3).build());
        validParentheses.addTestCase(TestCase.builder().inputData("(((").expectedOutput("false").isHidden(true).orderIndex(4).build());

        problemRepository.saveAll(List.of(twoSum, palindrome, validParentheses));
        log.info("Initial problems seeded successfully with clean boilerplate.");
    }
}
