/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author joker
 */
public class Perfume extends GameObject {

    @Override
    public String getCodeString() {
        return "03";
    }

    @Override
    public int getCode() {
        return 3;
    }

    @Override
    public String toString() {
        return "perfume";
    }
}
