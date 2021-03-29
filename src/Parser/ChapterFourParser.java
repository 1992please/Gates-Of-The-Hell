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
public class ChapterFourParser { // presentation hall map --> canceled

    public boolean triggerNext;
    private DLinkedList unknownString;
    private DLinkedList shapeString;
    Random randomGenerator;
    Map map;
    Kamina kamina;

    public ChapterFourParser(Kamina kamina) {
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

    private String shapeStringGenerator() {
        return shapeString.getItemAt(randomGenerator.nextInt(shapeString.size())).toString();
    }

    public String start() {
        return "A year has passed and I almost forgot about the last year’s "
                + "incident. I was afraid from what I will learn if I tried to"
                + " find out. So I had to give a presentation in my class about "
                + "some subject that I don’t even care about. But I have to make"
                + " it for grades.\nSitting and waiting for my turn. When I "
                + "think about myself standing in front of"
                + " this scary boring faces. My mind is convinced that I am not"
                + " scared but my heart don’t agree. At that moment I remembered "
                + "the last year’s event. Please, not again. My heart beats have"
                + " gone out of control it beating so fast. I don’t know what to"
                + " do. So I tried to stay calm. When I was about to get calm "
                + "the doctor said “Kamina you are next”, it was like an "
                + "explosion in my chest. I kept saying “Kamina you have to "
                + "stay calm, you can do it”. I stepped in front of all this "
                + "scary faces. I started talking. Hmm I thought that was easy ."
                + "it is not hard. I think I am doing well. But it was the "
                + "opposite. No one heard a word from what I am saying .I was "
                + "talking so fast that not a normal ear can hear. Some of my "
                + "colleagues were laughing and some of them were looking at me "
                + "with creepy faces. Oh I think that’s the end of my normal life. And "
                + "while imagining a lot of scenarios of what will happen .";
    }

    public String end() {
        return ".I found a girl standing and walking towards me and saying"
                + "” Sorry my cousin have some heart problems. I have to take "
                + "him to the clinic”. When I looked at her face, I think she is"
                + " the girl from the last year incident. I said while walking "
                + "beside her “I don’t know who you are, but thank you”. She smil"
                + "ed and said “you are welcome Kamina”, what? How could she reco"
                + "gnize what I am saying with my fast speaking? Who the hell is"
                + " she? A lot of questions that came to my mind. She cut my dee"
                + "p thinking by saying” don’t worry you will know everything soo"
                + "n” with her always smiley face. When we reached the main door"
                + " of the college .She continued “If you want to know more meet"
                + " me next Tuesday 8 o’clock at morning and it will be preferre"
                + "d that you don’t eat anything before you come, and by the way"
                + " my name is Nia, Nia Kristof. I got stuff to do so see you la"
                + "ter” I really didn’t care at this moment about her or what sec"
                + "rets are behind her, I cared the most about how to get back m"
                + "y normal life. I continued my normal life until the promised "
                + "day wondering about what will she do with me and what did she"
                + " meant by saying don’t eat before coming. ";
    }

    private String unknowStringGenerator() {
        return unknownString.getItemAt(randomGenerator.nextInt(unknownString.size())).toString();
    }
}
