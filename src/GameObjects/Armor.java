/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Armor extends GameObject {

    /**
     *
     * @return string holding the Armor code.
     */
    @Override
    public String getCodeString() {
        return "09";
    }

    @Override
    public int getCode() {
        return 9;
    }

    @Override
    public String toString() {
        return "Armor";
    }
}
