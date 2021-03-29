/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class WaterBottle extends GameObject {

    public boolean empty;
    private boolean isOpen;

    /**
     *
     * @param isEmpty boolean parameter to determine if the bottle of water is
     * empty or not.
     */
    public WaterBottle(boolean empty) {
        this.empty = empty;
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    /**
     *
     * @return String holding the bottle of water code.
     */
    @Override
    public String getCodeString() {
        return (empty) ? "00" : "11";
    }

    @Override
    public int getCode() {
        return (empty) ? 0 : 11;
    }

    @Override
    public String toString() {
        return (empty) ? "an empty water bottle" : "a full water bottle";
    }
}
