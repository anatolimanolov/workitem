package com.company.commands.listing;

import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.FeedbackStatus;
import com.company.models.workitems.enums.StoryStatus;

import java.util.ArrayList;
import java.util.List;

import static com.company.commands.CommandsConstants.JOIN_DELIMITER;

public class ListItemsCommand implements Command {

    private static final String[] LIST_ITEMS_LINE_INTERFACE = {
            "Enter the type of items you want to list [all/bug/feedback/story/member(assigned to)]: ",
            "Filter by [status/none]: ",
            "Sort by [title/priority/severity/size/rating/none]: "};
    private static final String NO_REGISTERED_ITEMS_IN_CATEGORY_EXCEPTION = "There are no registered items in category '%s'.";
    private static final String NO_ITEMS_WITH_STATUS_EXCEPTION = "There are no items with status: '%s'";
    private static final String NO_REGISTERED_ITEMS_WITH_PARAMETER_EXCEPTION = "There are no registered items with %s";
    private static final String UNEXPECTED_NUMBER_OF_INPUT_PARAMETERS = "Unexpected number of input parameters: %d";

    private WimRepository wimRepository;
    private List<WorkItem> items = new ArrayList<>();

    public ListItemsCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    //TODO -> Needs Testing
    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getListItemParametersLineInterface(LIST_ITEMS_LINE_INTERFACE, parameters);
        }

        if (parameters.size() == 1) {
            String parameter = parameters.get(0);
            FilterItems.filterItemsByOneParameter(items, wimRepository, parameter);
            if (items.size() == 0) {
                throw new IllegalArgumentException(String.format(NO_REGISTERED_ITEMS_IN_CATEGORY_EXCEPTION,
                        parameter));
            }
            return formatOutput(items, parameter);
        } else if (parameters.size() == 2) {
            String firstParameter = parameters.get(0);
            String secondParameter = parameters.get(1);
            if (isStatus(secondParameter)) {
                FilterItems.filterItemsByTwoParameters(items, wimRepository, firstParameter, secondParameter);
                if (items.size() == 0) {
                    throw new IllegalArgumentException(String.format(NO_ITEMS_WITH_STATUS_EXCEPTION,
                            secondParameter));
                }
                return formatOutput(items, firstParameter);
            } else {
                FilterItems.filterItemsByOneParameter(items, wimRepository, firstParameter);
                SortItems.sortItems(items, wimRepository, secondParameter);
                if (items.size() == 0) {
                    throw new IllegalArgumentException(String.format(NO_REGISTERED_ITEMS_WITH_PARAMETER_EXCEPTION,
                            secondParameter));
                }
                return formatOutput(items, firstParameter);

            }
        } else if (parameters.size() == 3) {
            String firstParameter = parameters.get(0);
            String secondParameter = parameters.get(1);
            String thirdParameter = parameters.get(2);
            FilterItems.filterItemsByTwoParameters(items, wimRepository, firstParameter, secondParameter);
            SortItems.sortItems(items, wimRepository, thirdParameter);
            return formatOutput(items, firstParameter);
        }
        throw new IllegalArgumentException(String.format(UNEXPECTED_NUMBER_OF_INPUT_PARAMETERS, parameters.size()));
    }

    private String formatOutput(List<WorkItem> items, String parameter) {
        List<String> itemsAsString = new ArrayList<>();
        for (WorkItem item : items) {
            itemsAsString.add(item.toString() + JOIN_DELIMITER);
        }
        itemsAsString.add(0, String.format("%s:", parameter.substring(0, 1).
                toUpperCase() + parameter.substring(1)));
        itemsAsString.add(1, JOIN_DELIMITER);
        return String.join(System.lineSeparator(), itemsAsString);
    }

    private boolean isStatus(String parameter) {
        for (BugStatus bugStatus : BugStatus.values()) {
            if (bugStatus.toString().equals(parameter.toUpperCase())) {
                return true;
            }
        }

        for (FeedbackStatus feedbackStatus : FeedbackStatus.values()) {
            if (feedbackStatus.toString().equals(parameter.toUpperCase())) {
                return true;
            }
        }

        for (StoryStatus storyStatus : StoryStatus.values()) {
            if (storyStatus.toString().equals(parameter.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}