/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Characters.Kamina;
import Execution.UpdateGame;
import GameObjects.GameObject;
import GraphWork.Map;
import Helpers.DLinkedList;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author joker
 */
public class ChapterFiveParser { // back home for downloading the map

    private UpdateGame ug;
    private short brushScore;
    private short perfumeScore;
    private short mapDownload;
    public boolean triggerNext;
    private boolean mailChecked;
    private boolean downloaded;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;

    public ChapterFiveParser(Kamina kamina) {
        brushScore = 20;
        perfumeScore = 15;
        triggerNext = false;
        mailChecked = false;
        downloaded = false;
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
        shapeString.insertLast("I am sexy and I know it.");
        shapeString.insertLast("That's much better.");
        shapeString.insertLast("YEAH! That's what I am talking about.");
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
        kamina.objectLists.clear();
        map.getObject(GameObject.PANTS); // removing the pants from this chapter
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
                            return "Perfume taken. You are going to meet Nia, I"
                                    + "think it's a good idea to put some perfume.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "I do have perfume.";
                        }
                        return "I can see no perfume bottles here.";

                    case "brush":
                        state = ug.take(GameObject.BRUSH);
                        if (state == UpdateGame.DONE) {
                            return "Brush Taken. You can make yourself look "
                                    + "better. It's recommended since you are"
                                    + "going to meet Nia.";
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
                            return "Laptop is added to the inventory. Don't "
                                    + "forget to check the mail by 'check mail'.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The inventory already holds a laptop.";
                        }
                        return "Laptop?!! what laptop? Nothing here!!";

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
                            if (missionCompleted()) {
                                return shapeStringGenerator()
                                        + "\nYou can leave the map right away.";
                            }
                            return shapeStringGenerator();
                        }
                        return unknowStringGenerator();
                    case "brush":
                        state = ug.use(GameObject.BRUSH);
                        if (state == UpdateGame.DONE) {
                            kamina.score += brushScore;
                            brushScore = 0;
                            if (missionCompleted()) {
                                return shapeStringGenerator()
                                        + "\nYou can leave the map right away.";
                            }
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
                            if(map.checkObject(GameObject.PANTS)) {
                                map.getObject(GameObject.PANTS);
                            }
                            return map.getDescription();
                        } else {
                            return "You cannot go east any further.";
                        }
                    default:
                        return unknowStringGenerator();
                }
            // end of going.

            // AR: I have adde the following cases
            case "check":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                switch (words[1]) {
                    case "mail":
                        state = ug.check("mail");
                        if (state == UpdateGame.DONE) {
                            mailChecked = true;
                            return "7 new messages. One from them is from Nia. "
                                    + "You don't have the time to check'em all."
                                    + " Nia's message contains an attachment"
                                    + " which is a map describing the meeting"
                                    + " place. You need to download this map to"
                                    + "the cell phone. Type 'download map to"
                                    + " mobile' to"
                                    + "download!!";
                        } else if (kamina.hasObject(GameObject.LAPTOP)) {
                            return "Of course, you need to open the laptop "
                                    + "before checking the mail.";
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

            case "copy":
            case "download":
                if (words.length > 4) {
                    return unknowStringGenerator();
                }
                if (mailChecked && kamina.hasObject(GameObject.LAPTOP)) {
                    downloaded = true;
                    if (missionCompleted()) {
                        return "Map Downloaded."
                                + "\nYou can leave the map right away.";
                    }
                    kamina.score += mapDownload;
                    mapDownload = 0;
                    return "Map downloaded.";
                } else if (mailChecked) {
                    return "Where did you put the laptop?";
                } else if (kamina.hasObject(GameObject.LAPTOP)) {
                    return "Please check the mail.";
                }
                return "You neither have the laptop nor checked the mail.";
            // end of copying.

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
                return "you cann't leave until you have the map to the nia place"
                        + "and make sure that you are in good shape.";
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
                        if (map.checkObject(GameObject.PANTS)) {
                            map.getObject(GameObject.PANTS);
                        }
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
        return "Ok I wake up 6 am at morning, well I am kind of excited today."
                + " I will know how to solve my heart problem. Beside that’s "
                + "the first time that I will meet a girl away from faculty "
                + "and work life. I have to be in a good shape. I dressed "
                + "really good, brushed my hear and I put some perfume. "
                + "Wait a minute I forgot something important she didn’t "
                + "tell the place besides I don’t have even her phone number. "
                + "How stupid I am? I get disappointed I think I will check my mail";
    }

    public String end() {
        return "after downloading it I found it contains a map that describes"
                + " the place. After some thinking I found that the location"
                + " is kind of near to my house.  So, I left home, took my "
                + "bicycle and started to take my way there. “How the hell did"
                + " she know about my heart problem? More importantly, why she"
                + " told me not to have my breakfast!!!”…\n"
                + "Five minutes later, I started to feel that I am at a "
                + "strange place. No more houses I can find around me. It’s "
                + "kind of strange to be away from the urban life. I stopped "
                + "to take my breath and look at my map; I found it’s so close."
                + " It must be here. I left the bicycle and started wondering "
                + "around looking for Nia for couple minutes. I found her "
                + "standing so I called her” hey, Nia!! , here you are”";
    }

    public Integer vertex() {
        return map.getCurrentPosition();
    }

    public String mission() {
        return "Download the map onto the mobile. Make sure to be in the form"
                + " by brushing your hair and putting some perfume.";
    }

    public boolean missionCompleted() {
        return kamina.hearBrushed && kamina.putPerfume && downloaded;
    }

    public String mapPath() {
        return "/Maps/Kamina/Kamina_" + vertex().toString() + ".png";
    }
}
