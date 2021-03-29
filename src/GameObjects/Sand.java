/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Sand extends GameObject {

    /**
     *
     * @return string holding the sand code.
     */
    @Override
    public String getCodeString() {
        return "18";
    }

    @Override
    public int getCode() {
        return 18;
    }

    @Override
    public String toString() {
        return "sand";
    }
}
