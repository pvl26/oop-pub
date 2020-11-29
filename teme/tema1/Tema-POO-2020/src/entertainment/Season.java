package entertainment;

import java.util.List;
import java.util.ArrayList;

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

    /**
     * Add rating to season
     * @param username user that added the rating
     * @param rating given by the user
     */
    public void addRatings(final String username, final Double rating) {
        this.addUsersRating(username);
        this.ratings.add(rating);
    }

    public ArrayList<String> getUserRatings() {
        return this.usersRating;
    }

    /**
     * Add user' name in list of rating authors
     * @param username of user
     */
    public void addUsersRating(final String username) {
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

