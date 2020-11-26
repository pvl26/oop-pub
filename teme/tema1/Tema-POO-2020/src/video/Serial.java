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

    public Serial(SerialInputData serialInput) {
        super(serialInput.getTitle(), serialInput.getYear(), serialInput.getCast(), serialInput.getGenres());
        this.numberOfSeasons = serialInput.getNumberSeason();
        this.seasons = serialInput.getSeasons();
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public Double getGrade() {
        double grade = 0.0;
        for (Season season : this.seasons) {
            Double seasonRating = 0.0;
            for (Double rating : season.getRatings()) {
                seasonRating += rating;
            }
            if (season.getRatings().size() == 0) { seasonRating = 0.0; } else {
                seasonRating /= season.getRatings().size();
            }
            grade += seasonRating;
        }
        grade /= this.seasons.size();
        return  grade;
    }

    public int getDuration() {
        int duration = 0;
        for (Season season : this.seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    @Override
    public String toString() {
        return "Serial{" +
                super.toString() +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                '}';
    }
}
