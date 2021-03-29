/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class Paper extends GameObject {

    public String owner;
    public String msg;

    public Paper() {
        owner = "";
        msg = "";
    }

    /**
     *
     * @return String holding the paper code.
     */
    @Override
    public String getCodeString() {
        return "08";
    }

    @Override
    public int getCode() {
        return 8;
    }

    @Override
    public String toString() {
        return "paper";
    }
}
