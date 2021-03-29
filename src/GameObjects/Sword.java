/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Sword extends GameObject {

    /**
     *
     * @return String holding the sword code.
     */
    @Override
    public String getCodeString() {
        return "10";
    }

    @Override
    public int getCode() {
        return 10;
    }

    @Override
    public String toString() {
        return "Sword";
    }
}
