package com.company;

import com.company.core.EngineImpl;

import java.io.ByteArrayInputStream;

public class Main {

    public static void testInput() {
        String input =
                "createperson Maurice\n" +
//                        "createperson Maurice\n" +
//                "createperson Maurice3\n" +
//                "createperson Maurice2\n" +
//                "createperson Maurice4\n" +
//                "listpeople\n" +

                        "createteam team1\n" +
//                        "createteam team2\n" +
//                "createteam team4\n" +
//                "createteam team3\n" +
//                "createteam team2\n" +
//                "listteams\n" +

                        "createboard board1 team1\n" +
//                        "createboard board2 team1\n" +
//                        "createboard board1 team2\n" +
//                "createboard board2 team1\n" +
//                "listboards team1\n" +
//                "listboards team2\n" +
//                "listboards team2 team2\n" +
//                "listboards team12344\n" +

                        "addpersontoteam Maurice team1\n" +
//                "addpersontoteam dfghjk team1\n" +
//                "addpersontoteam Maurice asdfghjk\n" +
//                "addpersontoteam Maurice team1\n" +

//                "listmembers team1\n" +
//                "listmembers team2\n" +
//                "listmembers tedrfghj\n" +

//                        "createbug bugtest bugDescription high major fixed team1 board1\n" +
//                        "createbug bugtest bugDescription high major fixed team1 board2\n" +
//                        "createbug bugtest bugDescription high major fixed board1\n" +
//                        "createbug bugtest bugDescription high major fixed board2\n" +
//                        "createbug bugtest bugDescription low major fixed board2\n" +
//                        "createbug abugname1 bugDescription medium major active team2 board1\n" +
//                        "createbug aabugname1 bugDescription low major active team1 board1\n" +
//                        "createbug aabbugname1 bugDescription low major active team2 board1\n" +
//                "createbug abbugname1 bugDescription high major active board1\n" +
//                "createbug bugname2 bugDescription high major active board1\n" +
//                "createbug thisistest bugDescription high major fixed board1\n" +
//                "createbug bug3 bugDescription low major fixed board1\n" +
//                "createbug bug2 bugDescription medium major fixed board1\n" +
//
//                        "createstory bstory storyDescription low medium done team1 board1\n" +
//                        "createstory story storyDescription low medium done board1\n" +
//                        "createstory dstory9 storyDescription medium small inprogress team2 board1\n" +
//                "createstory story3 storyDescription high large notdone board1\n" +
//                "createstory story4 storyDescription high medium inprogress board1\n" +
//                "createstory story5 storyDescription high medium done board1\n" +
//
//                        "createfeedback abbfeedback feedbackDescription 5 new team1 board1 Maurice\n" +
//                        "createfeedback abfeedback feedbackDescription 5 new board1 Maurice\n" +
//                        "createfeedback adfeedback feedbackDescription 5 new board1 Maurice\n" +
//                        "createfeedback aacsfeedback feedbackDescription 5 new board1 Maurice\n" +
                        "createfeedback feedback feedbackDescription 5 new board1 team1 Maurice\n" +
//                "createfeedback feedback3 feedbackDescription 5 unscheduled board1 Maurice\n" +
//                "createfeedback feedback2 feedbackDescription 5 done board1 Maurice\n" +

                        "listitems all\n" +
//                "assignitem feedback Maurice\n" +
//                 "assignitem Maurice team1 board1 bugtest\n" +
//                 "unassignitem Maurice team1 board1 bugtest\n" +
//                "assignitem bugname3 Maurice\n" +
//                "assignitem bug3 Maurice\n" +
//                "assignitem feedback2 Maurice\n" +
//                "unassignitem feedback2 Maurice\n" +

//                "assignitem story Maurice\n" +
//                "assignitem bugtest Maurice\n" +
//                "assignitem bugname afsg\n" +
//                "assignitem afsrtrr Maurice\n" +
//                "unassignitem bugname Maurice\n" +
//                "unassignitem story2 Maurice\n" +
//                "unassignitem story2 Maurice\n" +
//                  "assignitem story2 Maurice\n" +

//                "changepriority Maurice bugtest medium team1 board1\n" +
//                "changepriority story high\n" +
//                "changepriority story low\n" +
//                "changestatus feedback unscheduled\n" +
//                "changestatus story notdone\n" +
//                "changestatus bugtest fixed\n" +
                        "changerating feedback 9 Maurice team1 board1\n" +
//                "changeseverity bugtest critical\n" +
//                "changesize story large\n" +
//                "assignitem bugtest Maurice\n" +

//                "addcomment Maurice bugtest ThisIsComment\n" +
//                "addcomment Maurice2 bugtest This is another comment\n" +
//                "addcomment Maurice2 story This is another comment\n" +
//                "addcomment Maurice2 feedback This is another comment\n" +
//
//                        "listboards team1\n" +
//                "listmembers team1\n" +
//                "listitems all\n" +
//                        "listitems all title\n"  +
//                "listitems feedback\n" +
//                "listitems story\n" +
//                "listitems asdfjvbnvk\n" +
//                "listitems Maurice\n" +
//                "listitems Maurice2\n" +

//                "listitems bug fixed\n" +
//                "listitems bug title\n" +
//                "listitems feedback scheduled\n" +
//                "listitems story notdone\n" +
//                "listitems story notdone dgb\n" +
//                "listitems Maurice fixed\n" +
//                "listitems bug title\n" +
//                "listitems Maurice fixed priority\n" +
//                "listitems bug fixed priority\n" +
//                "listitems story title\n" +
//                "listitems story priority\n" +
//                "listitems bug priority\n" +
//                "listitems story size\n" +
//                "listitems story inprogress title\n" +

//                "addcomment Maurice bug2 thisIsComment\n" +
//                "addcomment Maurice bug2 thisIsSecondComment\n" +
//                "listitems bug\n" +
//                "showboardactivity board1\n" +
//                "showboardactivity board2\n" +
//                "showpersonactivity Maurice\n" +
//                "showteamactivity team1\n" +

//                "help\n" +

                        "exit";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    public static void main(String[] args) {

//        testInput();

        EngineImpl engine = new EngineImpl();
        engine.start();
    }
}

