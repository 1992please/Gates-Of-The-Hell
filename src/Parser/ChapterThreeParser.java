/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Characters.Kamina;
import GraphWork.Map;
import Helpers.DLinkedList;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author joker
 */
public class ChapterThreeParser { // examination hall mission --> canceled

    public boolean triggerNext;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    Random randomGenerator;
    Map map;
    Kamina kamina;

    public ChapterThreeParser(Kamina kamina) {
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
            map = new Map("Maps/ExaminationHall.txt", 0);
        } catch (IOException ex) {
            return;
        }
        this.kamina = kamina;
    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }

    private String shapeStringGenerator() {
        return shapeString.getItemAt(randomGenerator.nextInt(shapeString.size())).toString();
    }

    public String start() {
        return " I think I recognized the situation. I think I am dreaming. "
                + "I have to use it even if I am dreaming.So I moved behind my "
                + "friend and looked at his answer paper and "
                + "memorized the answer then I moved to some students who had "
                + "good grade last year. Then I came back to my paper and "
                + "finished the exam. When I reached the last question my "
                + "heart beats were going slower again. I can hear them clearly"
                + " now. I looked at the clock, only two minutes past. "
                + "I finished the exam I had a lot of feelings going in my mind."
                + " What the hell just happened?";
    }

    public String end() {
        return "I felt like someone is looking at me, someone is staring at "
                + "me. I looked behind me. I saw a girl smiling, looking at "
                + "me she was so away but I can see her face clearly. The look"
                + " on her face like she is saying “I know what you just did"
                + " there”.";
    }

    public String Mission() {
        return "";
    }
}
