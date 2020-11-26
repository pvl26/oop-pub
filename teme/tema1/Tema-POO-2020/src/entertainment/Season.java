package entertainment;

import java.util.*;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;
    /**
     * List of users that rated the season
     */
    private ArrayList<String> usersRating;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.usersRating = new ArrayList<>();
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void addRatings(String username, Double rating) {
        this.addUsersRating(username);
        this.ratings.add(rating);
    }

    public ArrayList<String> getUserRatings() {
        return this.usersRating;
    }

    public void addUsersRating(String username) {
        this.usersRating.add(username);
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

