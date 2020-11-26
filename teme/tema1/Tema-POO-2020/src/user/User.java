package user;

import entertainment.Genre;
import entertainment.Season;
import fileio.UserInputData;
import jdk.jshell.execution.Util;
import org.json.JSONObject;
import utils.Utils;
import video.Movie;
import video.Serial;
import video.Show;

import java.util.*;
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

    public User (UserInputData userInput) {
        this.username = userInput.getUsername();
        if ("premium".equals(userInput.getSubscriptionType().toLowerCase())) {
            this.subscriptionType = Subscription.PREMIUM;
        } else {
            this.subscriptionType = Subscription.BASIC;
        }
        this.history = userInput.getHistory();
        this.favoriteMovies = userInput.getFavoriteMovies();
    }

    public JSONObject addToFavoriteMovies(int id, String movie) {
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

    public JSONObject viewVideo(int id, String video, ArrayList<Movie> movieList, ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        int count = this.history.getOrDefault(video, 0) + 1;
        movieList.stream().filter(movie -> movie.getTitle().equals(video)).findFirst().ifPresent(Show::addViews);
        serialList.stream().filter(serial -> serial.getTitle().equals(video)).findFirst().ifPresent(Show::addViews);

        this.history.put(video, count);

        jsonObject.put("message", "success -> " + video + " was viewed with total views of " + count);
        return jsonObject;
    }

    public JSONObject addRating(int id, String video, double grade, int season, ArrayList<Movie> movieList, ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (season == 0) {
            Stream<Movie> movieStream = movieList.stream();
            Movie movie = movieStream.filter(show -> show.getTitle().equals(video))
                    .findFirst()
                    .orElse(null);
            if (movie == null) { return jsonObject; }

            if (movie.getRatedBy().contains(this.username)) {
                jsonObject.put("message", "error -> " + movie + " has been already rated");
                return jsonObject;
            } else {
                if (this.history.containsKey(video)) {
                    movie.setGradeCount(movie.getGradeCount() + 1);
                    movie.setGrade((movie.getGrade() + grade) / movie.getGradeCount());
                    jsonObject.put("message", "success -> " + video + " was rated with " + grade + " by " + this.username);
                } else {
                    jsonObject.put("message", "error -> " + video + " is not seen");
                }
            }
            return jsonObject;
        } else {
            Stream<Serial> serialStream = serialList.stream();
            Serial serial = serialStream.filter(show -> show.getTitle().equals(video)).findFirst().orElse(null);
            if (serial == null) { return jsonObject; }

            Season _season = serial.getSeasons().stream().filter(szn -> szn.getCurrentSeason() == season).findFirst().orElse(null);
            if (_season == null) { return jsonObject; }


            if (_season.getUserRatings().contains(this.username)) {
                jsonObject.put("message", "error -> " + serial.getTitle() + " has been already rated");
                return jsonObject;
            } else {
                if (this.history.containsKey(video)) {
                    _season.addRatings(username, grade);
                    jsonObject.put("message", "success -> " + video + " was rated with " + grade + " by " + this.username);
                } else {
                    jsonObject.put("message", "error -> " + video + " is not seen");
                }
            }
        }

        return jsonObject;
    }

    public JSONObject standardSearch(int id, ArrayList<Movie> movieList, ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        for (Movie movie : movieList) {
            if (!this.getHistory().containsKey(movie.getTitle())){
                jsonObject.put("message", "StandardRecommendation result: " + movie.getTitle());
                return jsonObject;
            }
        }
        for (Serial serial : serialList) {
            if (!this.getHistory().containsKey(serial.getTitle())){
                jsonObject.put("message", "StandardRecommendation result: " + serial.getTitle());
                return jsonObject;
            }
        }
        jsonObject.put("message", "SearchRecommendation cannot be applied!");
        return jsonObject;
    }

    public JSONObject premiumSearch(int id, ArrayList<Movie> movieList, ArrayList<Serial> serialList, String genra) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        ArrayList<String> recommendationList = new ArrayList<>();

        if (this.getSubscriptionType() == Subscription.BASIC) {
            jsonObject.put("message", "PopularRecommendation cannot be applied!");
            return jsonObject;
        }

        for (Movie movie : movieList) {
            if (!this.getHistory().containsKey(movie.getTitle())){
                if (movie.getGenres().contains(Utils.stringToGenre(genra)))
                recommendationList.add(movie.getTitle());
            }
        }
        for (Serial serial : serialList) {
            if (!this.getHistory().containsKey(serial.getTitle())){
                if (serial.getGenres().contains(Utils.stringToGenre(genra)))
                recommendationList.add(serial.getTitle());
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

    public JSONObject bestUnseen(int id, ArrayList<Movie> movieList, ArrayList<Serial> serialList) {
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

//        if (maxEntry == null) {
            if (this.standardSearch(id, movieList, serialList).getString("message").contains("result")) {
                jsonObject.put("message", "BestRatedUnseenRecommendation result: " + this.standardSearch(id, movieList, serialList).getString("message").substring(31));
            } else {
                jsonObject.put("message", "BestRatedUnseenRecommendation cannot be applied!");
            }
//        } else { jsonObject.put("message", "BestRatedUnseenRecommendation result: " + maxEntry.getValue()); }

        return jsonObject;
    }

    public JSONObject popularRecommendation(int id, ArrayList<Movie> movieList, ArrayList<Serial> serialList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (this.getSubscriptionType() == Subscription.BASIC) {
            jsonObject.put("message", "PopularRecommendation cannot be applied!");
            return jsonObject;
        }

        Map<Genre, Integer> genreList = new HashMap<>();

        for (Movie movie : movieList) {
            movie.getGenres().stream().filter(genre -> genreList.computeIfPresent(genre, (k, v) -> v + 1) == null).forEach(genre -> genreList.put(genre, 1));
        }
        for (Serial serial : serialList) {
            serial.getGenres().stream().filter(genre -> genreList.computeIfPresent(genre, (k, v) -> v + 1) == null).forEach(genre -> genreList.put(genre, 1));
        }

        List<Map.Entry<Genre, Integer>> toSort = new ArrayList<>(genreList.entrySet());
        toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<Genre, Integer> genreIntegerEntry : toSort) {
            for (Movie movie : movieList) {
                if (!movie.getGenres().contains(genreIntegerEntry.getKey())) { continue; }
                if (!this.getHistory().containsKey(movie.getTitle())) {
                    jsonObject.put("message", "PopularRecommendation result: " + movie.getTitle());
                    return jsonObject;
                }
            }
            for (Serial serial : serialList) {
                if (!serial.getGenres().contains(genreIntegerEntry.getKey())) { continue; }
                if (!this.getHistory().containsKey(serial.getTitle())) {
                    jsonObject.put("message", "PopularRecommendation result: " + serial.getTitle());
                    return jsonObject;
                }
            }
        }
        jsonObject.put("message", "PopularRecommendation cannot be applied!");
        return jsonObject;
    }

    public JSONObject favoriteRecommendation(int id, ArrayList<Movie> movieList, ArrayList<Serial> serialList, ArrayList<User> userList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        if (this.getSubscriptionType() == Subscription.BASIC) {
            jsonObject.put("message", "PopularRecommendation cannot be applied!");
            return jsonObject;
        }
//        fac un map Map<String, Integer>, cu String - titlul videoului, si value

        Map<String, Integer> favoriteList = new HashMap<>();

        userList.forEach(user -> user.getFavoriteMovies().forEach(video -> favoriteList.put(video, 1)));

        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(favoriteList.entrySet());
        toSort.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));

        for (Map.Entry<String, Integer> favoriteVideo : toSort) {
            if (!this.getHistory().containsKey(favoriteVideo.getKey())) {
                jsonObject.put("message", "FavoriteRecommendation result: " + favoriteVideo.getKey());
                return jsonObject;
            }
        }

        for (Movie movie : movieList) {
            if (userList.stream().anyMatch(user -> user.getFavoriteMovies().contains(movie.getTitle())) && !this.getHistory().containsKey(movie.getTitle())) {
                jsonObject.put("message", "FavoriteRecommendation result: " + movie.getTitle());
                return jsonObject;
            }
        }

        for (Serial serial : serialList) {
            if (userList.stream().anyMatch(user -> user.getFavoriteMovies().contains(serial.getTitle())) && !this.getHistory().containsKey(serial.getTitle())) {
                jsonObject.put("message", "FavoriteRecommendation result: " + serial.getTitle());
                return jsonObject;
            }
        }

        jsonObject.put("message", "FavoriteRecommendation cannot be applied!");
        return jsonObject;
    }

    public Subscription getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", subscriptionType=" + subscriptionType +
                ", history=" + history +
                ", favoriteMovies=" + favoriteMovies +
                '}';
    }
}
