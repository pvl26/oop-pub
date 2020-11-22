package Video;

import fileio.MovieInputData;

/**
 * Information about a movie, retrieved from MovieInputData
 * <p>
 */
public class Movie extends Show {
    /**
     * Duration in minutes of a movie
     */
    private final int duration;

    public Movie (MovieInputData movieInput) {
        super(movieInput.getTitle(), movieInput.getYear(), movieInput.getCast(), movieInput.getGenres());
        this.duration = movieInput.getDuration();
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                super.toString() +
                "duration=" + duration +
                '}';
    }
}
