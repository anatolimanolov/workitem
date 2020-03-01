package com.company.models.workitems;

import com.company.models.MemberImpl;
import com.company.models.contracts.Bug;
import com.company.models.workitems.enums.Priority;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;

import javax.print.attribute.standard.Severity;
import java.util.ArrayList;
import java.util.List;

public class BugImpl extends WorkItemsBaseImpl implements Bug {

    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 10;
    private static final String NAME_ERROR_MESSAGE =
            "Board name must be between 5 and 10 characters";

    private List<String> stepsToReproduce;
    private BugSeverity severity;
    private Priority priority;
    private BugStatus status;
    private List<MemberImpl> assignees;
    private List<String> comments;
    private List<String> activityHistory;
    private String board;

    public BugImpl(long itemID, String title, String description,
                   Priority priority, BugSeverity severity, BugStatus status, List<String> stepsToReproduce) {
        super(itemID, title, description);
        setPriority(priority);
        setSeverity(severity);
        setStatus(status);
        addStepsToReproduce(stepsToReproduce);
        assignees = new ArrayList<>();
        activityHistory = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);
    }

    @Override
    public BugSeverity getSeverity() {
        return severity;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public List<MemberImpl> getAssignees() {
        return new ArrayList<>(assignees);
    }

    public String getBoard() {
        return board;
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
    public BugStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public String toString() {
        return super.toString() + String.format("Severity: %s" + System.lineSeparator() +
                        "Priority: %s" + System.lineSeparator() +
                        "Stats: %s" + System.lineSeparator() +
                        "Steps to reproduce: " + System.lineSeparator() +
                        String.join(System.lineSeparator(), getStepsToReproduce()) + System.lineSeparator(),
                this.getSeverity(),
                this.getPriority(),
                this.getStatus());
    }

    @Override
    public void addComment(String comment) {
        comments.add(comment);
    }

    public void addStepsToReproduce(List<String> stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }

    public void assignMember(MemberImpl member) {
        assignees.add(member);
    }

    public void changePriority(Priority newPriority) {
        setPriority(newPriority);
    }

    public void changeSeverity(BugSeverity newSeverity) {
        setSeverity(newSeverity);
    }

    public void changeStatus(BugStatus newStatus) {
        setStatus(newStatus);
    }

    public void unassignMember(MemberImpl member) {
        assignees.remove(member);
    }

    public void addActivity(String activity) {
        activityHistory.add(activity);
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    private void setStatus(BugStatus status) {
        this.status = status;
    }

    private void setSeverity(BugSeverity severity) {
        this.severity = severity;
    }
}
