package action;

import actor.Actor;
import actor.ActorsAwards;
import entertainment.Genre;
import org.json.JSONObject;
import user.User;
import utils.Utils;
import video.Movie;
import video.Serial;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    private static JSONObject getJsonObject(final int number, final String sortingType,
                                            final int id, final Map<String, Integer> topList,
                                            final Map<String, Double> topDoubleList) {
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
            ArrayList<String> auxTopList = new ArrayList<>();
            for (int i = 0; i < number && i < toSort.size(); i++) {
                auxTopList.add(toSort.get(i).getKey());
            }
            jsonObject.put("message", "Query result: " + auxTopList.toString());
        } else {
            List<Map.Entry<String, Double>> toSort = new ArrayList<>(topDoubleList.entrySet());
            if (sortingType.equals("asc")) {
                toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
            } else {
                toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
                toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            }
            ArrayList<String> auxTopList = new ArrayList<>();
            for (int i = 0; i < number && i < toSort.size(); i++) {
                auxTopList.add(toSort.get(i).getKey());
            }
            jsonObject.put("message", "Query result: " + auxTopList.toString());
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
    public static JSONObject userQuery(final int id, final ArrayList<User> userList,
                                       final int number, final String sortingType) {
        Map<String, Integer> topUserList = new TreeMap<>();
        userList.stream().filter(user -> user.getReviewsCount() > 0
                && topUserList.computeIfPresent(user.getUsername(), (k, v) -> v + 1) == null)
                .forEach(user -> topUserList.put(user.getUsername(), user.getReviewsCount()));

        return getJsonObject(number, sortingType, id, topUserList, null);
    }

    /**
     * Extract year from filter year
     * @param filter year as string format
     * @return year as int
     */
    private static int getYear(final String filter) {
        if (filter == null) {
            return -1;
        } else {
            return Integer.parseInt(filter);
        }
    }

    /**
     * Extract genre form filter
     * @param filter genre as string
     * @return genre as Genre
     */
    private static Genre getGenre(final String filter) {
        if (filter == null) {
            return null;
        } else {
            return Utils.stringToGenre(filter);
        }
    }

    /**
     * Extract CharSequence list form List of words
     * @param wordsList list of words as Strings
     * @return CharSequence[]
     */
    private static CharSequence[] getWords(final List<String> wordsList) {
        if (wordsList == null) {
            return null;
        } else {
            return wordsList.toArray(new CharSequence[0]);
        }
    }

    /**
     * Convert awards list from Strings to ActorAwards
     * @param awardsList list of awards
     * @return list of ActorAwards
     */
    private static ArrayList<ActorsAwards> getAwards(final ArrayList<String> awardsList) {
        if (awardsList == null) {
            return null;
        } else {
            ArrayList<ActorsAwards> awards = new ArrayList<>();
            awardsList.forEach(award -> awards.add(Utils.stringToAwards(award)));
            return awards;
        }
    }

    /**
     * Query the movies based on their views count
     * @param id action id
     * @param movieList list of movies in the system
     * @param number number of movies for the query
     * @param sortingType sorting type, ascendant and descendant
     * @param filters filters used for the query
     * @return JSONObject containing query result
     */
    public static JSONObject mostViewedMoviesQuery(final int id, final ArrayList<Movie> movieList,
                                                   final int number, final String sortingType,
                                                   final List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre))
                && (year == -1 || movie.getYear() == year) && movie.getViews() != 0
                && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null)
                .forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getViews()));

        return getJsonObject(number, sortingType, id, topMoviesList, null);
    }

    /**
     * Query the shows based on their views count
     * @param id action id
     * @param serialList list of shows in the system
     * @param number number of shows for the query
     * @param sortingType sorting type, ascendant and descendant
     * @param filters filters used for the query
     * @return JSONObject containing query result
     */
    public static JSONObject mostViewedSerialsQuery(final int id,
                                                    final ArrayList<Serial> serialList,
                                                    final int number, final String sortingType,
                                                    final List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre))
                && (year == -1 || serial.getYear() == year) && serial.getViews() != 0
                && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null)
                .forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getViews()));

        return getJsonObject(number, sortingType, id, topSerialsList, null);
    }

    /**
     * Query for the lengthiest movies
     * @param id action id
     * @param movieList list of movies
     * @param number number of movies
     * @param sortingType sorting type
     * @param filters used for query
     * @return JSONObject containing query result
     */
    public static JSONObject longestMoviesQuery(final int id, final ArrayList<Movie> movieList,
                                                final int number, final String sortingType,
                                                final List<List<String>> filters) {
        if (filters.get(1).get(0) != null && Utils.stringToGenre(filters.get(1).get(0)) == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("message", "Query result: []");
            return jsonObject;
        }

        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre))
                && (year == -1 || movie.getYear() == year)
                && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null)
                .forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getDuration()));

        return getJsonObject(number, sortingType, id, topMoviesList, null);
    }

    /**
     * Query for the lengthiest series
     * @param id action id
     * @param serialList list of shows
     * @param number number of shows
     * @param sortingType sorting type
     * @param filters used for query
     * @return JSONObject containing query result
     */
    public static JSONObject longestSerialsQuery(final int id, final ArrayList<Serial> serialList,
                                                 final int number, final String sortingType,
                                                 final List<List<String>> filters) {
        if (filters.get(1).get(0) != null && Utils.stringToGenre(filters.get(1).get(0)) == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("message", "Query result: []");
            return jsonObject;
        }

        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre))
                && (year == -1 || serial.getYear() == year)
                && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null)
                .forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getDuration()));

        return getJsonObject(number, sortingType, id, topSerialsList, null);
    }

    /**
     * Query for the best rated movies
     * @param id of action
     * @param movieList list of movies
     * @param number of movies for query
     * @param sortingType for query
     * @param filters fpr query
     * @return JSONObject containing query result
     */
    public static JSONObject bestRatedMoviesQuery(final int id, final ArrayList<Movie> movieList,
                                                  final int number, final String sortingType,
                                                  final List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Double> topMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre))
                && (year == -1 || movie.getYear() == year)
                && movie.getGrade() != 0
                && topMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null)
                .forEach(movie -> topMoviesList.put(movie.getTitle(), movie.getGrade()));

        return getJsonObject(number, sortingType, id, null, topMoviesList);
    }

    /**
     * Query for the best rated shows
     * @param id of action
     * @param serialList list of shows
     * @param number of shows for query
     * @param sortingType for query
     * @param filters fpr query
     * @return JSONObject containing query result
     */
    public static JSONObject bestRatedSerialsQuery(final int id, final ArrayList<Serial> serialList,
                                                   final int number, final String sortingType,
                                                   final List<List<String>> filters) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Double> topSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre))
                && (year == -1 || serial.getYear() == year)
                && serial.getGrade() != 0
                && topSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null)
                .forEach(serial -> topSerialsList.put(serial.getTitle(), serial.getGrade()));

        return getJsonObject(number, sortingType, id, null, topSerialsList);
    }

    /**
     * Query for most favorite movies
     * @param id of action
     * @param movieList list of movies
     * @param number of movies for query
     * @param sortingType for query
     * @param filters for query
     * @param userList list of users in the system
     * @return JSONObject containing query result
     */
    public static JSONObject favoriteMoviesQuery(final int id, final ArrayList<Movie> movieList,
                                                 final int number, final String sortingType,
                                                 final List<List<String>> filters,
                                                 final ArrayList<User> userList) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> favoriteList = new HashMap<>();
        userList.forEach(user -> user.getFavoriteMovies().stream()
                .filter(movie -> favoriteList.computeIfPresent(movie, (k, v) -> v + 1) == null)
                .forEach(video -> favoriteList.put(video, 1)));

        Map<String, Integer> topFavoriteMoviesList = new TreeMap<>();
        movieList.stream().filter(movie -> (genre == null || movie.getGenres().contains(genre))
             && (year == -1 || movie.getYear() == year)
             && favoriteList.containsKey(movie.getTitle())
             && topFavoriteMoviesList.computeIfPresent(movie.getTitle(), (k, v) -> v + 1) == null)
             .forEach(movie ->
               topFavoriteMoviesList.put(movie.getTitle(), favoriteList.get(movie.getTitle())));

        return getJsonObject(number, sortingType, id, topFavoriteMoviesList, null);
    }


    /**
     * Query for most favorite shows
     * @param id of action
     * @param serialList list of shows
     * @param number of shows for query
     * @param sortingType for query
     * @param filters for query
     * @param userList list of users in the system
     * @return JSONObject containing query result
     */
    public static JSONObject favoriteSerialsQuery(final int id, final ArrayList<Serial> serialList,
                                                  final int number, final String sortingType,
                                                  final List<List<String>> filters,
                                                  final ArrayList<User> userList) {
        int year = getYear(filters.get(0).get(0));
        Genre genre = getGenre(filters.get(1).get(0));

        Map<String, Integer> favoriteList = new HashMap<>();
        userList.forEach(user -> user.getFavoriteMovies()
                .forEach(video -> favoriteList.put(video, 1)));

        Map<String, Integer> topFavoriteSerialsList = new TreeMap<>();
        serialList.stream().filter(serial -> (genre == null || serial.getGenres().contains(genre))
             && (year == -1 || serial.getYear() == year)
             && favoriteList.containsKey(serial.getTitle())
             && topFavoriteSerialsList.computeIfPresent(serial.getTitle(), (k, v) -> v + 1) == null)
             .forEach(serial ->
               topFavoriteSerialsList.put(serial.getTitle(), favoriteList.get(serial.getTitle())));

        return getJsonObject(number, sortingType, id, topFavoriteSerialsList, null);
    }

    /**
     * Query for actors based on their average rating of movies starred in
     * @param id of action
     * @param actorsList for actors in the system
     * @param number of actors for query
     * @param sortingType for query
     * @param filters for query
     * @param movieList for movies in the system
     * @param serialList for shows in the system
     * @return JSONObject containing query result
     */
    public static JSONObject averageActorQuery(final int id, final ArrayList<Actor> actorsList,
                                               final int number, final String sortingType,
                                               final List<List<String>> filters,
                                               final ArrayList<Movie> movieList,
                                               final ArrayList<Serial> serialList) {
        CharSequence[] words = getWords(filters.get(2));
        final int magicNumber = 3;
        ArrayList<ActorsAwards> awards = getAwards((ArrayList<String>) filters.get(magicNumber));

        Map<String, Double> topActorsList = new TreeMap<>();
        actorsList.stream().filter(actor ->
                (awards == null || actor.getAwards().entrySet().containsAll(awards))
             && topActorsList.computeIfPresent(actor.getName(), (k, v) -> v + 1) == null)
             .forEach(actor ->
               topActorsList.put(actor.getName(), actor.getRating(movieList, serialList)));

        topActorsList.values().removeIf(d -> d == 0.0);

        if (words != null) {
            for (CharSequence word : words) {
                topActorsList.entrySet().stream().filter(map -> map.getKey().contains(word))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        }

        return getJsonObject(number, sortingType, id, null, topActorsList);

    }

    /**
     * Query for actors by their awards
     * @param id of action
     * @param actorsList for actors in the system
     * @param number of actors for query
     * @param sortingType for query
     * @param filters for query
     * @return JSONObject containing query result
     */
    public static JSONObject awardsActorQuery(final int id, final ArrayList<Actor> actorsList,
                                              final int number, final String sortingType,
                                              final List<List<String>> filters) {
        final int magicNumber = 3;
        ArrayList<ActorsAwards> awards = getAwards((ArrayList<String>) filters.get(magicNumber));

        Set<ActorsAwards> conditionSet = new TreeSet<>(awards);
        Map<String, Integer> topActorsList = new TreeMap<>();
        actorsList.stream().filter(actor -> actor.getAwards().keySet().containsAll(conditionSet)
                && topActorsList.computeIfPresent(actor.getName(), (k, v) -> v + 1) == null)
                .forEach(actor -> topActorsList.put(actor.getName(),
                        actor.getAwards().values().stream().reduce(0, Integer::sum)));

        return getJsonObject(number, sortingType, id, topActorsList, null);
    }

    /**
     * Query for actors based on filters
     * @param id of action
     * @param actorsList actor list
     * @param sortingType for query
     * @param filters for query
     * @return
     */
    public static JSONObject filtersActorQuery(final int id, final ArrayList<Actor> actorsList,
                                               final String sortingType,
                                               final List<List<String>> filters) {
        ArrayList<String> words = (ArrayList<String>) filters.get(2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        ArrayList<String> topActorsList = new ArrayList<>();

        for (Actor actor : actorsList) {
            int count = 0;
            List<String> description = Arrays.asList(actor.getCareerDescription()
                    .toLowerCase().split("[ -.,]"));
            for (String word : words) {
                if (description.contains(word)) {
                    count++;
                }
            }
            if (count == words.size()) {
                topActorsList.add(actor.getName());
            }
        }

        if (sortingType.equals("asc")) {
            topActorsList.sort(Comparator.naturalOrder());
        } else {
            topActorsList.sort(Comparator.reverseOrder());
        }
        jsonObject.put("message", "Query result: " + topActorsList.toString());
        return jsonObject;
    }
}
