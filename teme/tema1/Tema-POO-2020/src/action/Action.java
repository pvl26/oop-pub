package Action;

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

    public Action(int actionId, String actionType) {
        this.actionId = actionId;
        this.actionType = actionType;
    }

    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return "actionId=" + actionId +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}
