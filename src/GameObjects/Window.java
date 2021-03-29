/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Window extends GameObject {

    private boolean isOpen;

    public Window() {
        isOpen = false;
    }

    public Window(boolean isOpen) {
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
        return "21";
    }

    @Override
    public int getCode() {
        return 21;
    }

    @Override
    public String toString() {
        return "Window";
    }
}
