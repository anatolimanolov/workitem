package com.company.models.workitems;

import com.company.models.MemberImpl;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.Priority;
import com.company.models.workitems.enums.Size;
import com.company.models.workitems.enums.StoryStatus;

import java.util.*;

public class StoryImpl extends WorkItemsBaseImpl implements Story {

    private Priority priority;
    private Size size;
    private StoryStatus status;
    private List<MemberImpl> assignees;
    private List<String> comments;
    private List<String> activityHistory;
    private String board;


    public StoryImpl(long itemID, String title, String description,
                     Priority priority, Size size, StoryStatus status) {
        super(itemID, title, description);
        setPriority(priority);
        setSize(size);
        setStatus(status);
        assignees = new ArrayList<>();
        activityHistory = new ArrayList<>();
        comments = new ArrayList<>();
    }


    public Priority getPriority() {
        return priority;
    }

    public String getBoard() {
        return board;
    }

    @Override
    public List<String> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public List<MemberImpl> getAssignees() {
        return new ArrayList<>(assignees);
    }

    public void assignMember(MemberImpl member) {
        assignees.add(member);
    }

    public void unassignMember(MemberImpl member) {
        assignees.remove(member);
    }

    @Override
    public StoryStatus getStatus() {
        return status;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void changeSize(Size newSize){
        setSize(newSize);
    }

    public void changeStatus(StoryStatus newStatus){
        setStatus(newStatus);
    }

    public void changePriority(Priority newPriority){
        setPriority(newPriority);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(activityHistory);
    }

    public void addActivity(String activity) {
        activityHistory.add(activity);
    }

    public String toString() {
        return super.toString() +
                String.format("Priority: %s" + System.lineSeparator() +
                                "Size: %s" + System.lineSeparator() +
                                "Status: %s" + System.lineSeparator(),
//                        "Assignees: " +
                        this.getPriority(),
                        this.getSize(),
                        this.getStatus());
    }


    @Override
    public void setBoard(String board) {
        this.board = board;
    }

    private void setStatus(StoryStatus status) {
        this.status = status;
    }

    private void setSize(Size size) {
        this.size = size;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }
}
