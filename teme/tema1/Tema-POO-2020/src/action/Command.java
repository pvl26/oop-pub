package Action;

import fileio.ActionInputData;

/**
 * Information about a command, retrieved from ActionInputData
 * <p>
 */
public class Command extends Action {
    /**
     * Used for commands
     */
    private final String type;                 //   -------- for command and recommandation
    /**
     * Username of user
     */
    private final String username;              //  -------- for command and recomandation
    /**
     * Video title
     */
    private final String title;                   //  ------------------- for commands
    /**
     * Grade for rating - aka value of the rating
     */
    private final double grade;                   //  -------------  for command
    /**
     * Season number
     */
    private final int seasonNumber;          // --------- for command
    public Command(ActionInputData actionInput) {
        super(actionInput.getActionId(), actionInput.getActionType());
        this.type = actionInput.getType();
        this.username = actionInput.getUsername();
        this.title = actionInput.getTitle();
        this.grade = actionInput.getGrade();
        this.seasonNumber = actionInput.getSeasonNumber();
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public double getGrade() {
        return grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    @Override
    public String toString() {
        return "Command{" +
                super.toString() +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", grade=" + grade +
                ", seasonNumber=" + seasonNumber +
                '}';
    }
}
