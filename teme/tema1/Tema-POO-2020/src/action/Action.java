package action;

import fileio.ActionInputData;

import java.util.ArrayList;
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

    public Action(ActionInputData actionInputData) {
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

    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumber() {
        return number;
    }

    public double getGrade() {
        return grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionId=" + actionId +
                ", actionType='" + actionType + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", objectType='" + objectType + '\'' +
                ", sortType='" + sortType + '\'' +
                ", criteria='" + criteria + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", number=" + number +
                ", grade=" + grade +
                ", seasonNumber=" + seasonNumber +
                ", filters=" + filters +
                '}';
    }
}
