/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Characters;

import java.util.Random;

/**
 *
 * @author joker
 */
public class Enemy {

    public static final short HELL_STRIKE = 0;
    public static final short RUBSHER = 1;
    public static final short FIRE_CAST = 2;
    private short hp;
    private Random randomGenerator;
    private int[] hitDictionary;

    public Enemy() {
        hp = 100;
        randomGenerator = new Random();
        hitDictionary = new int[3];
        hitDictionary[HELL_STRIKE] = 15;
        hitDictionary[RUBSHER] = 5;
        hitDictionary[FIRE_CAST] = 30;
    }

    public Integer hit() {
        return hitDictionary[randomGenerator.nextInt(3)];
    }

    public Short health() {
        return hp;
    }

    public Short receiveHit(int value) {
        hp -= value;
        return hp;
    }
}
