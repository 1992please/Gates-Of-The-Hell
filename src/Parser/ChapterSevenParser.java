/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Characters.Kamina;
import Execution.UpdateGame;
import GraphWork.Map;
import Helpers.DLinkedList;
import java.util.Random;

/**
 *
 * @author joker
 */
public class ChapterSevenParser {

    private UpdateGame ug;
    public boolean triggerNext;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;
    private boolean missionCompleteSignal;

    public ChapterSevenParser(Kamina kamina) { // in the building in the fight
        //  sight --> canceled
//        triggerNext = false;
//        unknownString = new DLinkedList();
//        shapeString = new DLinkedList();
//        unknownString.insertLast("I don't understand that");
//        unknownString.insertLast("Please rephrase that");
//        unknownString.insertLast("Undefined Input");
//        unknownString.insertLast("Uknown Input");
//        unknownString.insertLast("Bitch Please, I don't get what you have just"
//                + " said");
//        unknownString.insertLast("Kozbara!! Re-enter that again in different"
//                + " format");
//        unknownString.insertLast("WTF? Do you know the list of instructions?");
//        unknownString.insertLast("Dude, R u retarded?");
//        unknownString.insertLast("Please save your time and go read the manual.");
//        // ------------------------
//        shapeString.insertLast("AAAAAHHH! you are better than ever.");
//        shapeString.insertLast("That's much better.");
//        shapeString.insertLast("YEAH! That's what I am talking about.");
//        shapeString.insertLast("Me Gusta");
//        shapeString.insertLast("I am sexy and I know it");
//        shapeString.insertLast("I am the man!!");
//        // ------------------------
//        randomGenerator = new Random();
//        try {
//            map = new Map("Maps/building_NiaFightPlace.txt", 0);
//        } catch (IOException ex) {
//            return;
//        }
//        this.kamina = kamina;
//        missionCompleteSignal = false;
    }

//    private String unknowStringGenerator() {
//        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
//    }
//
//    private String shapeStringGenerator() {
//        return shapeString.getItemAt(randomGenerator.nextInt(shapeString.size())).toString();
//    }
//    public String parse(String line) {
//
//        // splitting the string
//        String[] words = line.split(" +"); // AR: fixed the regex
//
//        // AR: the above command will result in empty string element at the
//        // beginning of the array if spaces were added
//        if (words[0].equals("")) {
//            String[] temp = new String[words.length - 1];
//            int counter = 1;
//            while (counter < words.length) {
//                temp[counter - 1] = words[counter];
//                counter += 1;
//            }
//            words = temp;
//        } else {
//            int counter = 0;
//            while (counter < words.length) {
//                words[counter] = words[counter].toLowerCase();
//                counter += 1;
//            }
//        }
//        // AR: end of empty string removal
//
//        if (words.length < 1 || words.length > 4) {
//            return unknowStringGenerator();
//        }
//        short state;
//        switch (words[0]) {
//
//            case "open":
//                // AR: the following check is required.
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                // end of check
//                //when there’s an open-able object in front of the player.
//                switch (words[1]) { // AR: I have added the cases.
//                    case "door":
//                        return "Door opened.";
//                    case "window":
//                        return "Window Opened.";
//                    case "laptop":
//                        return "Laptop opened.";
//                    case "bottle":
//                        return "Bottle Opened.";
//                    default:
//                        return unknowStringGenerator();
//                }
//            //end of opening the object.
//
//            case "close":
//                // AR: the following check is required
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                // end of check
//                //when there’s an close-able object in front of the player.
//                switch (words[1]) {
//                    case "door":
//                        return "Door Closed.";
//                    case "window":
//                        return "Window Closed.";
//                    case "laptop":
//                        return "Laptop Closed.";
//                    case "bottle":
//                        return "Bottle Closed.";
//                    default:
//                        return unknowStringGenerator();
//                }
//            //end of closing the object.
//
//            case "look":
//                // AR: the following check is required
//                if (words.length != 1) {
//                    return unknowStringGenerator();
//                }
//                // end of check
//                return map.getDescription();
//            //end of looking the object.
//
//            case "take":
//                // AR: the following check is required
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                // end of check
//                //when there’s an close-able object in front of the player.
//                switch (words[1]) {
//                    case "perfume":
//                        return "Perfume Taken.";
//                    case "brush":
//                        return "Brush Taken.";
//                    case "bottle":
//                    case "glass":
//                        return "Taken.";
//                    case "paper":
//                        return "Paper Taken.";
//                    case "armor":
//                        return "Armor Taken.";
//                    case "laptop":
//                        return "Laptop Taken.";
//                    default:
//                        return unknowStringGenerator();
//                }
//            //end of taking the object.
//
//            case "use":
//                // AR: the following check is required
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                // end of check
//                //when there’s an close-able object in front of the player.
//                switch (words[1]) {
//                    case "perfume":
//                        return "Perfume Used.";
//                    default:
//                        return unknowStringGenerator();
//                }
//            //end of using the object.
//
//            case "go":
//                // AR: the following check is required
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                // end of check
//                switch (words[1]) {
//                    // you should first check the state of gamer allow him to use this action
//                    case "north":
//                        if (map.whileInGoTo('n')) {
//                            return map.getDescription();
//                        } else {
//                            return "You cannot go north any further.";
//                        }
//                    case "south":
//                        if (map.whileInGoTo('s')) {
//                            return map.getDescription();
//                        } else {
//                            return "You cannot go south any further.";
//                        }
//                    case "west":
//                        if (map.whileInGoTo('w')) {
//                            return map.getDescription();
//                        } else {
//                            return "You cannot go west any further.";
//                        }
//                    case "east":
//                        if (map.whileInGoTo('e')) {
//                            return map.getDescription();
//                        } else {
//                            return "You cannot go east any further.";
//                        }
//                    default:
//                        return unknowStringGenerator();
//                }
//            // end of going
//            // AR: I have added the following cases
//
//            case "smell":
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                if (words[1].equals("perfume")) {
//                    return "perfume smelled.";
//                } else {
//                    return unknowStringGenerator();
//                }
//            // end of smelling.
//
//            case "put":
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                if (words[1].equals("perfume")) {
//                    return "Perfume Dropped.";
//                } else if (words[1].equals("bottle")) {
//                    return "Bottle Dropped.";
//                } else if (words[1].equals("paper")) {
//                    return "Paper Dropped.";
//                } else if (words[1].equals("brush")) {
//                    return "Brush Dropped.";
//                } else if (words[1].equals("laptop")) {
//                    return "Laptop Dropped.";
//                } else {
//                    return unknowStringGenerator();
//                }
//            // end of putting.
//
//            case "brush":
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                if (words[1].equals("hair")) {
//                    return "Hair Brushed.";
//                } else {
//                    return unknowStringGenerator();
//                }
//            // end of brushing
//
//            case "drink":
//                if (words.length != 1) {
//                    return unknowStringGenerator();
//                } else {
//                    return unknowStringGenerator();
//                }
//            // end of drinking
//
//            case "leave":
//                if (words.length != 1) {
//                    return unknowStringGenerator();
//                } else {
//                    return "Kamina should leave the building";
//                }
//            // end of entering
//
//            case "get":
//                if (words.length != 2) {
//                    return unknowStringGenerator();
//                }
//                if (words[1].equals("out")) {
//                    return "getting out. That is, leaving";
//                } else if (words[1].equals("up")) {
//                    return "getting up";
//                } else if (words[1].equals("down")) {
//                    return "getting down";
//                } else {
//                    return unknowStringGenerator();
//                }
//            // end of getting
//
//            case "yawn":
//                if (words.length != 1) {
//                    return unknowStringGenerator();
//                } else {
//                    return "Yo!";
//                }
//            // end of yawning.
//
//            // AR: added the default case
//            default:
//                if (words[0].equals("north") && words.length == 1) {
//                    if (map.whileInGoTo('n')) {
//                        return map.getDescription();
//                    } else {
//                        return "You cannot go north any further.";
//                    }
//                } else if (words[0].equals("south") && words.length == 1) {
//                    if (map.whileInGoTo('s')) {
//                        return map.getDescription();
//                    } else {
//                        return "You cannot go south any further.";
//                    }
//                } else if (words[0].equals("east") && words.length == 1) {
//                    if (map.whileInGoTo('e')) {
//                        return map.getDescription();
//                    } else {
//                        return "You cannot go east any further.";
//                    }
//                } else if (words[0].equals("west") && words.length == 1) {
//                    if (map.whileInGoTo('w')) {
//                        return map.getDescription();
//                    } else {
//                        return "You cannot go west any further.";
//                    }
//                } else {
//                    return unknowStringGenerator();
//                }
//        }
//    }
    public String start() {
        return "I slept a lot. I started opening my eyes really easily, because"
                + " the place around me was dark. I think I am afraid that I"
                + " know where I am. I am in the wooden building that was close"
                + " to the site."
                + " I started walking out of this place while trying to discover"
                + " the place. I found Nia setting beside a bonfire. She looked"
                + " at me and said “don’t be afraid. I only wanted to test you, "
                + "I never intended to kill you “I didn’t know what to say but "
                + "“it’s weird that at some point I enjoyed the fight” .she "
                + "laughed and said “well I am not surprised anyway it’s in "
                + "your blood, you are born with this”. I said “well, I don’t "
                + "get what you mean”. \n"
                + "She said “you have the ability to control the speed of your"
                + " heart beats to make yourself really fast” I said “well, how"
                + " about you? I suppose you have the same ability”";
    }

    public String end() {
        return "Nia said “no, it’s just my own body raw strength because of the "
                + "training I had for the past 15 years. I will start training "
                + "you for the next month to make you master your ability. Now "
                + "take this pelt it will help control your ability and it’s "
                + "voice activated. When you want to activate it just say "
                + "“speed on” and it will produce a high frequency sound waves "
                + "in your body that will make your heart beat really fast” I "
                + "said “how about making my heart back to normal” Nia said "
                + "“just say speed off”. “Now I will have to go, see you in the "
                + "faculty” I said. “When I will see you again “.she answered “I "
                + "will tell in the faculty”. She left me wondering about a lot "
                + "of stuff like who is she and why she is going to help me and "
                + "how she knows about my ability in the first place and why "
                + "she is trained for 15 years. I looked at my clock to see "
                + "the time. Oh crap it’s 10 pm I will have to go home to do "
                + "my homework and I have to sleep at 12 pm. It will take like "
                + "two hours to go again with my bicycle to home. I think I "
                + "will take it as chance to try the control device. I put on "
                + "the pelt with the speed controller. And I said “speed on”. "
                + "I heard a peep voice coming from the pelt then I started "
                + "feeling my heart beats going really quickly.  I started "
                + "pressing on the pedal really fast. I finished like two miles "
                + "in 30 seconds. After I finished half of the distant the "
                + "pedal got broken. Then I started running .I started feeling "
                + "a great pain in my heart. I think that’s my limit in using "
                + "speed on mode. I said “speed off” I heard another peep "
                + "coming from the pelt and my heart started calming down "
                + "and I had to go home walking, and good bye for finishing "
                + "my homework.";
    }

    public Integer vertex() {
        return map.getCurrentPosition();
    }

    public String mission() {
        return "No Mission!!";
    }

    public boolean missionComplete() {
        return missionCompleteSignal;
    }

    public String mapPath() {
        return "/Maps/Building_NiaFightPlace/Building_NiaFightPlace_"
                + vertex().toString() + ".png";
    }
}
