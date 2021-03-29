/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Characters;

import static Characters.Enemy.FIRE_CAST;
import static Characters.Enemy.HELL_STRIKE;
import static Characters.Enemy.RUBSHER;
import java.util.Random;

/**
 *
 * @author joker
 */
public class Guard {

    public static final short HELL_STRIKE = 0;
    public static final short RUBSHER = 1;
    public static final short FIRE_CAST = 2;
    private short hp;
    private Random randomGenerator;
    private int[] hitDictionary;

    public Guard() {
        hp = 200;
        randomGenerator = new Random();
        hitDictionary = new int[3];
        hitDictionary[HELL_STRIKE] = 15 * 2;
        hitDictionary[RUBSHER] = 5 * 2;
        hitDictionary[FIRE_CAST] = 30 * 2;
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
