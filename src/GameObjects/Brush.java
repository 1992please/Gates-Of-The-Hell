/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Brush extends GameObject {

    /**
     *
     * @return String holding the Brush code
     */
    @Override
    public int getCode() {
        return 1;
    }

    @Override
    public String getCodeString() {
        return "01";
    }

    @Override
    public String toString() {
        return "Brush";
    }
}
