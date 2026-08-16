package com.codeclash.repository;

import com.codeclash.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findByRoomId(Long roomId);
    List<Match> findByStatus(Match.MatchStatus status);
}
