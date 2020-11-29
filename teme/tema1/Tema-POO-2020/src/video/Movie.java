package video;

import fileio.MovieInputData;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from MovieInputData
 * <p>
 */
public class Movie extends Show {
    /**
     * Duration in minutes of a movie
     */
    private final int duration;   /**
     * Movie grade
     */
    private ArrayList<Double> grades;
    /**
     * Movie grades count
     */
    private int gradeCount;
    /**
     * List of users that rated the movie
     */
    private ArrayList<String> ratedBy;

    public Movie(final MovieInputData movieInput) {
        super(movieInput.getTitle(), movieInput.getYear(),
                movieInput.getCast(), movieInput.getGenres());
        this.duration = movieInput.getDuration();
        this.grades = new ArrayList<>();
        this.gradeCount = 0;
        this.ratedBy = new ArrayList<>();
    }

    /**
     * Returns duration of a movie
     * @return duration as int
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Calculate rating of a movie
     * @return rating as double
     */
    public double getGrade() {
        if (this.grades.size() == 0) {
            return 0.0;
        }
        double finalGrade = this.grades.stream().mapToDouble(grade -> grade).sum();
        return finalGrade / this.grades.size();
    }

    /**
     * Adds a rating to grades array
     * @param grade is the provided rating
     */
    public void addGrade(final double grade) {
        this.grades.add(grade);
    }

    /**
     * Returns usernames that rated the movie
     * @return ratedBy, list of usernames
     */
    public ArrayList<String> getRatedBy() {
        return ratedBy;
    }

    /**
     * Add to list of usernames that rated the movie
     * @param user the username of the reviewer
     */
    public void addRatedBy(final String user) {
        this.ratedBy.add(user);
    }

    /**
     * toString method
     * @return string of movie fields
     */
    @Override
    public String toString() {
        return "Movie{"
                + super.toString()
                + "duration=" + duration
                + '}';
    }
}
