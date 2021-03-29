/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Characters.Kamina;
import Execution.UpdateGame;
import GameObjects.*;
import GraphWork.Map;
import Helpers.DLinkedList;
import java.io.IOException;
import java.util.Random;
/*
 * 1. change the map txt file input
 * 2. change the map image file input
 */

/**
 *
 * @author joker
 */
public class ChapterEightParser { // Nia's Department

    private UpdateGame ug;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    public boolean triggerNext;
    private boolean read;
    private Random randomGenerator;
    private Map map;
    private short niaScore;
    private short akiliScore;
    private Kamina kamina;
    private boolean missionCompleteSignal;

    public ChapterEightParser(Kamina kamina) { // Nia's Department
        triggerNext = false;
        niaScore = 10;
        akiliScore = 10;
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
        // ------------------------
        randomGenerator = new Random();
        try {
            map = new Map("Maps/Nia Department.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        kamina.hp = 100;
        missionCompleteSignal = false;
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
                    case "laptop":
                        state = ug.open(GameObject.LAPTOP);
                        if (state == UpdateGame.NOT_DONE) {
                            return "I don't have the laptop in the inventory.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The laptop is already opened.";
                        }
                        return "Laptop opened.";
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
                // end of check
                //when there’s an close-able object in front of the player.
                switch (words[1]) {
                    case "perfume":
                        state = ug.take(GameObject.PERFUME);
                        if (state == UpdateGame.DONE) {
                            return "Perfume taken";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "I do have perfume bottle.";
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
                    case "glass":
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
                    case "paper":case "akili":case"nia":
                        short paper;
                        if (map.checkObject(GameObject.AKILI_PAPER)) {
                            paper = GameObject.AKILI_PAPER;
                        } else if (map.checkObject(GameObject.NIA_PAPER)) {
                            paper = GameObject.NIA_PAPER;
                        } else {
                            return "No Papers around";
                        }
                        state = ug.take(paper);
                        if (state == UpdateGame.DONE) {
                            // adding the paper's msg:
                            // Nia's paper
                            // end of Akili's paper
                            // end of adding papers' msgs.
                            if(paper == GameObject.AKILI_PAPER){
                                return "akili paper taken. You can read the paper by "
                                    + "typing 'read paper'. It's prefered to"
                                    + "drop the paper after reading it. Just"
                                    + " for your sake.";
                            } else {
                                return "nia paper taken. You can read the paper by "
                                    + "typing 'read paper'. It's prefered to"
                                    + "drop the paper after reading it. Just"
                                    + " for your sake.";
                            }
                            
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "You cannot hold more than a paper at a time.";
                        }
                        return "No papers around";
                    case "akili paper":
                        
                    case "laptop":
                        state = ug.take(GameObject.LAPTOP);
                        if (state == UpdateGame.DONE) {
                            return "Laptop is added to the inventory.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "The inventory already holds a laptop.";
                        }
                        return "Laptop?!! what laptop? Nothing here!!";
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

            case "put":
            case "drop":
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
                } else if (words[1].equals("paper")) {
                    if (ug.put(GameObject.AKILI_PAPER) == UpdateGame.DONE) {
                        return "paper dropped.";
                    }
                    return "you don't have it to be dropped.";
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
            // end of putting.

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
            // end of drinking

            case "read":
                if (words.length > 3) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("nia")) {
                    if (ug.read(23) == UpdateGame.DONE) {
                        Paper paper = (NiaPaper) kamina.getObject(GameObject.NIA_PAPER);
                        if (read) {
                            return "Owner: " + paper.owner + "\n"
                                    + "Message: " + paper.msg
                                    + "------------\n"
                                    + "I remembered what Nia told me “Sometimes, "
                                    + "darkness is the key to see things "
                                    + "clearer”.\nHint: turn off the lights\nu can turn them off by typing lights off";
                        }
                        read = true;
                        return "Owner: " + paper.owner + "\n"
                                + "Message: " + paper.msg;
                    }
                    return "You are not holding any papers!!";
                } else if (words[1].equals("akili")) {
                    if (ug.read(24) == UpdateGame.DONE) {
                        Paper paper = (AkiliPaper) kamina.getObject(GameObject.AKILI_PAPER);
                        if (read) {
                            return "Owner: " + paper.owner + "\n"
                                    + "Message: " + paper.msg
                                    + "------------\n"
                                    + "I remembered what Nia told me “Sometimes, "
                                    + "darkness is the key to see things "
                                    + "clearer”.\nHint: turn off the lights/nu can turn them off by typing lights off";
                        }
                        read = true;
                        return "Owner: " + paper.owner + "\n"
                                + "Message: " + paper.msg;
                    }
                    return "You are not holding any papers!!";
                } else if (words[1].equals("paper")) {
                    return "which paper?";
                } else {
                    return unknowStringGenerator();
                }
            // end of reading

            case "lights":
            case "turn":
            case "switch":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("on")) {
                    state = ug.lightsON();
                    if (state == UpdateGame.DONE) {
                        return "turning lights on";
                    } else if (state == UpdateGame.ALREADY_DONE) {
                        return "The lights are on already.";
                    }
                    return "No switches here.";
                } else if (words[1].equals("off")) {
                    state = ug.lightsOFF();
                    if (state == UpdateGame.DONE) {
                        triggerNext = true;
                        return "turning lights off";
                    } else if (state == UpdateGame.ALREADY_DONE) {
                        return "The lights are off already";
                    }
                    return "No switches here";
                } else {
                    return "if you want to turn the lights off use lights off or turn off";
                }
            // end of lighting         

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

    public String start() {
        return "It’s morning and Kamina has just waked up. It’s been a month "
                + "and a week since that “big event” occurred in his life.  "
                + "Nia has helped him to completely master his heart, so he "
                + "can use his speed whenever he needs. However, It’s been a "
                + "week since he last met Nia. She hasn’t attended any of the "
                + "courses lectures this week.lectures this week. “Is she sick "
                + "or something?” I asked myself. “No! No! No! Those kind of crea"
                + "tures don’t get sick. Only weak people get sick.” That was a c"
                + "onvincible reason for me!! “If so, where is Nia now? And why s"
                + "he’s not attending the courses lectures?!” \n" 
                +"In the past month, he is trained by Nia almost every day. That "
                + "was his opportunity to know more about her to answer some of"
                + " the questions in his mind. Sometimes, she went with him to s"
                + "ome restaurant to have dinner. Sometimes, they went walking t"
                + "alking about various aspects of their lives. He knew that she"
                + " has a sister called Akili. Nia hasn’t talked a lot about Akil"
                + "i. However, Kamina felt something weird about her sister. knock knock knock..\n" +
                "Kamina is knocking Nia’s door. It’s been a minute and no one an"
                + "swers. He knocks her door again and waits again. Yet no one a"
                + "nswers. He starts trying to open the door. “It’s unlocked” he "
                + "wished to say that, but unfortunately it’s locked. “That’s eas"
                + "y task”. “SPEED ON”, the pelt starts generating the sound wave"
                + "s. His heart beats are now faster. Everything is moving slowl"
                + "y. BOOOM… He hits the door hard and breaks in. “SPEED OFF”, “"
                + "I don’t want to lose my life for opening a door.” ";
    }

    public String end() { // end of part 7 in the story
        return "After 1 minute, my eyes get used to the darkness. I started movi"
                + "ng in really quite scene waiting for something unusual in thi"
                + "s darkness. Finally I found some really weak violet lights co"
                + "ming from one of the rooms. So I tracked the lights into this"
                + " room. I found it coming from cracks in some of the room walls."
                + " I tried searching for some secret way to open this wall but "
                + "I couldn’t find it. Well I will go in the hard way. “Speed on”."
                + " Yeah, I sprinted and then I hit the wall with my leg. This "
                + "wall is really harder than I thought but the last month training "
                + "made my body very tough so I made a little damage. I started "
                + "hitting the wall many times until I broke it. I am in. “speed "
                + "off” wow what is that, I saw a lot of different ancient weapons"
                + " hanging on the wall of the room which have a violet lamp."
                + " I found a note on a table its title “hell manual” I read the "
                + "first section of it “this manual is made for contractors and"
                + " abnormal people. If you are not one, I recommend you leave"
                + " the room in 45 seconds from opening this note or the room will"
                + " get destroyed. If you are strong enough you will find a "
                + "red spot in the wall in front of you, hit it with all the "
                + "strength you have” I said really quickly “speed on” and I ran"
                + " towards the wall and hit it with my hand with all the "
                + "strength I got. I heard some sounds in all the room. It’s like"
                + " there are machines calculating my strength. And then quite "
                + "again I came back to the note I continued reading “If you "
                + "are still alive, then you passed the test, please read the "
                + "next page. You will find the table of contents. MAY YOU FIND "
                + "WHAT YOU SEEK” I started reading the table of contents. I "
                + "read a lot of weird topics but what really attracted my a"
                + "ttention “How to go to the hell” I opened this section “If "
                + "you want to go to the hell you need an armor from type heat "
                + "isolating that’s the most important thing you will need. After "
                + "getting it now the hard part the room you are in now it’s "
                + "inside a big machine this machine can open a portal to gate "
                + "of the hell where u will meet your final test before going "
                + "to the hell to activate the machine say this code “COMPUTER ON”"
                + " and then write in that computer this password XCTHKLA when "
                + "you press return the portal will open for 30 seconds, MAY YOU"
                + " FIND WHAT YOU SEEK” first I will have to find an armor. I "
                + "found a door with a blue button. I couldn’t resist clicking "
                + "the button. I found the door come out of the wall. What? "
                + "It’s a part of glass box which have a lot of different armors "
                + "and there is a notes on each armor and some manual of what "
                + "is the best weapons will act really good with it. I found "
                + "some armor that needs two swords. Well I couldn’t resist it "
                + "I start wearing it wow it’s really heavy. What materials did "
                + "they use to make it? But I got used to it thanks to the tough "
                + "training Nia gave me. I take two swords from these ancient "
                + "weapons. I hope there’s some mirror in this room to see how I "
                + "look like. Now time to go to hell. I said   “COMPUTER ON” I "
                + "heard some weird voices again some square of the room began "
                + "to move. Wow it’s a door. I found a huge computer coming out"
                + " of ground. I started typing the password on the computer I"
                + " prepared to click on the return button. It took from me "
                + "like 1 minute to click on it. Click ….. The ground started "
                + "shaking the room door started closing. I think that’s my "
                + "last chance to change my mind about what I am doing. But no "
                + "I don’t want to go back to my ignorant life again. I stayed "
                + "at the room. After a while four guns came out of the four "
                + "corners of the room and a really strong laser came out of the"
                + " guns. Their lasers met at one point and then portals get formed "
                + "in the middle of the room. Then I put my hand in this portal ."
                + "I felt really cold, well I didn’t expect to go really to the "
                + "hell. It must be a town called hell or something. I entered my"
                + " whole body I felt really weird feeling. Good bye Earth.";
    }

    public String mission() {
        return "Find Nia";
    }

    public Integer vertex() {
        return map.getCurrentPosition();
    }

    public boolean missionComplete() {
        return missionCompleteSignal;
    }

    public String mapPath() {
        return "/Maps/Nia_Home/Nia Department_" + vertex().toString() + ".jpg";
    }
}