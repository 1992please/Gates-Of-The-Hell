/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Map extends GameObject {

    /**
     *
     * @return String holding the map code.
     */
    @Override
    public String getCodeString() {
        return "04";
    }

    @Override
    public int getCode() {
        return 4;
    }

    @Override
    public String toString() {
        return "map";
    }
}
