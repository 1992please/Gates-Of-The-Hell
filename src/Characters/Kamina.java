/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Characters;

/**
 *
 * @author joker & red dagger
 */
import GameObjects.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author joker
 */
public class Kamina {

    public static final short NORMAL = 0;
    public static final short DEFENSIVE = 1;
    public boolean hearBrushed;
    public boolean putPerfume;
    public boolean wearPants;
    public LinkedList<GameObject> objectLists;
    public int hp;
    public int score;
    public short state;
    public boolean speedOn;

    public Kamina() {
        hearBrushed = false;
        wearPants = false;
        putPerfume = false;
        objectLists = new LinkedList();
        hp = 100;
        state = NORMAL;
        speedOn = false;
    }

    public void initiateObjectLists(GameObject[] objects) {
        objectLists.addAll(Arrays.asList(objects));
    }

    public int receiveHit(int value) {
        if (speedOn) {
            if (state == NORMAL) {
                hp -= value * 0.5;
            } else if (state == DEFENSIVE) {
                hp -= value * 0.5 * 0.5;
            }
        } else {
            if (state == NORMAL) {
                hp -= value;
            } else if (state == DEFENSIVE) {
                hp -= value * 0.5;
            }
        }
        return hp;
    }

    public Integer hit(String hit) {
        if (hit.equals("sword")) {
            if (speedOn) {
                return (state == DEFENSIVE) ? 10 : 20;
            }
            return (state == DEFENSIVE) ? 5 : 10;
        } else if (hit.equals("punch")) {
            if (speedOn) {
                return (state == DEFENSIVE) ? 5 : 10;
            }
            return (state == DEFENSIVE) ? 0 : 5;
        } else if (hit.equals("kick")) {
            if (speedOn) {
                return (state == DEFENSIVE) ? 10 : 15;
            }
            return (state == DEFENSIVE) ? 5 : 10;
        }
        return 0;
    }

    public boolean hasObject(int objectCode) {
        for (GameObject go : objectLists) {
            if (go.getCode() == objectCode) {
                return true;
            }
        }
        return false;
    }

    public GameObject removeObject(int objectCode) {
        for (int i = 0; i < objectLists.size(); i++) {
            if (objectLists.get(i).getCode() == objectCode) {
                return objectLists.remove(i);
            }
        }
        return null;
    }

    public GameObject[] getObjectCodes() {
        GameObject[] temp = new GameObject[objectLists.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = objectLists.get(i);
        }
        return temp;
    }

    public GameObject getObject(int objectCode) {
        for (int i = 0; i < objectLists.size(); i++) {
            if (objectLists.get(i).getCode() == objectCode) {
                return objectLists.get(i);
            }
        }
        return null;
    }
}
