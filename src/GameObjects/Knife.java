/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Knife extends GameObject {

    /**
     *
     * @return string holding the knife code.
     */
    @Override
    public String getCodeString() {
        return "16";
    }

    @Override
    public int getCode() {
        return 16;
    }

    @Override
    public String toString() {
        return "Knife";
    }
}
