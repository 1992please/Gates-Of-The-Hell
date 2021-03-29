/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author RedDagger
 */
public class AkiliPaper extends Paper {

    public AkiliPaper() {
        owner = "akili";
        msg = "I found a paper with a checklist \n"
                + " leather armor (heat isolating "
                + "type) \n ice bombs \n red smoke bombs"
                + " - thrown \n speed recovery drinks";
    }

    /**
     *
     * @return String holding the paper code.
     */
    @Override
    public String getCodeString() {
        return "24";
    }

    @Override
    public int getCode() {
        return 24;
    }

    @Override
    public String toString() {
        return "Akili Paper";
    }
}
