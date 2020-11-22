package Video;

import actor.ActorsAwards;
import entertainment.Genre;
import fileio.MovieInputData;
import utils.Utils;

import java.util.ArrayList;

/**
 * General information about show (video), retrieved from Movie or Serial class constructor
 * <p>
 */
public class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres in a list of Genre enums
     */
    private final ArrayList<Genre> genres = new ArrayList<>();

    public Show(String title, int year, ArrayList<String> cast, ArrayList<String> genres) {
        this.title = title;
        this.cast = cast;
        genres.forEach(genre -> this.genres.add(Utils.stringToGenre(genre)));
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", year=" + year +
                ", cast=" + cast +
                ", genres=" + genres +
                '}';
    }
}
