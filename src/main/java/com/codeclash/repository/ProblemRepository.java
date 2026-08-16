package com.codeclash.repository;

import com.codeclash.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findBySlug(String slug);
    List<Problem> findByDifficulty(Problem.Difficulty difficulty);
}
