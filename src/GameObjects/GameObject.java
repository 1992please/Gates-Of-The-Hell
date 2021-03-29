/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker This class is kind of ABC (Abstract Base Class) for all of the
 * other objects in the game.
 */
public class GameObject {

    public final static short FILLED_WATER_BOTTLE = 0;
    public final static short BRUSH = 1;
    public final static short LAPTOP = 2;
    public final static short PERFUME = 3;
    public final static short MAP = 4;
    public final static short MOBILE = 5;
    public final static short PHONE = 6;
    public final static short BICYCLE = 7;
    public final static short PAPER = 8;
    public final static short ARMOR = 9;
    public final static short SWORD = 10;
    public final static short EMPTY_WATER_BOTTLE = 11;
    public final static short MODEL_ANSWER_PAPER = 12;
    public final static short ANSWER_PAPER = 13;
    public final static short BAG = 14;
    public final static short WOOD_PIECE = 15;
    public final static short KNIFE = 16;
    public final static short STONE = 17;
    public final static short SAND = 18;
    public final static short PANTS = 19;
    public final static short DOOR = 20;
    public final static short WINDOW = 21;
    public final static short LIGHT_SWITCH = 22;
    public final static short NIA_PAPER = 23;
    public final static short AKILI_PAPER = 24;

    /**
     *
     * @return Integer representing the object.
     */
    public int getCode() {
        return 0;
    }

    /**
     *
     * @return String representing the object.
     */
    public String getCodeString() {
        return "";
    }
}
