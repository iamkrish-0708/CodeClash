package com.codeclash.repository;

import com.codeclash.model.MatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {
    List<MatchPlayer> findByMatchId(Long matchId);
    Optional<MatchPlayer> findByMatchIdAndUserId(Long matchId, Long userId);

    @Query("SELECT mp FROM MatchPlayer mp WHERE mp.user.id = :userId ORDER BY mp.match.startedAt DESC")
    List<MatchPlayer> findRecentMatchesByUserId(@Param("userId") Long userId);
}
