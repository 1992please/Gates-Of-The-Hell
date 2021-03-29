/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Bicycle extends GameObject {

    /**
     *
     * @return String holding the Bicycle code.
     */
    @Override
    public String getCodeString() {
        return "07";
    }

    @Override
    public int getCode() {
        return 7;
    }

    @Override
    public String toString() {
        return "bicycle";
    }
}
