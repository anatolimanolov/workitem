package com.company.models.workitems;

import com.company.models.MemberImpl;
import com.company.models.ValidationHelper;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Member;
import com.company.models.workitems.enums.FeedbackStatus;

import java.util.ArrayList;
import java.util.List;

public class FeedbackImpl extends WorkItemsBaseImpl implements Feedback {

    private static final int MIN_RATING_VALUE = 1;
    private static final int MAX_RATING_VALUE = 10;
    private static final String INVALID_RATING_EXCEPTION_MESSAGE = "Rating must be a number between 1 and 10.";

    private int rating;
    private FeedbackStatus status;
    private Member author;
    private List<String> comments;
    private List<String> activityHistory;
    private String board;


    public FeedbackImpl(long itemID, String title, String description,
                        int rating, FeedbackStatus status, MemberImpl author) {
        super(itemID, title, description);
        setRating(rating);
        setStatus(status);
        setAuthor(author);
        activityHistory = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public FeedbackStatus getStatus() {
        return status;
    }

    @Override
    public Member getAuthor() {
        return author;
    }

    @Override
    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public List<String> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(activityHistory);
    }

    public String getBoard() {
        return board;
    }

    public void changeRating(int newRating) {
        setRating(newRating);
    }

    public String toString() {
        return super.toString() + String.format("Rating: %d" + System.lineSeparator() +
                        "Status: %s" + System.lineSeparator() +
                        "Author: %s" + System.lineSeparator(),
                this.getRating(),
                this.getStatus(),
                this.getAuthor().getName());
    }

    @Override
    public void addComment(String comment) {
        comments.add(comment);
    }

    private void setRating(int rating) {
        ValidationHelper.checkRatingRange(rating, MIN_RATING_VALUE, MAX_RATING_VALUE, INVALID_RATING_EXCEPTION_MESSAGE);
        this.rating = rating;
    }

    public void addActivity(String activity) {
        activityHistory.add(activity);
    }

    public void changeStatus(FeedbackStatus newStatus) {
        setStatus(newStatus);
    }

    private void setStatus(FeedbackStatus status) {
        this.status = status;
    }

    private void setAuthor(MemberImpl author) {
        this.author = author;
    }
}
