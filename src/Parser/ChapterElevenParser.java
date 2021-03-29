/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Characters.Boss;
import Characters.Kamina;
import Execution.UpdateGame;
import GameObjects.Armor;
import GameObjects.GameObject;
import GameObjects.Sword;
import GraphWork.Map;
import Helpers.DLinkedList;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author joker
 */
public class ChapterElevenParser { // final fight

    private UpdateGame ug;
    public boolean triggerNext;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;
    private boolean missionCompleteSignal;
    private Boss boss;
    private boolean gameOverSignal;
    private boolean warCry;
    private boolean moved;

    public ChapterElevenParser(Kamina kamina) { // final fight
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
            map = new Map("Maps/final_fight.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        kamina.hp = 500;
        kamina.objectLists.clear();
        kamina.objectLists.add(new Sword());
        kamina.objectLists.add(new Armor());
        gameOverSignal = false;
        warCry = false;
        missionCompleteSignal = false;
        boss = new Boss();
        moved = false;
    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }

    private String fightMethod(String method) {
        if (!warCry) {
            return "You have to start with a war cry";
        }
        String returnString = new String();
        returnString += "SHIT!!!! He's Hitting\n";
        returnString += "OH MY GOD!!!!!\n";
        boss.receiveHit(kamina.hit(method) * 3);
        returnString += "I have stabbed him with my sword.\n";
        if (boss.health() <= 0) {
            triggerNext = true;
            returnString += "YEAAAAAAH!!! HE'S DEAD HE'S DEAD."
                    + "I DID IT I DID IT .. ";
            return returnString;
        }
        returnString += "His HP: " + boss.health().toString();
        returnString += "\nI am also hit.";
        kamina.receiveHit(boss.hit());
        if (kamina.hp <= 0) {
            gameOverSignal = true;
            return "YOU LOSE!!";
        }
        return returnString;
    }

    public boolean gameOver() {
        return gameOverSignal;
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
                        case "sword":case"swords":
                            return fightMethod("sword");
                        default:
                            return unknowStringGenerator();

                    }
                    // update the ph of the enemy
                } else {
                    return unknowStringGenerator();
                }
            // end of hitting

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
                    case "sword":
                        state = ug.take(GameObject.SWORD);
                        if (state == UpdateGame.DONE) {
                            return "Sword taken";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "I do have a sword.";
                        }
                        return "I can see no swords here.";
                    case "armor":
                        state = ug.take(GameObject.ARMOR);
                        if (state == UpdateGame.DONE) {
                            return "Armor Taken.";
                        } else if (state == UpdateGame.ALREADY_DONE) {
                            return "I do have an armor.";
                        }
                        return "There are no armors around.";
                    default:
                        return unknowStringGenerator();
                }
            //end of taking the object.

            case "go":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (moved) {
                            gameOverSignal = true;
                            return "You turned your back to the boss and killed"
                                    + " you.";
                        }
                        if (map.whileInGoTo('n')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go north any further.";
                        }
                    case "south":
                        if (moved) {
                            gameOverSignal = true;
                            return "You turned your back to the boss and killed"
                                    + " you.";
                        }
                        if (map.whileInGoTo('s')) {
                            moved = true;
                            return map.getDescription();
                        } else {
                            return "You cannot go south any further.";
                        }
                    case "west":
                        if (moved) {
                            gameOverSignal = true;
                            return "You turned your back to the boss and killed"
                                    + " you.";
                        }
                        if (map.whileInGoTo('w')) {
                            return map.getDescription();
                        } else {
                            return "You cannot go west any further.";
                        }
                    case "east":
                        if (moved) {
                            gameOverSignal = true;
                            return "You turned your back to the boss and killed"
                                    + " you.";
                        }
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
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("sword")) {
                    state = ug.put(GameObject.SWORD);
                    if (state == UpdateGame.DONE) {
                        return "Sword Dropped. I admire your bravery!! You are"
                                + "going to kill your enemies with bare hands.";
                    } else if (state == UpdateGame.NOT_DONE) {
                        return "You don't have swords to drop!!";
                    }
                } else if (words[1].equals("armor")) {
                    state = ug.put(GameObject.ARMOR);
                    if (state == UpdateGame.DONE) {
                        return "Armor Dropped. I am impressed how you are going"
                                + "to defend yourself.";
                    } else if (state == UpdateGame.NOT_DONE) {
                        return "You don't have armors to drop!!";
                    }
                } else {
                    return unknowStringGenerator();
                }
            // end of putting.

            case "get":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("up")) {
                    kamina.state = Kamina.NORMAL;
                    return "getting up";
                } else if (words[1].equals("down")) {
                    kamina.state = Kamina.DEFENSIVE;
                    return "getting down";
                } else {
                    return unknowStringGenerator();
                }
            // end of getting

            case "speed":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("on")) {
                    kamina.speedOn = true;
                    return "speed mode activated.";
                } else if (words[1].equals("off")) {
                    kamina.speedOn = false;
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
                        return fightMethod("punch");
                    } else {
                        return unknowStringGenerator();
                    }
                }
                return fightMethod("punch");
            // end of punching

            case "kick":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    return fightMethod("kick");
                }
            // end of kicking

            case "dodge":
            case "block":
                if (words.length != 1) {
                    return unknowStringGenerator();
                } else {
                    kamina.state = Kamina.DEFENSIVE;
                    return "dodged.";
                }
            // end of blocking

            case "war":
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                if (words[1].equals("cry")) {
                    warCry = true;
                    return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH, I WANT TO DRINK SOME HELL BLOOD"
                            + "\n, 8 archers and 4 guards ran away";
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
    }

    public Integer vertex() {
        return map.getCurrentPosition();
    }

    public String start() {
        return "This hell people really hated Nia and her sister to do all of"
                + " that to just kill them. I saw like 20 long range archers "
                + "on a building preparing their bows to launch arrows on us "
                + "and 10 huge guards and their leader whose body is same as"
                + " 3 big guards and he is the castle owner. I kept looking at"
                + " that view. Until Bogzila shouted “Kamina!! War cry”. ";
    }

    public String end() {
        return "I stabbed him with my both swords 10 times. It was as fast as "
                + "1 stab with my normal speed mode. He fall on the ground with"
                + " really despair look on his face. I decided to calm my speed"
                + " so I don’t get myself killed. Right before I turn off the "
                + "speed mode I saw Nia on the ground. She got hit with an "
                + "arrow while I was fighting the castle owner. I saw one of "
                + "the guards getting his weapon ready to finish her. "
                + "“NOOOOOOOOOOOOOOOOO!!” .I screamed with a really high voice "
                + "even higher than the war cry. After that everything stopped. "
                + "Time had stopped. I ran towards the guard who was going to "
                + "kill her and I cut his head with my sword. And then I "
                + "started killing the rest of guards. I felt like there was "
                + "a hole in my chest from the pain. I have to pear with it. "
                + "I need to finish all of them. Nia is going to die. I started "
                + "running at the speed of sound to get to the place where "
                + "archers .killing the rest of them. After I finished the "
                + "last one my heart beats started getting slower and slower "
                + "and slower. It came back to its normal state but it didn’t "
                + "stop slowing down. My heart slows down more until I could "
                + "barely feel it. I tried saying “Speed On” but it was no use. "
                + "I fall down on the ground. Bogzila shouted at me “idiot, why "
                + "did you exhaust your heart?” I said with really quite voice. "
                + "I didn’t want to see the one who helped me when no one cared "
                + "dies. I saw Akili coming from away with Nia leaning on her. "
                + "Everything fainted for a second then I opened my eyes trying"
                + " to see anything. I saw Nia crying while she is shacking me "
                + "to wake me up.I didn’t hear what she was saying. And it "
                + "doesn’t matter anymore. I smiled to Bogzila and said “sorry "
                + "my friend I think I won’t be able to fulfill our contract” "
                + "and I heard the last and the most beautiful beat coming "
                + "from my heart.\n"
                + "\n"
                + "\n"
                + "The End…………………";
    }

    public String mission() {
        return "Finish the boss";
    }

    public boolean missionComplete() {
        return missionCompleteSignal;
    }

    public String mapPath() {
        return "/Maps/Final_fight/final fight_" + vertex().toString() + ".png";
    }
}
