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
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && serial.getViews() != 0 && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getViews()));

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
        jsonObject.put("message", "Query result: " + topList.toString());

        return jsonObject;
    }

    public static JSONObject longestMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (filters.get(1).get(0) != null && Utils.stringToGenre(filters.get(1).get(0)) == null) {
            jsonObject.put("message", "Query result: []");
            return jsonObject;
        }

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getDuration()));

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
        jsonObject.put("message", "Query result: " + topList.toString());

        return jsonObject;
    }

    public static JSONObject longestSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (filters.get(1).get(0) != null && Utils.stringToGenre(filters.get(1).get(0)) == null) {
            jsonObject.put("message", "Query result: []");
            return jsonObject;
        }

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Integer> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getDuration()));

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
        jsonObject.put("message", "Query result: " + topList.toString());

        return jsonObject;
    }

    public static JSONObject bestRatedMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Double> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && movie.getGrade() != 0 && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getGrade()));

        List<Map.Entry<String, Double>> toSort = new ArrayList<>(topMoviesList.entrySet());
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

    public static JSONObject bestRatedSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }
//        ArrayList<String> words = (ArrayList<String>) filters.get(2);
//        ArrayList<ActorsAwards> awards = new ArrayList<>();
//        filters.get(3).forEach(award -> awards.add(Utils.stringToAwards(award)));

        Map<String, Double> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && serial.getGrade() != 0 && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getGrade()));

        List<Map.Entry<String, Double>> toSort = new ArrayList<>(topSerialsList.entrySet());
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

    public static JSONObject favoriteMoviesQuery(int id, ArrayList<Movie> movieList, int number, String sortingType, List<List<String>> filters, ArrayList<User> userList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }


        Map<String, Integer> favoriteList = new HashMap<>();
        userList.forEach(user -> user.getFavoriteMovies().forEach(video -> favoriteList.put(video, 1)));

        Map<String, Integer> topFavoriteMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre)) && (year == -1 || movie.getYear() == year) && favoriteList.containsKey(movie.getTitle()) && topFavoriteMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null).forEach(movie -> topFavoriteMoviesList.put(movie.getTitle(), favoriteList.get(movie.getTitle())));


        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(topFavoriteMoviesList.entrySet());
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

        System.out.println(toSort.toString());

        return jsonObject;
    }

    public static JSONObject favoriteSerialsQuery(int id, ArrayList<Serial> serialList, int number, String sortingType, List<List<String>> filters, ArrayList<User> userList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int year;
        if (filters.get(0).get(0) == null) {year = -1;} else { year = Integer.parseInt(filters.get(0).get(0)); }
        Genre genre;
        if (filters.get(1).get(0) == null) { genre = null; } else { genre = Utils.stringToGenre(filters.get(1).get(0)); }

        Map<String, Integer> favoriteList = new HashMap<>();
        userList.forEach(user -> user.getFavoriteMovies().forEach(video -> favoriteList.put(video, 1)));

        Map<String, Integer> topFavoriteSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre)) && (year == -1 || serial.getYear() == year) && favoriteList.containsKey(serial.getTitle()) && topFavoriteSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null).forEach(serial -> topFavoriteSerialsList.put(serial.getTitle(), favoriteList.get(serial.getTitle())));


        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(topFavoriteSerialsList.entrySet());
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

        System.out.println(toSort.toString());

        return jsonObject;
    }
}
