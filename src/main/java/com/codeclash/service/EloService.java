package com.codeclash.service;

import org.springframework.stereotype.Service;

@Service
public class EloService {

    private static final int K_FACTOR = 32;

    /**
     * Calculates new Elo ratings for Player A and Player B after a match.
     *
     * @param ratingA Player A's current rating
     * @param ratingB Player B's current rating
     * @param scoreA  1.0 for Win, 0.5 for Draw, 0.0 for Loss
     * @return EloResult containing new ratings and delta changes
     */
    public EloResult calculateNewRatings(int ratingA, int ratingB, double scoreA) {
        double expectedA = 1.0 / (1.0 + Math.pow(10.0, (ratingB - ratingA) / 400.0));
        double expectedB = 1.0 - expectedA;

        double scoreB = 1.0 - scoreA;

        int changeA = (int) Math.round(K_FACTOR * (scoreA - expectedA));
        int changeB = (int) Math.round(K_FACTOR * (scoreB - expectedB));

        int newRatingA = Math.max(100, ratingA + changeA);
        int newRatingB = Math.max(100, ratingB + changeB);

        return new EloResult(newRatingA, newRatingB, changeA, changeB);
    }

    public record EloResult(int newRatingA, int newRatingB, int changeA, int changeB) {}
}
