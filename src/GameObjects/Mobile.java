/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Mobile extends GameObject {

    /**
     *
     * @return String holding the mobile code. Note that the mobile is the same
     * as phone in the object code list.
     */
    @Override
    public String getCodeString() {
        return "05";
    }

    @Override
    public int getCode() {
        return 5;
    }

    @Override
    public String toString() {
        return "mobile";
    }
}
