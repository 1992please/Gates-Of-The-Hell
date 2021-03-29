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

/**
 *
 * @author joker
 */
public class ChapterNineParser {

    private UpdateGame ug;
    public boolean triggerNext;
    private DLinkedList unknownString;
    private Random randomGenerator;
    private Map map;
    private Kamina kamina;
    private boolean missionCompleteSignal;
    private Enemy firstOne;
    private Enemy secondOne;
    private boolean gameOverSignal;

    public ChapterNineParser(Kamina kamina) { // Hell
        triggerNext = false;
        unknownString = new DLinkedList();
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
        randomGenerator = new Random();
        try {
            map = new Map("Maps/Hell_map.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
        kamina.hp = 100;
        kamina.objectLists.clear();
        kamina.objectLists.add(new Sword());
        kamina.objectLists.add(new Armor());
        firstOne = new Enemy();
        secondOne = new Enemy();
        missionCompleteSignal = false;
        gameOverSignal = false;

    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }

    private String fightMethod(String kaminaHittingMethod) {
        Enemy currentOne;
        if (firstOne.health() > 0) {
            currentOne = firstOne;
        } else if (secondOne.health() >= 20) {
            currentOne = secondOne;
        } else {
            currentOne = null;
        }

        if (currentOne == null) {
            return "There are no more enemies. The third one was killed by"
                    + " bogzilla.\nHint: Nia is at the end of this map.";
        }
        String returnString = new String();
        returnString += "Trying to hit the enemy.\n";
        returnString += "His HP: " + currentOne.health().toString();
        currentOne.receiveHit(kamina.hit(kaminaHittingMethod));
        returnString += ".......\n\n";
        if (currentOne.health() <= 0 && currentOne == firstOne) {
            returnString += "you cut his head. take care of the other one";
        } else if (currentOne.health() <= 20 && currentOne == secondOne) {
            returnString += "I moved towards the next one I stabbed him with my"
                    + " right sword in really fast scene. I couldn’t grab my"
                    + " right sword out of his body. I surprised when I saw"
                    + " the look on his face smiling while holding the sword"
                    + " inside his body. Was this his plan from the start to"
                    + " make me stab him but what will he gain, he is going to"
                    + " suicide just to slow me down. Oh I forgot about the"
                    + " third. He is behind me. I saw my life passing in front"
                    + " of me at that moment. I prepared myself to be shattered"
                    + " but nothing happened. Why he is so slow? I looked"
                    + " behind me to see what happened I found. A man with"
                    + " fancy gears stabbed the third man behind me. and said"
                    + " “see you pal the next time you get revived” I heard the"
                    + " man in front of me saying “damn you, Bogzila” I swung my"
                    + " left sword to cut his head before he thinks about"
                    + " another tricky move then I grabbed my right sword out"
                    + " of his body ”Speed Off”. I said “So……Bogzila, why did"
                    + " you help me?” he said “I just hated this guys although"
                    + " that won’t affect them much they will get revived soon"
                    + " but I like making them feel pain”. Oh god the look on"
                    + " his face probably the most evil look I ever saw. Then"
                    + " I prepared myself to leave quickly before he thinks "
                    + "about making me feel pain too. “Wait, are you Kamina?” "
                    + "he said. “How did you knew my name?” that’s the traditional"
                    + " response. He smiled and said “I heard about you from "
                    + "Nia the contractor” I get surprised “you know Nia?” He "
                    + "said “She told me about a human who she was training and"
                    + " he can control his heart beats to become really fast. I"
                    + " suspected that you are him when u said speed off” I had "
                    + "a lot of questions to ask him but the highest priority"
                    + " question right now is “do you know where is she right "
                    + "now?” he said “I am going to meet her right now”. I am a "
                    + "lucky guy today. I said “can you take me with you?” he "
                    + "answered “what I will have in return?” well I think I "
                    + "take back my words about being a lucky today. I said "
                    + "“what do you need in return? I don’t have but my gears "
                    + "right now and I can’t give you it and I think you already"
                    + " know”. He said “you will owe me a contract” I said “a "
                    + "contract?” He said “I suppose Nia didn’t tell you. A "
                    + "contractor is a human who make a contract with one of "
                    + "the hell clan to kill another one from the hell clan... "
                    + "Because if a human killed one of the hell clan they won’t"
                    + " come back and they are really skilled to be able to do"
                    + " so”. I think I have no choice but to accept this thing. "
                    + "“OK”I answered. He told me to come with him, I started "
                    + "following him to the north. I miss Nia I don’t know why but I miss her. ";
            currentOne.receiveHit(30);
        } else {
            returnString += "He's hit He's hit. His HP "
                    + "now: " + currentOne.health().toString();
            returnString += "\nyou hit him .. he hits you."
                + "\n....... OUCH!!!";
        }
        if (currentOne.health() > 0) {
            kamina.receiveHit(currentOne.hit());
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

            case "go":
                // AR: the following check is required
                if (words.length != 2) {
                    return unknowStringGenerator();
                }
                // end of check
                switch (words[1]) {
                    // you should first check the state of gamer allow him to use this action
                    case "north":
                        if (firstOne.health() > 0) {
                            return "You cannot pass until the you kill the first"
                                    + "enemy.";
                        }
                        if (secondOne.health() > 0
                                && map.getCurrentPosition() == 1) {
                            return "You cannot pass until the second one is "
                                    + "killed.";
                        }
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
                            triggerNext = (map.getCurrentPosition() == 9) ? true : false;
                            return map.getDescription();
                        } else {
                            return "You cannot go east any further.";
                        }
                    default:
                        return unknowStringGenerator();
                }
            // end of going
            // AR: I have added the following cases

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

            case "put":
            case "drop":
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

            // AR: added the default case
            default:
                if (words[0].equals("north") && words.length == 1) {
                    if (firstOne.health() > 0) {
                        return "You cannot pass until the you kill the first"
                                + "enemy.";
                    }
                    if (secondOne.health() > 0
                            && map.getCurrentPosition() == 1) {
                        return "You cannot pass until the second one is "
                                + "killed.";
                    }
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
                        triggerNext = (map.getCurrentPosition() == 9) ? true : false;
                        return map.getDescription() + "\nYou have successfully"
                                + " found Nia.";
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
        return "I can’t feel my legs anymore. It’s really cold here. Isn’t that"
                + " supposed to be the hell? I stunned in my place when I saw a"
                + " huge gate in front of me. Wow I can’t see its height. I feel"
                + " now my legs are getting shaken more because of that view. I"
                + " think that’s the point in my life that I will see secrets "
                + "that no human ever saw. Well I am not a normal human any "
                + "more I started walking towards the gate for 30 minutes. It "
                + "was so away the more I come close the more it become bigger "
                + "in my sight. Finally I reached it. I found a big statue in "
                + "front of the gate. I came close to that statue. It started "
                + "moving and I heard a voice coming from it “Welcome wonderer, "
                + "I am the guardian of the hell gates, what do you seek?” I "
                + "think I got used to this stuff. I answered “I want to pass "
                + "the gates of the hell”. It said “what is your cause?” .I "
                + "answered “I want to save a friend” the statue said “I don’t "
                + "sense any evil coming from your strong heart, I open the "
                + "gates now. MAY YOU FIND WHAT YOU SEEK”. The statue started "
                + "moving towards the gates and pushed it with his two hands. "
                + "Wow is he going to open this gates it looks higher than a "
                + "mountain. I thanked God that this statue is not my enemy. I "
                + "heard a great sound from the friction between the guardian "
                + "hands and the gates. Then I started hearing even greater "
                + "sound, I think the gates started moving. I started feeling "
                + "great heat coming from inside.\n"
                + "Walking in a place where not weakling can survive. I can’t "
                + "see the sky from the smoke in the air. I am walking beside "
                + "rivers of lava. The air here is so hot. I can feel it even "
                + "with my heat isolating armor. I walk for like 5 miles. I saw"
                + " someone coming from away. I think they are three. I thought"
                + " about hiding. But before I do they saw me and started "
                + "running in very high speed towards me. I unsheathed my two "
                + "swords.\n\nHint: It's recommended to use speed on mode.";
    }

    public String end() {
        return "Bogzila said “Niaaaa, I have a surprise for you” she looked in"
                + " shock. That’s the first time I see this angry look on Nia’s"
                + " face. I was about to say hi. She interrupted me saying "
                + "“Why are you here? And how did you get here?” I couldn’t "
                + "answer at all. I think I did something I shouldn’t do. She "
                + "asked another hard questions “is that my dad’s armor? And "
                + "those are my grandfather swords? Who gave you the "
                + "permission to take them” I think the only thing I could say "
                + "“I was worried about you Nia”. Ops I hope I didn’t say that. "
                + "She looked at me with wondering look “and why you are "
                + "worried about me? You accepted my training at the first "
                + "place so you can control your ability, that was the deal "
                + "and I finished your training so what do you need me for?”. "
                + "That was really harsh situation I said with really "
                + "embarrassed face “well because I….I………..I……..Lo”. "
                + "Before I finish the trash I am saying .she moved towards "
                + "me and put her hands on my mouth to stop what I am going "
                + "to say then she hugged me. I really didn’t get what is on "
                + "her mind at this moment. I felt her hand on the back of my "
                + "head then I felt more likely to sleep. I wake up I found "
                + "myself sleeping on the ground. I looked around I found "
                + "Bogzila sitting on rock looking at me “good morning, Lover "
                + "boy”. I said “where is Nia?” he answered “she is not here "
                + "anymore”. I said “I know I want to know where she had gone?” "
                + "he said “she had gone to the hell castle”. I said “why?” "
                + ".He answered “it’s a long story. I think after all you have "
                + "suffered to come here you have the right to hear it. Nia 5 "
                + "years ago was an active contractor she kill hell people for "
                + "their life experience which we call their essence. She was "
                + "really good at that but for some reason she retired from this"
                + " job. But her sister kept doing the job and didn’t retire Nia"
                + " tried to prevent her sister from doing that but it was no"
                + " hope to do so. The hell clan began to become angry they "
                + "forgot their problems and united and made a plan to capture"
                + " her. So one of them made a contract with her and when she "
                + "was going to kill her prey, they ambushed her, now Nia want "
                + "to help her little sister” I said “seems that she didn’t tell "
                + "me a lot. Bogzila I want you to show me the way to the hell "
                + "castle and help me and I will owe you two contracts, good deal ha?”";
    }

    public String mission() {
        return "Find Nia";
    }

    public boolean missionComplete() {
        return missionCompleteSignal;
    }

    public Integer vertex() {
        return map.getCurrentPosition();
    }

    public String mapPath() {
        return "/Maps/Hell/Hell_1_" + vertex().toString() + ".png";
    }

    public boolean gameOver() {
        return gameOverSignal;
    }
}