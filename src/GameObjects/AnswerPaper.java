/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class AnswerPaper extends GameObject {

    /**
     *
     * @return string holding the answer paper code.
     */
    @Override
    public String getCodeString() {
        return "13";
    }

    @Override
    public int getCode() {
        return 13;
    }

    @Override
    public String toString() {
        return "answer paper";
    }
}
