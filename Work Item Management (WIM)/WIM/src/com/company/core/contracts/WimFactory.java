package com.company.core.contracts;

import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Member;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.*;

import java.util.List;


public interface WimFactory {
    MemberImpl createPerson(String name);

    TeamImpl createTeam(String name);

    BoardImpl createBoard(String name, String team);

    Bug createBug(long itemID, String title, String description,
                  Priority priority, BugSeverity severity, BugStatus status, List<String> stepsToReproduce);

    Story createStory(long itemID, String title, String description,
                      Priority priority, Size size, StoryStatus status);

    Feedback createFeedback(long itemID, String title, String description,
                            int rating, FeedbackStatus status, MemberImpl author);

}
