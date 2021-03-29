/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class WoodPiece extends GameObject {

    /**
     *
     * @return string holding the piece of wood code.
     */
    @Override
    public String getCodeString() {
        return "15";
    }

    @Override
    public int getCode() {
        return 15;
    }

    @Override
    public String toString() {
        return "wood piece";
    }
}
