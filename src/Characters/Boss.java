/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Characters;

import static Characters.Guard.FIRE_CAST;
import static Characters.Guard.HELL_STRIKE;
import static Characters.Guard.RUBSHER;
import java.util.Random;

/**
 *
 * @author joker
 */
public class Boss {

    public static final short HELL_STRIKE = 0;
    public static final short RUBSHER = 1;
    public static final short FIRE_CAST = 2;
    private short hp;
    private Random randomGenerator;
    private int[] hitDictionary;

    public Boss() {
        hp = 800;
        randomGenerator = new Random();
        hitDictionary = new int[3];
        hitDictionary[HELL_STRIKE] = 15 * 3;
        hitDictionary[RUBSHER] = 5 * 3;
        hitDictionary[FIRE_CAST] = 30 * 3;
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
