package action;

import fileio.ActionInputData;

import java.util.List;

/**
 * Information about an action, retrieved from ActionInputData
 * <p>
 */
public class Action {
    /**
     * Action id
     */
    private final int actionId;
    /**
     * Type of action
     */
    private final String actionType;

    /**
     * Used for commands
     */
    private final String type;
    /**
     * Username of user
     */
    private final String username;
    /**
     * The type of object on which the actions will be performed
     */
    private final String objectType;
    /**
     * Sorting type: ascending or descending
     */
    private final String sortType;
    /**
     * The criterion according to which the sorting will be performed
     */
    private final String criteria;
    /**
     * Video title
     */
    private final String title;
    /**
     * Video genre
     */
    private final String genre;
    /**
     * Query limit
     */
    private final int number;
    /**
     * Grade for rating - aka value of the rating
     */
    private final double grade;
    /**
     * Season number
     */
    private final int seasonNumber;
    /**
     * Filters used for selecting videos
     */
    private final List<List<String>> filters;

    /**
     * Constructor for Action Object
     * @param actionInputData input data for action
     */
    public Action(final ActionInputData actionInputData) {
        this.actionId = actionInputData.getActionId();
        this.actionType = actionInputData.getActionType();
        this.criteria = actionInputData.getCriteria();
        this.filters = actionInputData.getFilters();
        this.genre = actionInputData.getGenre();
        this.grade = actionInputData.getGrade();
        this.number = actionInputData.getNumber();
        this.objectType = actionInputData.getObjectType();
        this.seasonNumber = actionInputData.getSeasonNumber();
        this.sortType = actionInputData.getSortType();
        this.title = actionInputData.getTitle();
        this.type = actionInputData.getType();
        this.username = actionInputData.getUsername();
    }

    /**
     * Get action id
     * @return id of action
     */
    public int getActionId() {
        return actionId;
    }

    /**
     * Get action type
     * @return action type as String
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Get type of action
     * @return type as String
     */
    public String getType() {
        return type;
    }

    /**
     * Get username of user
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get object type for query
     * @return query object type
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Get sorting type
     * @return "asc" / "desc
     */
    public String getSortType() {
        return sortType;
    }

    /**
     * Sorting criteria
     * @return String
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * Get show title
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get genre
     * @return genre as String
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Get number of objects for query
     * @return int
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get grade for rating
     * @return double
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Get season number for serial
     * @return int
     */
    public int getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Get filters for query
     * List[0] - year
     * List[1] - genre
     * List[2] - words list
     * List[3] - awards list
     * @return List<List<String>> list for lists of Strings
     */
    public List<List<String>> getFilters() {
        return filters;
    }

    /**
     * ToString method for Action Object
     * @return String of Action fields
     */
    @Override
    public String toString() {
        return "Action{"
                + "actionId=" + actionId
                + ", actionType='" + actionType + '\''
                + ", type='" + type + '\''
                + ", username='" + username + '\''
                + ", objectType='" + objectType + '\''
                + ", sortType='" + sortType + '\''
                + ", criteria='" + criteria + '\''
                + ", title='" + title + '\''
                + ", genre='" + genre + '\''
                + ", number=" + number
                + ", grade=" + grade
                + ", seasonNumber=" + seasonNumber
                + ", filters=" + filters
                + '}';
    }
}
