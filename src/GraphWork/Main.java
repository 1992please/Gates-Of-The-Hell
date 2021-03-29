/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphWork;

import java.io.IOException;

/**
 *
 * @author RedDagger
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Map Kamina_room = new Map("src\\Maps\\kamina_room.txt", 1);
        System.out.println(Kamina_room.getDescription());
        Kamina_room.whileInGoTo('w');
        System.out.println(Kamina_room.getDescription());
        System.out.println(Kamina_room.whileInGoTo('n'));
    }
}
