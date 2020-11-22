package Video;

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

    @Override
    public String toString() {
        return "Serial{" +
                super.toString() +
                "numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                '}';
    }
}
