package com.company.models.contracts;

import com.company.models.MemberImpl;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.Priority;

import java.util.List;

public interface Bug extends WorkItem{

    List getStepsToReproduce();

    BugSeverity getSeverity();

    BugStatus getStatus();

    Priority getPriority();

    String getBoard();

    List<MemberImpl> getAssignees();

    void changeSeverity(BugSeverity newSeverity);

    void changePriority(Priority newPriority);

    void changeStatus(BugStatus newStatus);

    void assignMember(MemberImpl member);

    void unassignMember(MemberImpl member);

     void addStepsToReproduce(List<String> stepsToReproduce);
}