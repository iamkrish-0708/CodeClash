package com.codeclash.repository;

import com.codeclash.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByMatchIdOrderBySubmittedAtDesc(Long matchId);
    List<Submission> findByUserIdOrderBySubmittedAtDesc(Long userId);
    List<Submission> findByMatchIdAndUserIdOrderBySubmittedAtDesc(Long matchId, Long userId);
}
