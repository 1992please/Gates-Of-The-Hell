/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Stone extends GameObject {

    /**
     *
     * @return string holding the stone code.
     */
    @Override
    public String getCodeString() {
        return "17";
    }

    @Override
    public int getCode() {
        return 17;
    }

    @Override
    public String toString() {
        return "Stone";
    }
}
