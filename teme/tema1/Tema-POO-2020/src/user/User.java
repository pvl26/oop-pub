package user;

import entertainment.Genre;
import entertainment.Season;
import fileio.UserInputData;
import org.json.JSONObject;
import utils.Utils;
import video.Movie;
import video.Serial;
import video.Show;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class User {
    /**
     * The username of the user
     */
    private final String username;
    /**
     * The subscription plan type of the user
     */
    private final Subscription subscriptionType;
    /**
     * The history of the videos seen by the user
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites list of the user
     */
    private ArrayList<String> favoriteMovies;
    /**
     * The number of reviews by user
     */
    private Integer reviewsCount;

    /**
     * Constructor for user
     * @param userInput input data for user
     * @param movieList of movies in the system
     * @param serialList of shows in the system
     */
    public User(final UserInputData userInput,
                 final ArrayList<Movie> movieList,
                 final ArrayList<Serial> serialList) {
        this.username = userInput.getUsername();
        if ("premium".equals(userInput.getSubscriptionType().toLowerCase())) {
            this.subscriptionType = Subscription.PREMIUM;
        } else {
            this.subscriptionType = Subscription.BASIC;
        }
        this.history = userInput.getHistory();
        this.addView(movieList, serialList);
        this.favoriteMovies = userInput.getFavoriteMovies();
        this.reviewsCount = 0;
    }

    /**
     * Add view to move / serial
     * @param movieList list fo movies
     * @param serialList list of shows
     */
    private void addView(final ArrayList<Movie> movieList,
                          final ArrayList<Serial> serialList) {
        List<Map.Entry<String, Integer>> historyList = new ArrayList<>(this.history.entrySet());

        historyList.forEach(video -> {
            movieList.stream().filter(movie -> movie.getTitle().equals(video.getKey()))
                    .findFirst().ifPresent(
                    movie -> IntStream.range(0, video.getValue()).forEach(i -> movie.addViews())
            );
            serialList.stream().filter(serial -> serial.getTitle().equals(video.getKey()))
                    .findFirst().ifPresent(
                    serial -> IntStream.range(0, video.getValue()).forEach(i -> serial.addViews())
            );
        });
    }

    /**
     * Add to favorite movies list of user
     * @param id action id
     * @param movie name
     * @return JSONObject for command
     */
    public JSONObject addToFavoriteMovies(final int id, final String movie) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (this.favoriteMovies.contains(movie)) {
            jsonObject.put("message", "error -> " + movie + " is already in favourite list");
            return jsonObject;
        }
        if (this.history.containsKey(movie)) {
            this.favoriteMovies.add(movie);
            jsonObject.put("message", "success -> " + movie + " was added as favourite");
        } else {
            jsonObject.put("message", "error -> " + movie + " is not seen");
        }
        return jsonObject;
    }

    /**
     * View movie/show
     * @param id of action
     * @param video name of movie/show
     * @param movieList of movies
     * @param serialList of shows
     * @return JSONObject for command
     */
    public JSONObject viewVideo(final int id, final String video,
                                final ArrayList<Movie> movieList,
                                final ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int count = this.history.getOrDefault(video, 0) + 1;
        movieList.stream().filter(movie -> movie.getTitle().equals(video))
                .findFirst().ifPresent(Show::addViews);
        serialList.stream().filter(serial -> serial.getTitle().equals(video))
                .findFirst().ifPresent(Show::addViews);

        this.history.put(video, count);

        jsonObject.put("message", "success -> " + video
                + " was viewed with total views of " + count);
        return jsonObject;
    }

    /**
     * Add rating command
     * @param id of action
     * @param video name
     * @param grade for rating
     * @param season of show
     * @param movieList list of movies
     * @param serialList list of shows
     * @return JSONObject for command
     */
    public JSONObject addRating(final int id, final String video,
                                final double grade, final int season,
                                final ArrayList<Movie> movieList,
                                final ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (season == 0) {
            Stream<Movie> movieStream = movieList.stream();
            Movie movie = movieStream.filter(show -> show.getTitle().equals(video))
                    .findFirst()
                    .orElse(null);
            if (movie == null) {
                return jsonObject;
            }

            if (movie.getRatedBy().contains(this.username)) {
                jsonObject.put("message", "error -> " + movie.getTitle()
                        + " has been already rated");
                return jsonObject;
            } else {
                if (this.history.containsKey(video)) {
                    movie.addGrade(grade);
                    jsonObject.put("message", "success -> " + video
                            + " was rated with " + grade + " by " + this.username);
                    this.addReviewsCount();
                    movie.addRatedBy(this.username);
                } else {
                    jsonObject.put("message", "error -> " + video + " is not seen");
                }
            }
            return jsonObject;
        } else {
            Stream<Serial> serialStream = serialList.stream();
            Serial serial = serialStream.filter(show -> show.getTitle().equals(video))
                    .findFirst().orElse(null);
            if (serial == null) {
                return jsonObject;
            }

            Season showSeason = serial.getSeasons().stream()
                    .filter(szn -> szn.getCurrentSeason() == season).findFirst().orElse(null);
            if (showSeason == null) {
                return jsonObject;
            }

            if (showSeason.getUserRatings().contains(this.username)) {
                jsonObject.put("message", "error -> " + serial.getTitle()
                        + " has been already rated");
                return jsonObject;
            } else {
                if (this.history.containsKey(video)) {
                    showSeason.addRatings(username, grade);
                    jsonObject.put("message", "success -> " + video + " was rated with "
                            + grade + " by " + this.username);
                    this.addReviewsCount();
                    showSeason.addUsersRating(this.username);
                } else {
                    jsonObject.put("message", "error -> " + video + " is not seen");
                }
            }
        }
        return jsonObject;
    }

    /**
     * Standard Search recommendation
     * @param id of action
     * @param movieList list of movies
     * @param serialList list of shows
     * @return JSONObject for command
     */
    public JSONObject standardSearch(final int id, final ArrayList<Movie> movieList,
                                     final ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        for (Movie movie : movieList) {
            if (!this.getHistory().containsKey(movie.getTitle())) {
                jsonObject.put("message", "StandardRecommendation result: "
                        + movie.getTitle());
                return jsonObject;
            }
        }
        for (Serial serial : serialList) {
            if (!this.getHistory().containsKey(serial.getTitle())) {
                jsonObject.put("message", "StandardRecommendation result: "
                        + serial.getTitle());
                return jsonObject;
            }
        }
        jsonObject.put("message", "StandardRecommendation cannot be applied!");
        return jsonObject;
    }

    /**
     * Premium search recommendation
     * @param id of action
     * @param movieList list of movies
     * @param serialList list of serials
     * @param genra for query
     * @return JSONObject for command
     */
    public JSONObject premiumSearch(final int id, final ArrayList<Movie> movieList,
                                    final ArrayList<Serial> serialList, final String genra) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        ArrayList<String> recommendationList = new ArrayList<>();

        if (this.getSubscriptionType() == Subscription.BASIC) {
            jsonObject.put("message", "SearchRecommendation cannot be applied!");
            return jsonObject;
        }

        for (Movie movie : movieList) {
            if (!this.getHistory().containsKey(movie.getTitle())) {
                if (movie.getGenres().contains(Utils.stringToGenre(genra))) {
                    recommendationList.add(movie.getTitle());
                }
            }
        }
        for (Serial serial : serialList) {
            if (!this.getHistory().containsKey(serial.getTitle())) {
                if (serial.getGenres().contains(Utils.stringToGenre(genra))) {
                    recommendationList.add(serial.getTitle());
                }
            }
        }
        recommendationList.sort(Comparator.naturalOrder());
        if (recommendationList.isEmpty()) {
            jsonObject.put("message", "SearchRecommendation cannot be applied!");
        } else {
            jsonObject.put("message", "SearchRecommendation result: " + recommendationList);
        }
        return jsonObject;
    }

    /**
     * Recommendatioin for best unseen video
     * @param id of action
     * @param movieList list
     * @param serialList list
     * @return JSONObject for command
     */
    public JSONObject bestUnseen(final int id, final ArrayList<Movie> movieList,
                                 final ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        Map.Entry<String, Double> maxEntry = null;

        for (Movie movie : movieList) {
            if (!this.getHistory().containsKey(movie.getTitle())) {
                if (maxEntry == null || movie.getGrade() > maxEntry.getValue()) {
                    maxEntry = Map.entry(movie.getTitle(), movie.getGrade());
                }
            }
        }
        for (Serial serial : serialList) {
            if (!this.getHistory().containsKey(serial.getTitle())) {
                if (maxEntry == null || serial.getGrade() > maxEntry.getValue()) {
                    maxEntry = Map.entry(serial.getTitle(), serial.getGrade());
                }
            }
        }

        if (maxEntry != null) {
            jsonObject.put("message", "BestRatedUnseenRecommendation result: "
                    + maxEntry.getKey());
        } else {
            jsonObject.put("message", "BestRatedUnseenRecommendation cannot be applied!");
        }
        return jsonObject;
    }

    /**
     * Recommendation for most popular video
     * @param id action
     * @param movieList list
     * @param serialList list
     * @return JSONObject for command
     */
    public JSONObject popularRecommendation(final int id, final ArrayList<Movie> movieList,
                                            final ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (this.getSubscriptionType() == Subscription.BASIC) {
            jsonObject.put("message", "PopularRecommendation cannot be applied!");
            return jsonObject;
        }

        Map<Genre, Integer> genreList = new HashMap<>();

        for (Movie movie : movieList) {
            movie.getGenres().stream().
                    filter(genre -> genreList.computeIfPresent(genre, (k, v) -> v + 1) == null)
                    .forEach(genre -> genreList.put(genre, 1));
        }
        for (Serial serial : serialList) {
            serial.getGenres().stream()
                    .filter(genre -> genreList.computeIfPresent(genre, (k, v) -> v + 1) == null)
                    .forEach(genre -> genreList.put(genre, 1));
        }

        List<Map.Entry<Genre, Integer>> toSort = new ArrayList<>(genreList.entrySet());
        toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<Genre, Integer> genreIntegerEntry : toSort) {
            for (Movie movie : movieList) {
                if (!movie.getGenres().contains(genreIntegerEntry.getKey())) {
                    continue;
                }
                if (!this.getHistory().containsKey(movie.getTitle())) {
                    jsonObject.put("message", "PopularRecommendation result: " + movie.getTitle());
                    return jsonObject;
                }
            }
            for (Serial serial : serialList) {
                if (!serial.getGenres().contains(genreIntegerEntry.getKey())) {
                    continue;
                }
                if (!this.getHistory().containsKey(serial.getTitle())) {
                    jsonObject.put("message", "PopularRecommendation result: " + serial.getTitle());
                    return jsonObject;
                }
            }
        }
        jsonObject.put("message", "PopularRecommendation cannot be applied!");
        return jsonObject;
    }

    /**
     * Recommendation based on most favorite videos
     * @param id of action
     * @param movieList of movies
     * @param serialList of serials
     * @param userList list of users
     * @return JSONObject for command
     */
    public JSONObject favoriteRecommendation(final int id, final ArrayList<Movie> movieList,
                                             final ArrayList<Serial> serialList,
                                             final ArrayList<User> userList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (this.getSubscriptionType() == Subscription.BASIC) {
            jsonObject.put("message", "FavoriteRecommendation cannot be applied!");
            return jsonObject;
        }
        Map<String, Integer> favoriteList = new HashMap<>();

        userList.forEach(user -> user.getFavoriteMovies().stream()
                .filter(movie -> favoriteList.computeIfPresent(movie, (k, v) -> v + 1) == null)
                .forEach(video -> favoriteList.put(video, 1)));

        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(favoriteList.entrySet());
        toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> favoriteVideo : toSort) {
            if (!this.getHistory().containsKey(favoriteVideo.getKey())) {
                jsonObject.put("message", "FavoriteRecommendation result: "
                        + favoriteVideo.getKey());
                return jsonObject;
            }
        }

        for (Movie movie : movieList) {
            if (userList.stream().anyMatch(user -> user.getFavoriteMovies()
                    .contains(movie.getTitle()))
                    && !this.getHistory().containsKey(movie.getTitle())) {
                jsonObject.put("message", "FavoriteRecommendation result: "
                        + movie.getTitle());
                return jsonObject;
            }
        }

        for (Serial serial : serialList) {
            if (userList.stream().anyMatch(user -> user.getFavoriteMovies()
                    .contains(serial.getTitle()))
                    && !this.getHistory().containsKey(serial.getTitle())) {
                jsonObject.put("message", "FavoriteRecommendation result: "
                        + serial.getTitle());
                return jsonObject;
            }
        }

        jsonObject.put("message", "FavoriteRecommendation cannot be applied!");
        return jsonObject;
    }

    /**
     * Get subscription type
     * @return Subscription Object
     */
    public Subscription getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * Get favorite movies list
     * @return list of Strings
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     * Get username
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get history of user
     * @return Map of title - count entries
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     * Get number of reviews
     * @return Integer
     */
    public Integer getReviewsCount() {
        return reviewsCount;
    }

    /**
     * Add one review to counter
     */
    public void addReviewsCount() {
        this.reviewsCount += 1;
    }

    /**
     * ToString method
     * @return String object with all user fields
     */
    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + ", subscriptionType=" + subscriptionType
                + ", history=" + history
                + ", favoriteMovies=" + favoriteMovies
                + '}';
    }
}
