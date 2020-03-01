package com.company.core.factories;

import com.company.core.contracts.WimFactory;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Member;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.FeedbackImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.*;

import java.util.List;

public class WimFactoryImpl implements WimFactory {

    @Override
    public MemberImpl createPerson(String name) {
        return new MemberImpl(name);
    }

    @Override
    public TeamImpl createTeam(String name) {
        return new TeamImpl(name);
    }

    @Override
    public BoardImpl createBoard(String name, String team) {
        return new BoardImpl(name, team);
    }

    @Override
    public Bug createBug(long itemID, String title, String description, Priority priority,
                         BugSeverity severity, BugStatus status, List<String> stepsToReproduce) {
        return new BugImpl(itemID, title, description, priority, severity, status, stepsToReproduce);
    }

    @Override
    public Story createStory(long itemID, String title, String description, Priority priority, Size size, StoryStatus status) {
        return new StoryImpl(itemID, title, description, priority, size, status);
    }


    @Override
    public Feedback createFeedback(long itemID, String title, String description, int rating, FeedbackStatus status, MemberImpl author) {
        return new FeedbackImpl(itemID, title, description, rating, status, author);
    }

//    private Priority getPriorityType(String priority) {
//        return Priority.valueOf(priority.toUpperCase());
//    }
//
//    private BugSeverity getSeverityType(String severity) {
//        return BugSeverity.valueOf(severity.toUpperCase());
//    }
//
//    private BugStatus getBugStatusType(String status) {
//        return BugStatus.valueOf(status.toUpperCase());
//    }
//
//    private StoryStatus getStoryStatus(String status) {
//        return StoryStatus.valueOf(status.toUpperCase());
//    }
//
//    private Size getSizeType (String size) {
//        return Size.valueOf(size.toUpperCase());
//    }
//
//    private FeedbackStatus getFeedbackStatus (String status) {
//        return FeedbackStatus.valueOf(status.toUpperCase());
//    }

}
