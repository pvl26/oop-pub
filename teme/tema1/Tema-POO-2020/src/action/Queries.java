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
     *
     * @param id the id of the action
     * @param userList the list of users in the system
     * @param number the number of elements the query is for
     * @param sortingType the sorting type
     * @return a JSONObject
     */
    public static JSONObject userQuery(int id, ArrayList<User> userList, int number, String sortingType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        Map<String, Integer> topUserList = new TreeMap<>();
        userList.stream().filter(user -> user.getReviewsCount() > 0 && topUserList.computeIfPresent(user.getUsername(), (k, v) -> v + 1) == null).forEach(user -> topUserList.put(user.getUsername(), user.getReviewsCount()));

        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(topUserList.entrySet());
        if (sortingType.equals("asc")) {
            toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
        } else {
            toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
            toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        }
        ArrayList<String> topList = new ArrayList<>();
        for (int i = 0; i < number && i < toSort.size(); i++) {
            topList.add(toSort.get(i).getKey());
        }
        jsonObject.put("message", "Query result: " + topList.toString());
        return jsonObject;
    }

    public static JSONObject mostViewedMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && movie.getViews() != 0 && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getViews()));

        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(topMoviesList.entrySet());
        if (sortingType.equals("asc")) {
            toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
        } else {
            toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
            toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        }
        ArrayList<String> topList = new ArrayList<>();
        for (int i = 0; i < number && i < toSort.size(); i++) {
            topList.add(toSort.get(i).getKey());
        }

//        System.out.println("MOVIES    ------------" + topList.toString());
        jsonObject.put("message", "Query result: " + topList.toString());

        return jsonObject;
    }

    public static JSONObject mostViewedSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && serial.getViews() != 0 && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topSerialsList.put(movie.getTitle(), movie.getViews()));

        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(topSerialsList.entrySet());
        if (sortingType.equals("asc")) {
            toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
        } else {
            toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
            toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        }
        ArrayList<String> topList = new ArrayList<>();
        for (int i = 0; i < number && i < toSort.size(); i++) {
            topList.add(toSort.get(i).getKey());
        }

//        System.out.println("SERIALS    ------------" + topList.toString());
        jsonObject.put("message", "Query result: " + topList.toString());

        return jsonObject;
    }
}
