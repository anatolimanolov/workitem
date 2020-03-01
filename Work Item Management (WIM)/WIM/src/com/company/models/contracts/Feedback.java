package com.company.models.contracts;

import com.company.models.workitems.enums.FeedbackStatus;

public interface Feedback extends WorkItem{

    int getRating();

    FeedbackStatus getStatus();

    String getBoard();

    Member getAuthor();

    void changeStatus(FeedbackStatus newStatus);

    void changeRating(int newRating);
}
