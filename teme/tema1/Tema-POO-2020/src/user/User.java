package User;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

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
     * The history of the movies seen by the user
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites list of the user
     */
    private final ArrayList<String> favoriteMovies;

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

//    comenzi
//    query-uri
//    recomandari


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
