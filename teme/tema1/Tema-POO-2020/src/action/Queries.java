package action;

import actor.ActorsAwards;
import entertainment.Genre;
import net.sf.json.processors.JsDateJsonBeanProcessor;
import org.json.JSONObject;
import user.User;
import utils.Utils;
import video.Movie;
import video.Serial;

import java.lang.reflect.Array;
import java.util.*;

/**
 * The class contains static methods for different queries.
 *
 */
public final class Queries {
    /**
     * for coding style
     */
    private Queries() {
    }
    /**
     * Construct JSONObject based on list parameter
     * @param number of objects the query is made for
     * @param sortingType for list inside JSONObject
     * @param id of action
     * @param topList list of Map.Entries<String, Integer>
     * @param topDoubleList list of Map.Entries<String, Double>
     * @return JSONObject
     */
    private static JSONObject getJsonObject(int number, String sortingType, int id, Map<String, Integer> topList, Map<String, Double> topDoubleList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (topDoubleList == null) {
            List<Map.Entry<String, Integer>> toSort = new ArrayList<>(topList.entrySet());
            if (sortingType.equals("asc")) {
                toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
            } else {
                toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
                toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            }
            ArrayList<String> _topList = new ArrayList<>();
            for (int i = 0; i < number && i < toSort.size(); i++) {
                _topList.add(toSort.get(i).getKey());
            }
            jsonObject.put("message", "Query result: " + _topList.toString());
        } else {
            List<Map.Entry<String, Double>> toSort = new ArrayList<>(topDoubleList.entrySet());
            if (sortingType.equals("asc")) {
                toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
            } else {
                toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
                toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            }
            ArrayList<String> _topList = new ArrayList<>();
            for (int i = 0; i < number && i < toSort.size(); i++) {
                _topList.add(toSort.get(i).getKey());
            }
            jsonObject.put("message", "Query result: " + _topList.toString());
        }
        return jsonObject;
    }

    /**
     *
     * @param id the id of the action
     * @param userList the list of users in the system
     * @param number the number of elements the query is for
     * @param sortingType the sorting type
     * @return a JSONObject
     */
    public static JSONObject userQuery(int id, ArrayList<User> userList, int number, String sortingType) {
        Map<String, Integer> topUserList = new TreeMap<>();
        userList.stream().filter(user -> user.getReviewsCount() > 0 && topUserList.computeIfPresent(user.getUsername(), (k, v) -> v + 1) == null).forEach(user -> topUserList.put(user.getUsername(), user.getReviewsCount()));

        return getJsonObject(number, sortingType, id, topUserList, null);
    }

    private static int getYear(String filter) {
        if (filter == null) {return -1;} else { return Integer.parseInt(filter); }
    }

    private static Genre getGenre(String filter) {
        if (filter == null) { return null; } else { return Utils.stringToGenre(filter); }
    }

    public static JSONObject mostViewedMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && movie.getViews() != 0 && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getViews()));

        return getJsonObject(number, sortingType, id, topMoviesList, null);
    }

    public static JSONObject mostViewedSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && serial.getViews() != 0 && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getViews()));

        return getJsonObject(number, sortingType, id, topSerialsList, null);
    }

    public static JSONObject longestMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters) {
        if (filters.get(1).get(0) != null && Utils.stringToGenre(filters.get(1).get(0)) == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("message", "Query result: []");
            return jsonObject;
        }

        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getDuration()));

        return getJsonObject(number, sortingType, id, topMoviesList, null);
    }

    public static JSONObject longestSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters) {
        if (filters.get(1).get(0) != null && Utils.stringToGenre(filters.get(1).get(0)) == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("message", "Query result: []");
            return jsonObject;
        }

        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getDuration()));

        return getJsonObject(number, sortingType, id, topSerialsList, null);
    }

    public static JSONObject bestRatedMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Double> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && movie.getGrade() != 0 && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getGrade()));

        return getJsonObject(number, sortingType, id, null, topMoviesList);
    }

    public static JSONObject bestRatedSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Double> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && serial.getGrade() != 0 && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getGrade()));

        return getJsonObject(number, sortingType, id, null, topSerialsList);
    }

    public static JSONObject favoriteMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters, ArrayList<User> userList) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> favoriteList = new HashMap<>();
        userList.forEach(user -> user.getFavoriteMovies().forEach(video -> favoriteList.put(video, 1)));

        Map<String, Integer> topFavoriteMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && favoriteList.containsKey(movie.getTitle()) && topFavoriteMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topFavoriteMoviesList.put(movie.getTitle(), favoriteList.get(movie.getTitle())));

        return getJsonObject(number, sortingType, id, topFavoriteMoviesList, null);
    }

    public static JSONObject favoriteSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters, ArrayList<User> userList) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> favoriteList = new HashMap<>();
        userList.forEach(user -> user.getFavoriteMovies().forEach(video -> favoriteList.put(video, 1)));

        Map<String, Integer> topFavoriteSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && favoriteList.containsKey(serial.getTitle()) && topFavoriteSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topFavoriteSerialsList.put(serial.getTitle(), favoriteList.get(serial.getTitle())));

        return getJsonObject(number, sortingType, id, topFavoriteSerialsList, null);
    }

//    public static JSONObject averageActorQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters, ArrayList<User> userList) {
////        ArrayList<String> words = (ArrayList<String>) filters.get(2);
////        ArrayList<ActorsAwards> awards = new ArrayList<>();
////        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));
//    }
}
