package com.company.models.contracts;

import com.company.models.MemberImpl;
import com.company.models.workitems.enums.Priority;
import com.company.models.workitems.enums.Size;
import com.company.models.workitems.enums.StoryStatus;

import java.util.List;

public interface Story extends WorkItem{

    Size getSize();

    StoryStatus getStatus();

    Priority getPriority();

    void changePriority(Priority newPriority);

    void changeSize(Size newSize);

    List<MemberImpl> getAssignees();

    String getBoard();

    void changeStatus(StoryStatus newStatus);

    void assignMember(MemberImpl member);

    void unassignMember(MemberImpl member);
}
