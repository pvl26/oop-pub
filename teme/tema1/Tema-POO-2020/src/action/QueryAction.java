package Action;

import fileio.ActionInputData;

import java.util.List;

/**
 * Information about a query action, retrieved from ActionInputData
 * <p>
 */
public class QueryAction extends Action {
    /**
     * The type of object on which the actions will be performed
     */
    private final String objectType;                           // ----------- for query
    /**
     * Sorting type: ascending or descending
     */
    private final String sortType;                          // ----------- for query
    /**
     * The criterion according to which the sorting will be performed
     */
    private final String criteria;                          // ----------- for query
    /**
     * Query limit
     */
    private final int number;                     // --------------------- for query
    /**
     * Filters used for selecting videos
     */
    private final List<List<String>> filters;      //  ----------- for query

    public QueryAction(ActionInputData actionInput) {
        super(actionInput.getActionId(), actionInput.getActionType());
        this.objectType = actionInput.getObjectType();
        this.sortType = actionInput.getSortType();
        this.criteria = actionInput.getCriteria();
        this.number = actionInput.getNumber();
        this.filters = actionInput.getFilters();
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

    public int getNumber() {
        return number;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        return "QueryAction{" +
                super.toString() +
                "objectType='" + objectType + '\'' +
                ", sortType='" + sortType + '\'' +
                ", criteria='" + criteria + '\'' +
                ", number=" + number +
                ", filters=" + filters +
                '}';
    }
}
