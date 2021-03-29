/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author RedDagger
 */
public class NiaPaper extends Paper {

    public NiaPaper() {
        owner = "nia";
        msg = "I know that my sister is up to "
                + "something but don’t know what it is. "
                + "I hope everything in her life is "
                + "going well. I can’t tell her that "
                + "she’s been acting weird lately. If "
                + "she has something to do with the "
                + "hell guys, I won’t permit that. I "
                + "will prevent her from making a "
                + "contract. I don’t want to lose her.";
    }
    
    /**
     *
     * @return String holding the paper code.
     */
    @Override
    public String getCodeString() {
        return "23";
    }
    
    @Override
    public int getCode() {
        return 23;
    }
    
    @Override
    public String toString() {
        return "Nia Paper";
    }
 
}
