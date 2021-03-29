/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Characters.Enemy;
import Characters.Kamina;
import Execution.UpdateGame;
import GameObjects.Armor;
import GameObjects.GameObject;
import GameObjects.Sword;
import GraphWork.Map;
import Helpers.DLinkedList;
import java.io.IOException;
import java.util.Random;
import Characters.Guard;

/**
 *
 * @author joker
 */
public class ChapterTenParser { // hell castle

    private UpdateGame ug;
    public boolean triggerNext;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;
    private boolean missionCompleteSignal;
    private boolean gameOverSignal;
    private Guard guard;
    private boolean warCry;

    public ChapterTenParser(Kamina kamina) { // castle fight
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
            map = new Map("Maps/hell castle.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        kamina.hp = 200;
        kamina.objectLists.clear();
        kamina.objectLists.add(new Sword());
        kamina.objectLists.add(new Armor());
        missionCompleteSignal = false;
        guard = new Guard();
        gameOverSignal = false;
        warCry = false;
    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }

    public boolean gameOver() {
        return gameOverSignal;
    }

    private String fightMethod(String method) {
        if (!warCry) {
            return "You are not able to engage with the "
                    + "guard unless he hears your war cry.";
        }
        if (guard.health() < 30) {
            return "Thanks God!! Bogzilla helped out and "
                    + "killed that piece of shit.";
        }
        String returnString = new String();
        returnString = "I'm trying to hit him.\nHis HP: "
                + guard.health().toString() + "\n";
        guard.receiveHit(kamina.hit(method));
        returnString += "He's hit ... He's hit.. \n";
        if (guard.health() < 30) {
            returnString += "Thanks God!! Bogzilla helped out and "
                    + "killed that piece of shit.";
            triggerNext = true;
        } else {
            returnString += "His HP: " + guard.health().toString() + "\n";
            kamina.receiveHit(guard.hit());
            returnString += "I was hit too. Shit!!";
        }
        if (kamina.hp <= 0) {
            gameOverSignal = true;
            returnString += "\n\nGAME OVER\n\nYOU LOSE!!";
        }
        return returnString;
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
                        if (map.whileInGoTo('n')) {
                            if (map.getCurrentPosition() == 3) {
                                return map.getDescription() + "\n"
                                        + "The guards are infront of you. Start with"
                                        + "the war cry.";
                            }
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
                } else {
                    return fightMethod("punch");
                }
            // end of punching

            case "kick":
                if (words.length != 1) {
                    return fightMethod("kick");
                } else {
                    return "kicking your ass.";
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
                    return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHH!!!"
                            + "YOU GUARDS DON'T UNDERESTIMATE ME OR ELSE.";
                } else {
                    return unknowStringGenerator();
                }
            // end of war cry

            // AR: added the default case
            default:
                if (words[0].equals("north") && words.length == 1) {
                    if (map.whileInGoTo('n')) {
                        if (map.getCurrentPosition() == 3) {
                            return map.getDescription() + "\n"
                                    + "The guards are infront of you. Start with"
                                    + "the war cry.";
                        }
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

    public String mapPath() {
        return "/Maps/Hell_Castle/Hell_Castle_" + vertex().toString() + ".png";
    }

    public String start() {
        return "We travelled very long distance. I don’t know how much days "
                + "have passed since the time I entered the gates of hell. I "
                + "lost the sense of time. I became a really good friend with "
                + "Bogzila. He taught me a lot of skills to use my swords "
                + "although he was specialist in using daggers. He had the "
                + "ability to hide in the shadows. He told me that I can’t be "
                + "like him because it’s not suitable for my fighting style. "
                + "He taught me a move called “War cry” it draws the attention "
                + "of the enemies within 500 meter and make them more likely "
                + "to run away.  Finally we reached the hell castle. It was "
                + "something I have never seen before even in my dreams. It "
                + "was built on an island inside a lava lake on the top of "
                + "mountain. We found killed guards bodies in front of the "
                + "gates of the castle. I think Nia is doing a great job but "
                + "Bogzila didn’t feel easy like me. He said “these guards are "
                + "so weak; I think they know that Nia will come to save her "
                + "sister. We need to move faster” we entered the castle I saw "
                + "two guards inside they were really huge and wearing a really"
                + " heavy armors. Bogzila whispered “that’s the real guards; we"
                + " need to kill one of them and leave the other alive so we "
                + "can interrogate him. You go first and draw their attention "
                + "with your war cry and I will take one of them in one shot "
                + "and make sure they don’t sense me while hiding in the "
                + "shadows”";
    }

    public String end() {
        return "In an instant one of them falls to ground. I didn’t even see "
                + "what happened to him. I saw Bogzila coming out of the "
                + "shadows saying” Now!! Kamina!!!!!” I started swinging my"
                + " both swords with all the might I have towards the other "
                + "guard. He takes a lot of damage. I lost control of my power"
                + " I kept hitting him with my sword until Bogzila shouts “STOP"
                + "!!!!” .I stopped swinging my swords. I don’t know what happen"
                + "ed to me. Bogzila started interrogating the guard “a contrac"
                + "tor came here where is she?” .The guard was really in pain a"
                + "nd didn’t answer. Bogzila said “are you aware that I am Bogzi"
                + "la?” .The look on the guard face changed and he said “she i"
                + "s in the basement prison”. Bogzila with an evil look said "
                + "“u saved yourself and he hit the guard in his heart with his"
                + " dagger” .We went to the Basement. I found Nia with her sist"
                + "er in a prison. She said “Kamina!” .I said “sorry I couldn’t "
                + "resist to go after you, let’s get the hell out of here” .her "
                + "sister looked at me like she is inspecting me and said with "
                + "tricky face “So is he …. ”. ” Akili!! ” Nia shouted. I wonder"
                + "ed what Akili was going to say. But anyway we opened their c"
                + "ells and we found their weapons. And we left the castle. Whe"
                + "n we opened the castle doors to go out. All of us looked at "
                + "the view outside with dead faces except Bogzila who seemed r"
                + "eally happy, he said” we can’t leave without a party, can we?"
                + " ”. ";
    }

    public String mission() {
        return "Rescue Nia";
    }

    public boolean missionComplete() {
        return missionCompleteSignal;
    }
}
