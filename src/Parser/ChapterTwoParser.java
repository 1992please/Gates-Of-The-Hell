/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Helpers.*;
import java.util.Random;
import GraphWork.*;
import java.io.IOException;
import Characters.*;
import Execution.UpdateGame;
import GameObjects.GameObject;

/**
 *
 * @author joker
 */
public class ChapterTwoParser {

    private UpdateGame ug;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;
    public boolean triggerNext;

    public ChapterTwoParser(Kamina kamina) { // kamina is in the faculty  
        // searching for the exam place
        triggerNext = false;
        unknownString = new DLinkedList();
        shapeString = new DLinkedList();

        unknownString.insertLast("I don't understand that");
        unknownString.insertLast("Please rephrase that");
        unknownString.insertLast("Undefined Input");
        unknownString.insertLast("Uknown Input");
        unknownString.insertLast("I don't get what you have just"
                + " said");
        unknownString.insertLast("Re-enter that again in different"
                + " format");
        unknownString.insertLast("Do you know the list of instructions?");
        unknownString.insertLast("Dude, R u retarded?");
        unknownString.insertLast("Please save your time and go read the manual.");
        unknownString.insertLast("Man, save your pc time and close the pc");
        // ------------------------
        shapeString.insertLast("AAAAAHHH! you are better than ever.");
        shapeString.insertLast("That's much better.");
        shapeString.insertLast("YEAH! That's what I am talking about.");
        shapeString.insertLast("I can travel to the moon");
        shapeString.insertLast("I feel, I am a new man today");
        shapeString.insertLast("I am the man!!");
        // ------------------------
        randomGenerator = new Random();
        try {
            map = new Map("Maps/Faculty.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        ug = new UpdateGame(kamina, map);
    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }

    private String shapeStringGenerator() {
        return shapeString.getItemAt(randomGenerator.nextInt(shapeString.size())).toString();
    }

    public String parse(String line) {

        // splitting the string
        String regex = "\\bthe\\b\\s*";
        line = line.replaceAll(regex, "");
        regex = "\\bto\\b\\s*";
        line = line.replaceAll(regex, "");
        String[] words = line.split(" +"); // AR: fixed the regex

        // AR: the above command will result in empty string element at the
        // beginning of the array if spaces were added
        if (words[0].equals("")) {
            String[] temp = new String[words.length - 1];
            int counter = 1;
            while (counter < words.length) {
                temp[counter - 1] = words[counter];
                counter += 1;
            }
            words = temp;
        } else {
            int counter = 0;
            while (counter < words.length) {
                words[counter] = words[counter].toLowerCase();
                counter += 1;
            }
        }
        // AR: end of empty string removal

        if (words.length < 1 || words.length > 4) {
            return unknowStringGenerator();
        }
        short state;
        switch (words[0]) {
            case "run":
                if (words.length != 2) { // AR: Adding a check
                    return unknowStringGenerator();
                }
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (map.getID() == 6) {
                            triggerNext = true;
                            return "";
                        } else if (map.whileInGoTo('n')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go north any further.";
                        }
                    case "south":
                        if (map.whileInGoTo('s')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go south any further.";
                        }
                    case "west":
                        if (map.whileInGoTo('w')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go west any further.";
                        }
                    case "east":
                        if (map.whileInGoTo('e')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go east any further.";
                        }
                    default:
                        return unknowStringGenerator();
                }
            // end of running

            case "inventory":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    String returnString = new String();
                    if (kamina.objectLists.size() == 0) {
                        returnString = "You have nothing";
                    } else {
                        returnString = "You are holding:\n";
                        for (GameObject go : kamina.objectLists) {
                            returnString += go.toString() + "\n";
                        }
                    }
                    return returnString;
                }
            // end of inventory

            case "open":
                // AR: the following check is required.
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an open-able object in front of the player.

                switch (words[1]) { // AR: I have added the cases.
                    case "door":
                        state = ug.open(GameObject.DOOR);
                        if (state == UpdateGame.NOT_DONE) {
                            return "There are no doors here to open!!";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The door is already opened";
                        }
                        return "Door opened";
                    case "window":
                        return "There are no windows here to open";
                    case "laptop":
                        state = ug.open(GameObject.LAPTOP);
                        if (state == UpdateGame.NOT_DONE) {
                            return "I don't have the laptop in the inventory.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The laptop is already opened.";
                        }
                        return "Laptop opened. You can check the mail now.";
                    case "bottle":
                        if (kamina.hasObject(GameObject.EMPTY_WATER_BOTTLE)) {
                            state = ug.open(GameObject.EMPTY_WATER_BOTTLE);
                            if (state == UpdateGame.ALREADY_DONE) {
                                return "The bottle is already opened.";
                            } else if (state == UpdateGame.DONE) {
                                return "Bottle Opened.\nShit!! The bottle is "
                                        + "empty.";
                            }
                        } else if (kamina.hasObject(GameObject.FILLED_WATER_BOTTLE)) {
                            state = ug.open(GameObject.FILLED_WATER_BOTTLE);
                            if (state == UpdateGame.ALREADY_DONE) {
                                return "The bottle is already opened.";
                            } else if (state == UpdateGame.DONE) {
                                return "Bottle Opened.\nAAAAHHH! I can drink now";
                            }
                        }
                        return "I am not holding any bottles!!";
                    default:
                        return unknowStringGenerator();
                }
            //end of opening the object.

            case "close":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an close-able object in front of the player.
                switch (words[1]) {
                    case "door":
                        state = ug.close(GameObject.DOOR);
                        if (state == UpdateGame.NOT_DONE) {
                            return "There are no doors arount to be closed!!";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The door is already closed.";
                        }
                        return "Door Closed.";
                    case "window":
                        return "I can't see any window around. I have no idea"
                                + "where you got this command from!!";
                    case "laptop":
                        state = ug.close(GameObject.LAPTOP);
                        if (state == UpdateGame.NOT_DONE) {
                            return "I am not holding the laptop";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The laptop is already closed.";
                        }
                        return "Laptop closed.";
                    case "bottle":
                        if (kamina.hasObject(GameObject.EMPTY_WATER_BOTTLE)) {
                            state = ug.close(GameObject.EMPTY_WATER_BOTTLE);
                            if (state == UpdateGame.ALREADY_DONE) {
                                return "The bottle is already closed.";
                            } else if (state == UpdateGame.DONE) {
                                return "Bottle Closed.";
                            }
                        } else if (kamina.hasObject(GameObject.FILLED_WATER_BOTTLE)) {
                            state = ug.close(GameObject.FILLED_WATER_BOTTLE);
                            if (state == UpdateGame.ALREADY_DONE) {
                                return "The bottle is already closed.";
                            } else if (state == UpdateGame.DONE) {
                                return "Bottle Closed.";
                            }
                        }
                        return "I am not holding any bottles!!";
                    default:
                        return unknowStringGenerator();
                }
            //done closing the object.

            case "look":
                // AR: the following check is required
                if (words.length != 1) {
                    return unknowStringGenerator();
                }
                // end of check
                return map.getDescription();
            //end of looking the object.

            case "take":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                switch (words[1]) {
                    case "perfume":
                        state = ug.take(GameObject.PERFUME);
                        if (state == UpdateGame.DONE) {
                            return "Perfume taken";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "I do have perfume.";
                        }
                        return "I can see no perfume bottles here.";
                    case "brush":
                        state = ug.take(GameObject.BRUSH);
                        if (state == UpdateGame.DONE) {
                            return "Brush Taken. You can make yourself look "
                                    + "better. Try that. I know it's kinda hard.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "I do have a brush.";
                        }
                        return "There are no brushes around.";

                    case "bottle":
                        if (map.checkObject(GameObject.FILLED_WATER_BOTTLE)) {
                            state = ug.take(GameObject.FILLED_WATER_BOTTLE);
                            if (state == UpdateGame.DONE) {
                                return "Filled bottle taken. Open to drink."
                                        + " Simple is that.";
                            } else if (state == UpdateGame.ALREADY_DONE) {
                                return "I am holding another bottle.";
                            }
                        } else if (map.checkObject(GameObject.EMPTY_WATER_BOTTLE)) {
                            state = ug.take(GameObject.EMPTY_WATER_BOTTLE);
                            if (state == UpdateGame.DONE) {
                                return "Filled bottle taken. Open to drink."
                                        + " Simple is that.";
                            } else if (state == UpdateGame.ALREADY_DONE) {
                                return "I am holding another bottle.";
                            }
                        }
                        return "There are no water bottles here.";
                    case "laptop":
                        state = ug.take(GameObject.LAPTOP);
                        if (state == UpdateGame.DONE) {
                            return "Laptop is added to the inventory.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The inventory already holds a laptop.";
                        }
                        return "Laptop?!! what laptop? Nothing here!!";

                    case "pants":
                        return "No pants here.";
                    default:
                        return unknowStringGenerator();
                }
            // end of taking objects

            case "use":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an close-able object in front of the player.
                switch (words[1]) {
                    case "perfume":
                        state = ug.use(GameObject.PERFUME);
                        if (state == UpdateGame.DONE) {
                            return shapeStringGenerator();
                        }
                        return unknowStringGenerator();
                    case "brush":
                        state = ug.use(GameObject.BRUSH);
                        if (state == UpdateGame.DONE) {
                            return shapeStringGenerator();
                        }
                        return unknowStringGenerator();
                    default:
                        return unknowStringGenerator();
                }
            // end of using objects.

            case "go":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (map.getID() == 6) {
                            triggerNext = true;
                            return "";
                        } else if (map.whileInGoTo('n')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go north any further.";
                        }
                    case "south":
                        if (map.whileInGoTo('s')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go south any further.";
                        }
                    case "west":
                        if (map.whileInGoTo('w')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go west any further.";
                        }
                    case "east":
                        if (map.whileInGoTo('e')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go east any further.";
                        }
                    default:
                        return unknowStringGenerator();
                }
            // end of going
            // AR: I have adde the following cases
            case "check":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                switch (words[1]) {
                    case "mail":
                        state = ug.check("mail");
                        if (state == UpdateGame.DONE) {
                            return "No new messages.";
                        }
                        return "The laptop is not in the inventory";
                    case "myself":
                        state = ug.check("myself");
                        if (state == UpdateGame.DONE) {
                            if (map.getID() == 2) {
                                return shapeStringGenerator();
                            }
                            return "Make sure you are in front of the mirror.";
                        }
                        return "Make sure you put some perfume and brush your "
                                + "hair.";
                    default:
                        return unknowStringGenerator();
                }
            // end of checking

            case "smell":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("perfume")) {
                    if (kamina.hasObject(GameObject.PERFUME)) {
                        return "AWESOME!!!";
                    } else {
                        return "You are not holding some perfume in the"
                                + " inventory.";
                    }
                } else {
                    return unknowStringGenerator();
                }
            // end of smelling

            // AR: I have added the following cases

            case "put":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("perfume")) {
                    if (ug.put(GameObject.PERFUME) == UpdateGame.DONE) {
                        return "perfume dropped.";
                    }
                    return "you don't have it to be drop.";
                } else if (words[1].equals("bottle")) {
                    if (ug.put(GameObject.EMPTY_WATER_BOTTLE) == UpdateGame.DONE
                            || ug.put(GameObject.FILLED_WATER_BOTTLE) == UpdateGame.DONE) {
                        return "bottle droped dropped.";
                    }
                    return "you don't have it to drop it.";
                } else if (words[1].equals("brush")) {
                    if (ug.put(GameObject.BRUSH) == UpdateGame.DONE) {
                        return "Brush Dropped.";
                    }
                    return "you don't have it to drop it.";

                } else if (words[1].equals("laptop")) {
                    if (ug.put(GameObject.LAPTOP) == UpdateGame.DONE) {
                        return "Laptop Dropped.";
                    }
                    return "you don't have it to drop it";

                } else {
                    return unknowStringGenerator();
                }
            // end of putting objects.

            case "brush":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("hair")) {
                    state = ug.use(GameObject.BRUSH);
                    if (state == UpdateGame.DONE) {
                        return shapeStringGenerator();
                    } else {
                        return "You don't have a brush.";
                    }
                } else {
                    return unknowStringGenerator();
                }

            case "drink":
                if (words.length != 1) {
                    return unknowStringGenerator();
                }
                if (ug.drink() == UpdateGame.DONE) {
                    return "AAAAHH! I was very thirsty.";
                } else if (kamina.hasObject(GameObject.EMPTY_WATER_BOTTLE)) {
                    return "The bottle is empty.";
                }
                return "You are not holding bottles";
            // end of drinking.

            // AR: added the default case
            default:
                if (words[0].equals("north") && words.length == 1) {
                    if (map.getID() == 6) {
                        triggerNext = true;
                        return "";
                    } else if (map.whileInGoTo('n')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go north any further.";
                    }
                } else if (words[0].equals("south") && words.length == 1) {
                    if (map.whileInGoTo('s')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go south any further.";
                    }
                } else if (words[0].equals("east") && words.length == 1) {
                    if (map.whileInGoTo('e')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go east any further.";
                    }
                } else if (words[0].equals("west") && words.length == 1) {
                    if (map.whileInGoTo('w')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go west any further.";
                    }
                } else {
                    return unknowStringGenerator();
                }
        }

    }

    public String start() {
        return "";
    }

    public String end() {
        return "Me in the Exam sitting and nervous. I hate this kind of Air."
                + " My heart beats are increasing. I can feel it jumping out of"
                + " my chest. I feel like going to the bathroom but I can’t,"
                + " only five minutes left and I didn’t solve but three"
                + " questions from six questions. Oh it’s so hard to breath."
                + " My heart beats are going fast. I can’t control it anymore."
                + " At some point I looked at my clock it’s like it’s not"
                + " working anymore .it stopped. I can’t know the time now."
                + " Did the five remaining minutes left are over?  My heart"
                + " beats are going faster and faster I can’t hear an"
                + " individual beat to indicate how fast it is. So I gave up,"
                + " I think I am going to die if I continued looking at this"
                + " answer paper. So I stood up and went to the exam mentor to"
                + " give him my answer paper. But I think he didn’t see me"
                + " coming. I think everyone is moving so slow. Or, I am the"
                + " one who is moving so fast. I think I recognized the"
                + " situation.\r\n";
    }

    public String mission() {
        return "Your mission is to find the exam place.";
    }

    public boolean missionCompleted() {
        return false;
    }

    public Integer vertex() {
        return map.getID();
    }

    public String mapPath() {
        return "/Maps/Faculty/Faculty_" + vertex().toString() + ".png";
    }
}

/*
 *         switch (words[0]) {
            case "run":
                if (words.length != 2) { // AR: Adding a check
                    return unknowStringGenerator();
                }
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (map.whileInGoTo('n')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go north any further.";
                        }
                    case "south":
                        if (map.whileInGoTo('s')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go south any further.";
                        }
                    case "west":
                        if (map.whileInGoTo('w')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go west any further.";
                        }
                    case "east":
                        if (map.whileInGoTo('e')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go east any further.";
                        }
                    default:
                       return unknowStringGenerator();
                }
                // end of running
                
            case "hit":
                // AR: the following check is required.
                if (words.length != 3 && words.length != 4) {
                    return unknowStringGenerator();
                }
                // end of check
                // first check if gamer could use this action
                if (words[1].equals("with") || words[1].equals("by")) {
                    // check if he has the weapon (words[2])
                    // check if it's a legal use at the current status
                    switch (words[2]) {
                        case "feast": // AR: changed from Feast to feast
                            return "hit with feast based on game senario"
                                    + " , reply gamer with updates";
                        case "sword":
                            return "hit with sword based on game senario"
                                    + " , reply gamer with updates";
                        case "axe":
                            return "hit with axe based on game senario" 
                                    + " , reply gamer with updates";
                        case "bow":
                            return "hit with bow based on game senario"
                                    + " , reply gamer with updates";
                        default:
                            return unknowStringGenerator();

                    }
                    // update the ph of the enemy
                } else {
                    return unknowStringGenerator();
                }
                // end of hitting
                
            case "kill":
                // AR: the following check is required.
                if (words.length != 3 && words.length != 4) {
                    return unknowStringGenerator();
                }
                // end of check
                // first check if gamer could use this action
                if (words[1].equals("with") || words[1].equals("by")) {
                    // check if he has the weapon (words[2])
                    // check if it's a legal use at the current status
                    switch (words[2]) {
                        case "feast": // AR: changed from Feast to feast
                            return "kill with feast based on game senario"
                                    + " , reply gamer with updates";
                        case "sword":
                            return "kill with sword based on game senario" 
                                    + " , reply gamer with updates";
                        case "axe":
                            return "kill with axe based on game senario"
                                    + " , reply gamer with updates";
                        case "bow":
                            return "kill with bow based on game senario"
                                    + " , reply gamer with updates";
                        default:
                            return unknowStringGenerator();
                    }
                    // update the ph of the enemy
                } else {
                    return unknowStringGenerator();
                }
                // end of killing
                
            case "open":
                // AR: the following check is required.
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an open-able object in front of the player.
                switch (words[1]) { // AR: I have added the cases.
                    case "door":
                        return "Door opened.";
                    case "window":
                        return "Window Opened.";
                    case "laptop":
                        return "Laptop opened.";
                    case "bottle":
                        return "Bottle Opened.";
                    default:
                        return unknowStringGenerator();
                }
                //end of opening the object.
                
            case "close":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an close-able object in front of the player.
                switch (words[1]) {
                    case "door":
                        return "Door Closed.";
                    case "window":
                        return "Window Closed.";
                    case "laptop":
                        return "Laptop Closed.";
                    case "bottle":
                        return "Bottle Closed.";
                    default:
                        return unknowStringGenerator();
                }
                //end of closing the object.
                
            case "look":
                // AR: the following check is required
                if (words.length != 1) {
                    return unknowStringGenerator();
                }
                // end of check
                return map.getDescription();
                //end of looking the object.
                
            case "take":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an close-able object in front of the player.
                switch (words[1]) {
                    case "perfume":
                        return "Perfume Taken.";
                    case "brush":
                        return "Brush Taken.";
                    case "bottle":
                    case "glass":
                        return "Taken.";
                    case "paper":
                        return "Paper Taken.";
                    case "armor":
                        return "Armor Taken.";
                    case "laptop":
                        return "Laptop Taken.";
                    default:
                        return unknowStringGenerator();
                }
                //end of taking the object.
                
            case "use":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                //when there’s an close-able object in front of the player.
                switch (words[1]) {
                    case "perfume":
                        return "Perfume Used.";
                    default:
                        return unknowStringGenerator();
                }
                //end of using the object.
                
            case "go":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (map.whileInGoTo('n')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go north any further.";
                        }
                    case "south":
                        if (map.whileInGoTo('s')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go south any further.";
                        }
                    case "west":
                        if (map.whileInGoTo('w')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go west any further.";
                        }
                    case "east":
                        if (map.whileInGoTo('e')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go east any further.";
                        }
                    default:
                       return unknowStringGenerator();
                }
                // end of going
                // AR: I have added the following cases
            case "check":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                switch (words[1]) {
                    case "mail":
                        return "Mail Checked.";
                    case "myself":
                        return "Checking yourself?";
                    default:
                        return unknowStringGenerator();
                }
                // end of checking
                
            case "smell":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("perfume")) {
                    return "perfume smelled.";
                } else {
                    return unknowStringGenerator();
                }
                // end of smelling.
                
            case "put":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("perfume")) {
                    return "Perfume Dropped.";
                } else if (words[1].equals("bottle")) {
                    return "Bottle Dropped.";
                } else if (words[1].equals("paper")) {
                    return "Paper Dropped.";
                } else if (words[1].equals("brush")) {
                    return "Brush Dropped.";
                } else if (words[1].equals("laptop")) {
                    return "Laptop Dropped.";
                } else {
                    return unknowStringGenerator();
                }
                // end of putting.
                
            case "brush":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("hair")) {
                    return "Hair Brushed.";
                } else {
                    return unknowStringGenerator();
                }
                // end of brushing
                
            case "drink":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return unknowStringGenerator();
                }
                // end of drinking
                
            case "enter":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return "Kamina should enter";
                }
                // end of entering
                
            case "get":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("in")) {
                    return "getting in";
                } else if (words[1].equals("up")) {
                    return "getting up";
                } else if (words[1].equals("down")) {
                    return "getting down";
                } else if (words[1].equals("off")) {
                    return "getting off";
                } else {
                    return unknowStringGenerator();
                }
                // end of getting
                
            case "copy":
            case "download":
                if (words.length != 4) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("map") && words[1].equals("to")
                        && (words[1].equals("mobile")
                        || words[1].equals("phone"))) {
                    return "downloading the map to the phone.";
                } else {
                    return unknowStringGenerator();
                }
                // end of copying.
                
            case "yawn":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return "Yo!";
                }
                // end of yawning.
                
            case "speed":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("on")) {
                    return "speed mode activated.";
                } else if (words[1].equals("off")) {
                    return "speed mode deactivated.";
                } else {
                    return unknowStringGenerator();
                }
                // end of speed mode.
                
            case "punch":
                if (words.length != 1 && words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words.length == 2) {
                    if (words[1].equals("face")) {
                        return "punching in the face.";
                    } else {
                        return unknowStringGenerator();
                    }
                }
                // end of punching
                
            case "kick":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return "kicking your ass.";
                }
                // end of kicking
                
            case "slap":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return "slapping in the face.";
                }
                // end of slapping
                
            case "pull":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("hair")) {
                    return "Hair Pulled.";
                } else {
                    return unknowStringGenerator();
                }
                // end of pulling
                
            case "hold":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("hand")) {
                    return "Hand Held.";
                } else {
                    return unknowStringGenerator();
                }
                // end of holding
                
            case "break":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("bone")) {
                    return "Bone Broken.";
                } else if (words[1].equals("in")) {
                    return "Broke in.";
                } else {
                    return unknowStringGenerator();
                }
                // end of break
                
            case "release":
                if (words.length != 1) {
                    return unknowStringGenerator();
                }
                return "Released.";
                // end of releasing
                
            case "dodge":
            case "block":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return "dodged.";
                }
                // end of blocking
                
            case "ride":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("bicycle")) {
                    return "Bicycle ride.";
                } else {
                    return unknowStringGenerator();
                }
                // end of riding
                
            case "knock":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("door")) {
                    return "door knocked.";
                } else {
                    return unknowStringGenerator();
                }
                // end of knocking
                
            case "read":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("paper")) {
                    return "Paper read.";
                } else {
                    return unknowStringGenerator();
                }
                // end of reading
                
            case "lights":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("on")) {
                    return "turning lights on";
                } else if (words[1].equals("off")) {
                    return "turning lights off";
                } else {
                    return unknowStringGenerator();
                }
                // end of lighting
                
            case "sprint":
                if (words.length != 3) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("and") && words[1].equals("hit")) {
                    return "End of sprinting";
                } else {
                    return unknowStringGenerator();
                }
                // end of sprinting
                
            case "computer":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("on")) {
                    return "Computer On";
                } else if (words[1].equals("off")) {
                    return "Computer Off.";
                } else {
                    return unknowStringGenerator();
                }
                // end of computing
                
            case "type":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("\"XCTHKLA\"")) {
                } else {
                    return unknowStringGenerator();
                }
            case "prepare":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("swords")) {
                    return "Swords prepared.";
                } else {
                    return unknowStringGenerator();
                }
                // end of preparing
                
            case "sheath":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("swords")) {
                    return "Swords Sheathed.";
                } else {
                    return unknowStringGenerator();
                }
                // end of sheathing
                
            case "war":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("cry")) {
                    return "I have just cried the war cry :)";
                } else {
                    return unknowStringGenerator();
                }
                // end of war cry
                
            // AR: added the default case
            default:
                if (words[0].equals("north") && words.length == 1) {
                    if (map.whileInGoTo('n')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go north any further.";
                    }
                } else if (words[0].equals("south") && words.length == 1) {
                    if (map.whileInGoTo('s')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go south any further.";
                    }
                } else if (words[0].equals("east") && words.length == 1) {
                    if (map.whileInGoTo('e')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go east any further.";
                    }
                } else if (words[0].equals("west") && words.length == 1) {
                    if (map.whileInGoTo('w')) {
                        return map.getDescription();
                    } else {
                        return "You cannot go west any further.";
                    }
                } else {
                    return unknowStringGenerator();
                }
        }
 */