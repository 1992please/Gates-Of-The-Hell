/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 1. add the execution to the parser
 * 2. add merge get and take for the approperiate objects.
 * 3. 
 */
package Parser;

import Helpers.*;
import java.util.Random;
import GraphWork.*;
import java.io.IOException;
import Characters.Kamina;
import Execution.*;
import GameObjects.GameObject;

/**
 *
 * @author joker
 */
public class ChapterOneParser {
    
    
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private Random randomGenerator;
    public boolean triggerNext;
    private Map map;
    private short brushScore;
    private short perfumeScore;
    Kamina kamina;
    // AR's way in implementing the execution
    UpdateGame ug;

    public ChapterOneParser(Kamina kamina) { // kamina is in his room --> he 
        // should put on his pants.
        brushScore = 20;
        perfumeScore = 15;
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
            map = new Map("Maps/kamina_room.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        ug = new UpdateGame(kamina, map);
    }

    private boolean missionCompleted() {
        return kamina.wearPants;
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
        // end of check

        short state;
        switch (words[0]) {

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
            // end of looking

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
                        state = ug.take(GameObject.PANTS);
                        if (state == UpdateGame.DONE) {
                            return "Pants taken. Wear the pants to complete "
                                    + "your mission. Type 'wear pants' to "
                                    + "wear them.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "You are holding the pants!!";
                        }
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
                            kamina.score += perfumeScore;
                            perfumeScore = 0;
                            return shapeStringGenerator();
                        }
                        return unknowStringGenerator();
                    case "brush":
                        state = ug.use(GameObject.BRUSH);
                        if (state == UpdateGame.DONE) {
                            kamina.score += brushScore;
                            brushScore = 0;
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
            // end of going.

            case "wear":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if ("pants".equals(words[1])) {
                    if (kamina.hasObject(19)) {
                        kamina.wearPants = true;
                        kamina.score += 10;
                        kamina.removeObject(19);
                        return "now you can go to the faculty \n "
                                + "your first mision has been completed "
                                + "successufully \n"
                                + "when you are ready to leave the room type "
                                + "'leave' or 'leave room' ";
                    } else {
                        return "you must have pants in your inventory first";
                    }
                }

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
                        kamina.score += brushScore;
                        brushScore = 0;
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

            case "leave":
                if (missionCompleted()) {
                    triggerNext = true;
                    return "";
                }
                return "you cann't leave until you wear your pants";
            // end of leaving

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
    }

    public Integer vertex() {
        return map.getID();
    }

    public String start() {
        return "Hi, I am a student in engineering faculty, having a lot of pro"
                + "blems, or what I thought they were.\n"
                + "today, I woke up at voice saying “wake up, wake up Kamina”"
                + " so I ignored it and I heard it again and again, something"
                + " in my mind told me that If I didn’t wake up it will keep"
                + " annoying me forever so I said with a loud voice “ok, ok I "
                + "am waking up, leave me alone” so I got up and looked at my"
                + " unorganized room. I am trying to find my pants.";
    }

    public String end() {
        if (!kamina.hearBrushed) {
            return "... and finally I found my pants. So I left the home and I got"
                    + " shocked, oh I forgot to brush my hair. But I had an "
                    + "exam at that day and I was late.";
        } else {
            return "... and finally I found my pants. So I left the home. I had to"
                    + "hurry; since I had an "
                    + "exam at that day and I was late.";
        }
    }

    public String mission() {
        return "Your mission is to get prepared for going to the faculty, "
                + "put on your pants!!";
    }

    public String mapPath() {
        return "/Maps/Kamina/Kamina_" + vertex().toString() + ".png";
    }

    public String mp3Path() {
        return "";
    }
}