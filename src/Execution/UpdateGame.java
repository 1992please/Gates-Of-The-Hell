/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Execution;

import Characters.Kamina;
import GameObjects.*;
import GraphWork.Map;

/**
 *
 * @author joker
 */
public class UpdateGame {
    
    private Map map;
    private Kamina kamina;
    public final static short DONE = 0;
    public final static short ALREADY_DONE = 1;
    public final static short NOT_DONE = 2;

    public UpdateGame(Kamina kamina, Map map) {
        this.kamina = kamina;
        this.map = map;
    }

    public short take(int objectCode) {
        // the water bottle is a special case since it has 2 codes
        if (objectCode == GameObject.FILLED_WATER_BOTTLE
                || objectCode == GameObject.EMPTY_WATER_BOTTLE) {
            if (kamina.hasObject(GameObject.EMPTY_WATER_BOTTLE)
                    || kamina.hasObject(GameObject.FILLED_WATER_BOTTLE)) {
                return ALREADY_DONE;
            }
        }
        if (kamina.hasObject(objectCode)) {
            return ALREADY_DONE;
        } else if (map.checkObject(objectCode)) {
            kamina.objectLists.add(map.getObject(objectCode));
            return DONE;
        }
        return NOT_DONE;
    }

    public short put(int objectCode) {
        if (kamina.hasObject(objectCode)) {
            map.addObject(kamina.removeObject(objectCode));
            return DONE;
        }
        return NOT_DONE;
    }

    public short use(int objectCode) {
        if (objectCode == GameObject.PERFUME) {
            if (kamina.hasObject(GameObject.PERFUME)) {
                kamina.putPerfume = true;
                return DONE;
            } else {
                return NOT_DONE;
            }
        } else if (objectCode == GameObject.BRUSH) {
            if (kamina.hasObject(GameObject.BRUSH)) {
                kamina.hearBrushed = true;
                return DONE;
            } else {
                return NOT_DONE;
            }
        }
        return NOT_DONE;
    }

    public short open(int objectCode) {
        if (objectCode == GameObject.DOOR) {
            Door door = (Door) map.getObject(20);
            if (door == null) {
                return NOT_DONE;
            } else if (door.isOpen()) {
                map.addObject(door);
                return ALREADY_DONE;
            }
            door.open();
            map.addObject(door);
            return DONE;
        } else if (objectCode == GameObject.WINDOW) {
            Window window = (Window) map.getObject(21);
            if (window == null) {
                return NOT_DONE;
            } else if (window.isOpen()) {
                map.addObject(window);
                return ALREADY_DONE;
            }
            window.open();
            map.addObject(window);
            return DONE;
        } else if (objectCode == GameObject.LAPTOP) {
            Laptop laptop = (Laptop) kamina.getObject(GameObject.LAPTOP);
            if (laptop == null) {
                return NOT_DONE;
            } else if (laptop.isOpen()) {
                return ALREADY_DONE;
            }
            laptop.open();
            return DONE;
        } else if (objectCode == GameObject.EMPTY_WATER_BOTTLE
                || objectCode == GameObject.FILLED_WATER_BOTTLE) {
            WaterBottle bottle = (WaterBottle) kamina.getObject(objectCode);
            if (bottle == null) {
                return NOT_DONE;
            } else if (bottle.isOpen()) {
                return ALREADY_DONE;
            }
            bottle.open();
            return DONE;
        }
        return NOT_DONE;
    }

    public short close(int objectCode) {
        if (objectCode == GameObject.DOOR) {
            Door door = (Door) map.getObject(20);
            if (door == null) {
                return NOT_DONE;
            } else if (!door.isOpen()) {
                map.addObject(door);
                return ALREADY_DONE;
            }
            door.close();
            map.addObject(door);
            return DONE;
        } else if (objectCode == GameObject.WINDOW) {
            Window window = (Window) map.getObject(21);
            if (window == null) {
                return NOT_DONE;
            } else if (!window.isOpen()) {
                map.addObject(window);
                return ALREADY_DONE;
            }
            window.close();
            map.addObject(window);
            return DONE;
        } else if (objectCode == GameObject.LAPTOP) {
            Laptop laptop = (Laptop) kamina.getObject(GameObject.LAPTOP);
            if (laptop == null) {
                return NOT_DONE;
            } else if (!laptop.isOpen()) {
                return ALREADY_DONE;
            }
            laptop.close();
            return DONE;
        } else if (objectCode == GameObject.EMPTY_WATER_BOTTLE
                || objectCode == GameObject.FILLED_WATER_BOTTLE) {
            WaterBottle bottle = (WaterBottle) kamina.getObject(objectCode);
            if (bottle == null) {
                return NOT_DONE;
            } else if (!bottle.isOpen()) {
                return ALREADY_DONE;
            }
            bottle.close();
            return DONE;
        }
        return NOT_DONE;
    }

    public short check(String string) {
        if (string.equals("mail")) {
            if (kamina.hasObject(GameObject.LAPTOP)) {
                Laptop laptop = (Laptop) kamina.getObject(GameObject.LAPTOP);
                if (laptop.isOpen()) {
                    laptop.checkMail();
                    return DONE;
                }
            }
        } else if (string.equals("myself")) {
            if (kamina.hearBrushed && kamina.putPerfume) {
                return DONE;
            }
        }
        return NOT_DONE;
    }

    public short drink() {
        if (kamina.hasObject(GameObject.FILLED_WATER_BOTTLE)) {
            return DONE;
        }
        return NOT_DONE;
    }

    public short read (int objectCode) {
        if (kamina.hasObject(objectCode)) {
            if (objectCode == GameObject.NIA_PAPER 
                    || objectCode == GameObject.AKILI_PAPER) {
                return DONE;
            }
        }
        return NOT_DONE;
    }

    public short lightsON() {
        if (map.checkObject(GameObject.LIGHT_SWITCH)) {
            LightSwitch lightSwitch = (LightSwitch) map.getObject(GameObject.LIGHT_SWITCH);
            if (lightSwitch.isON()) {
                map.addObject(lightSwitch);
                return ALREADY_DONE;
            }
            lightSwitch.turnOn();
            map.addObject(lightSwitch);
            return DONE;
        }
        return NOT_DONE;
    }

    public short lightsOFF() {
        if (map.checkObject(GameObject.LIGHT_SWITCH)) {
            LightSwitch lightSwitch = (LightSwitch) map.getObject(GameObject.LIGHT_SWITCH);
            if (!lightSwitch.isON()) {
                map.addObject(lightSwitch);
                return ALREADY_DONE;
            }
            lightSwitch.turnOff();
            map.addObject(lightSwitch);
            return DONE;
        }
        return NOT_DONE;
    }
}
