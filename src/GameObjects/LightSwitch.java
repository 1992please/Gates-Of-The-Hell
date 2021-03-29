/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author joker
 */
public class LightSwitch extends GameObject {

    private boolean on;

    public LightSwitch() {
        on = false;
    }

    public LightSwitch(boolean isOn) {
        on = isOn;
    }

    public void turnOn() {
        on = true;
    }

    public void turnOff() {
        on = false;
    }

    public boolean isON() {
        return on;
    }

    /**
     *
     * @return string holding the answer paper code.
     */
    @Override
    public String getCodeString() {
        return "22";
    }

    @Override
    public int getCode() {
        return 22;
    }

    @Override
    public String toString() {
        return "Light Switch";
    }
}
