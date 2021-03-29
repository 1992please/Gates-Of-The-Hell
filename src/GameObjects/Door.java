/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Door extends GameObject {

    private boolean isOpen;

    public Door() {
        isOpen = false;
    }

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
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
     * @return string holding the answer paper code.
     */
    @Override
    public String getCodeString() {
        return "20";
    }

    @Override
    public int getCode() {
        return 20;
    }

    @Override
    public String toString() {
        return "Door";
    }
}
