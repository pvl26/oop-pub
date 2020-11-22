package Action;

import fileio.ActionInputData;

/**
 * Information about a recommendation action, retrieved from ActionInputData
 * <p>
 */
public class Recommendation extends Action {
    /**
     * Used for commands
     */
    private final String type;                 //   -------- for command and recommandation
    /**
     * Username of user
     */
    private final String username;              //  -------- for command and recomandation
    /**
     * Video genre
     */
    private final String genre;                  //  ------------- for recomandation

    public Recommendation(ActionInputData actionInput) {
        super(actionInput.getActionId(), actionInput.getActionType());
        this.type = actionInput.getType();
        this.username = actionInput.getUsername();
        this.genre = actionInput.getGenre();
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                super.toString() +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
