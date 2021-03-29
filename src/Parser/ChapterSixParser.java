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
public class ChapterSixParser {

    private UpdateGame ug;
    private int countDown;
    public boolean triggerNext;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private DLinkedList cowrdString;
    private DLinkedList fightString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;
    private boolean missionCompleteSignal;

    public ChapterSixParser(Kamina kamina) { // Nia Fight Place
        triggerNext = false;
        countDown = 0;
        unknownString = new DLinkedList();
        shapeString = new DLinkedList();
        cowrdString = new DLinkedList();
        fightString = new DLinkedList();
        kamina.hp -= 10;
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
        cowrdString.insertLast("Nia is shouting don't run coward");
        cowrdString.insertLast("Nia is saying 'all you can do is running'");
        cowrdString.insertLast("Nia is running really fast behind me, she make me fall on the ground");
        cowrdString.insertLast("\"man stop being a pussy and fight like a man\"nia said while hiting me");
        //-----------------------------------------
        fightString.insertLast("Nia dodgeed the hit and give you a good hit in your stomach");
        fightString.insertFirst("Nia blocked the hit and say \"good student you didn't eat as I told you\"");
        fightString.insertLast("she disappeared from my sight and hit me in my back and say \"well not fast enough\"");
        fightString.insertLast("I couldn't even touch her, she is so fast and is saying \"not fast enough\" ");

        randomGenerator = new Random();
        try {
            map = new Map("Maps/NiaFightPlace.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        missionCompleteSignal = false;
    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }

    private String shapeStringGenerator() {
        return shapeString.getItemAt(randomGenerator.nextInt(shapeString.size())).toString();
    }

    private String cowrdStringGenerator() {
        return cowrdString.getItemAt(randomGenerator.nextInt(cowrdString.size())).toString();
    }

    private String fightStringGenerator() {
        return fightString.getItemAt(randomGenerator.nextInt(fightString.size())).toString();
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
                if (words.length == 1) {
                    if (kamina.hp > 20) {
                        kamina.hp -= 15;
                        return cowrdStringGenerator();

                    } else {
                        triggerNext = true;
                        return "";
                    }
                } else if (words.length != 2) { // AR: Adding a check
                    return unknowStringGenerator();
                }
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('n') && kamina.hp > 10) {
                                kamina.hp -= 15;
                                return cowrdStringGenerator();

                            } else {
                                return "You cannot go north any further.";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "south":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('s')) {
                                kamina.hp -= 15;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go south any further. you take a really strong hit from Nia in your back. and she is about to attack you again";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "west":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('w')) {
                                kamina.hp -= 15;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go west any further. . you take a really strong hit from Nia in your back . and she is about to attack you again";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "east":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('e')) {
                                kamina.hp -= 15;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go east any further. you take a really strong hit from Nia in your legs.. and she is about to attack you again";
                            }
                        } else {
                            triggerNext = true;
                            return "";
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

            case "hit":
                // AR: the following check is required.
                if (kamina.hp > 15) {
                    kamina.hp -= 15;
                    return fightStringGenerator();
                } else {
                    triggerNext = true;
                    return "";
                }
            // end of check
            // first check if gamer could use this action

            // end of hitting

            case "look":
                // AR: the following check is required
                if (words.length != 1) {
                    return unknowStringGenerator();
                }
                // end of check
                return "all you can see is the evil look on Nia face you must try to save yourself.. she is about to attack you again";
            //end of looking the object.

            case "go":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('n') && kamina.hp > 10) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();

                            } else {
                                kamina.hp -= 20;
                                return "You cannot go north any further.";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "south":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('s')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go south any further. you take a really strong hit from Nia in your back";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "west":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('w')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go west any further. . you take a really strong hit from Nia in your back";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "east":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('e')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go east any further. you take a really strong hit from Nia in your legs";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    default:
                        return unknowStringGenerator();
                }
            // end of going
            // AR: I have added the following cases

            case "put":
                return "you cann't do that in combar mode";
            // end of putting.

            case "get":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("up")) {
                    if (kamina.hp > 15) {
                        kamina.hp -= 15;
                        return "getting up";
                    } else {
                        triggerNext = false;
                    }
                } else if (words[1].equals("down")) {
                    return "getting down. you reduced the damage you are taking";
                } else {
                    return unknowStringGenerator();
                }
            // end of getting

            case "punch":
                if (kamina.hp > 30) {
                    kamina.hp -= 30;
                    return fightStringGenerator();
                } else {
                    triggerNext = true;
                    return "";
                }
            // end of punching

            case "kick":

                if (kamina.hp > 30) {
                    kamina.hp -= 30;
                    return fightStringGenerator();
                } else {
                    triggerNext = true;
                    return "";
                }
            // end of kicking

            case "slap":

                if (kamina.hp > 30) {
                    kamina.hp -= 30;
                    return "you tried to slap her but she slapped you instead.";
                } else {
                    triggerNext = true;
                    return "";
                }
            // end of slapping

            case "pull":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("hair")) {
                    if (kamina.hp > 30) {
                        kamina.hp -= 30;
                        return "you tried to pull her hair put she kicked your ass";
                    } else {
                        triggerNext = true;
                        return "";
                    }
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
                    if (kamina.hp > 30) {
                        kamina.hp -= 30;
                        return fightStringGenerator();
                    } else {
                        triggerNext = true;
                        return "";
                    }
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
                if (kamina.hp > 30) {
                    kamina.hp -= 30;
                    return "you are not fast enough to dodge her moves";
                } else {
                    triggerNext = true;
                    return "";
                }
            case "block":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    if (kamina.hp > 30) {
                        kamina.hp -= 30;
                        return "you are not strong enough to block";
                    } else {
                        triggerNext = true;
                        return "";
                    }
                }
            // end of blocking

            // AR: added the default case
            default:
                switch (words[0]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('n')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();

                            } else {
                                kamina.hp -= 20;
                                return "You cannot go north any further.. you take a really strong hit from Nia in your back";
                            }
                        } else {

                            triggerNext = true;
                            return "";
                        }
                    case "south":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('s')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go south any further. you take a really strong hit from Nia in your back";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "west":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('w')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go west any further. . you take a really strong hit from Nia in your back";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    case "east":
                        if (kamina.hp > 20) {
                            if (map.whileInGoTo('e')) {
                                kamina.hp -= 10;
                                return cowrdStringGenerator();
                            } else {
                                kamina.hp -= 20;
                                return "You cannot go east any further. you take a really strong hit from Nia in your legs";
                            }
                        } else {
                            triggerNext = true;
                            return "";
                        }
                    default:
                        return unknowStringGenerator();
                }
        }
    }

    public String start() {
        return "Nia said “you think you are the only one fast here”.  "
                + "I answered “what are you talking ab…?” what, where is she?"
                + " She disappeared. Before I recognize what just happened, "
                + "I heard a voice whispering in my ear “here!” I didn’t have "
                + "the chance to react, I felt something like a stone hitting"
                + " my back .this hit sent me flying for like 5 meters in the "
                + "air. I fall down on the ground.";
    }

    public String end() {
        return "She said “I thought you are a special, but you are not and now "
                + "that you know that much, I have to kill you. Bye Kamina”. I "
                + "am going to die. All I wanted to live a normal life. I look"
                + "ed at her I found in her hand a dagger. She really is goin"
                + "g to kill me. Is this that the end? tek………tek……tek.…tek"
                + "…tek..tek.tek. that’s how my heart responded. That’s the fi"
                + "rst time I am happy to hear this lovely beats going faster. "
                + "I can feel my body again. She moved really fast towards me "
                + "to finish her prey. But this time I can see her moves clearl"
                + "y I held her hand before she hit with her dagger. I have to"
                + " survive that’s the only idea I thought about in that time. I"
                + " loved the look of surprise on her face then I started moving"
                + " really fast around her. Faster faster, faster. The ground we"
                + "re shacking around me, Wow I am enjoying that. I shocked whe"
                + "n I saw a smile on her face and she said” well you are somet"
                + "hing”. Something? She think she can win this. The best way "
                + "to defend my self is by offense. So I ran out of her sight a"
                + "nd started moving really fast to attack her from where she can’t see. "
                + "Moved really close. I was about to hit her. She dodged my h"
                + "it and was about to stab me with the dagger. I think that’s the end. ";
    }

    public Integer vertex() {
        return map.getCurrentPosition() + 1;
    }

    public String mission() {
        return "Defend yourself";
    }

    public boolean missionComplete() {
        return missionCompleteSignal;
    }

    public String mapPath() {
        return "/Maps/NiaFightPlace/NiaFightPlace_" + vertex().toString()
                + ".png";
    }
}
