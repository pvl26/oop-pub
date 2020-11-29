package video;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;
/**
 * Information about a tv show, retrieved from ShowInputData
 * <p>
 */
public class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public Serial(final SerialInputData serialInput) {
        super(serialInput.getTitle(), serialInput.getYear(),
                serialInput.getCast(), serialInput.getGenres());
        this.numberOfSeasons = serialInput.getNumberSeason();
        this.seasons = serialInput.getSeasons();
    }

    /**
     * Get number of seasons of the series
     * @return number of seasons
     */
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     * Get list of Season Objects
     * @return list of seasons for the series
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * Get the rating of the series as a Double
     * @return rating of the series
     */
    public Double getGrade() {
        double grade = 0.0;
        for (Season season : this.seasons) {
            Double seasonRating = 0.0;
            for (Double rating : season.getRatings()) {
                seasonRating += rating;
            }
            if (season.getRatings().size() == 0) {
                seasonRating = 0.0;
            } else {
                seasonRating /= season.getRatings().size();
            }
            grade += seasonRating;
        }
        grade /= this.seasons.size();
        return  grade;
    }

    /**
     * Get the duration of the series in minutes as int
     * @return duration of the series
     */
    public int getDuration() {
        int duration = 0;
        for (Season season : this.seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    /**
     * ToString method
     * @return String of serial's fields
     */
    @Override
    public String toString() {
        return "Serial{"
                + super.toString()
                + ", numberOfSeasons=" + numberOfSeasons
                + ", seasons=" + seasons
                + '}';
    }
}
