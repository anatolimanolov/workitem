package com.company.tests.models;

import com.company.models.MemberImpl;
import com.company.models.contracts.Member;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.Priority;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MemberImpl_Test {

    @Before
    public void before() {
        Member testMember = new MemberImpl("Maurice");
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtest1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_NameIsNull() {
        //Arrange, Act, Assert
        Member newMember = new MemberImpl("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_nameLengthIsInvalid() {
        //Arrange, Act, Assert
        Member newMember = new MemberImpl("Tes");
    }

    @Test
    public void constructor_should_return_member_when_argumentsAreValid() {
        Member newMember = new MemberImpl("Maurice");
    }

    @Test
    public void getWorkItems_should_not_returnShallowCopy() {
        //Arrange
        Member newMember = new MemberImpl("Maurice");
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtest1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        newMember.addWorkItem(testBug);


        //Assert and Act
        Assert.assertEquals(1, newMember.getItem().size());
    }

    @Test
    public void getActivityHistory_should_not_returnShallowCopy() {
        //Arrange
        Member newMember = new MemberImpl("Maurice");
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtest1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        newMember.addWorkItem(testBug);
        newMember.addActivity("Maurice created new item");


        //Assert and Act
        Assert.assertEquals(1, newMember.getActivityHistory().size());
    }


}
