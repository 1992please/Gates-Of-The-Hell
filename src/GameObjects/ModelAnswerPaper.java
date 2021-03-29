/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class ModelAnswerPaper extends GameObject {

    /**
     *
     * @return string holding the Model Answer Paper code.
     */
    @Override
    public String getCodeString() {
        return "12";
    }

    @Override
    public int getCode() {
        return 12;
    }

    @Override
    public String toString() {
        return "model answer paper";
    }
}
