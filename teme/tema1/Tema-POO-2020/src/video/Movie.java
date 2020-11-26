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
    private double grade;
    /**
     * Movie grades count
     */
    private int gradeCount;
    /**
     * List of users that rated the movie
     */
    private ArrayList<String> ratedBy;

    public Movie (MovieInputData movieInput) {
        super(movieInput.getTitle(), movieInput.getYear(), movieInput.getCast(), movieInput.getGenres());
        this.duration = movieInput.getDuration();
        this.grade = 0;
        this.gradeCount = 0;
        this.ratedBy = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(int gradeCount) {
        this.gradeCount = gradeCount;
    }

    public ArrayList<String> getRatedBy() {
        return ratedBy;
    }

    public void addRatedBy(String user) {
        this.ratedBy.add(user);
    }

    @Override
    public String toString() {
        return "Movie{" +
                super.toString() +
                "duration=" + duration +
                '}';
    }
}
