package video;

import entertainment.Genre;
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
    /**
     * Number of views for video
     */
    private Integer views = 0;

    public Show(final String title, final int year, final ArrayList<String> cast,
                final ArrayList<String> genres) {
        this.title = title;
        this.cast = cast;
        genres.forEach(genre -> this.genres.add(Utils.stringToGenre(genre)));
        this.year = year;
    }

    /**
     * Get title of the show
     * @return the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get cast list of the show
     * @return the list of actors
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * Get year of release
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Get the list of genres for the show
     * @return genres list
     */
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    /**
     * Get the view count for the show
     * @return view count
     */
    public Integer getViews() {
        return views;
    }

    /**
     * Add one view to the show
     */
    public void addViews() {
        this.views += 1;
    }

    /**
     * ToString method
     * @return string of show fields
     */
    @Override
    public String toString() {
        return "title='" + title + '\''
                + ", year=" + year
                + ", cast=" + cast
                + ", genres=" + genres
                + '}';
    }
}
