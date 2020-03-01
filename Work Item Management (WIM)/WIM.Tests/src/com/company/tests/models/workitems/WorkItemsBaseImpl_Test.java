package com.company.tests.models.workitems;

import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.WorkItemsBaseImpl;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.Priority;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WorkItemsBaseImpl_Test {

    WimRepository wimRepository;

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
    }

    @Test
    public void addComment_should_not_returnShallowCopy() {
        //Arrange
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        WorkItem testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        testBug.addComment("Item's activity was changed");

        //Assert and Act
        Assert.assertEquals(1, testBug.getComments().size());
    }
}
