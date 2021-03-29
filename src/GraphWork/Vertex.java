/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphWork;

import GameObjects.GameObject;
import java.util.LinkedList;

/**
 *
 * @author RedDagger
 */
public class Vertex {

    /**
     * the number of each room as it is in the map
     */
    public int id;
    /**
     * example name of room
     */
    public String name;
    /**
     * check if i pass here or not
     */
    public boolean visited;
    /*
     * the description of the vertex;
     */
    public String description;
    public LinkedList<GameObject> listOfObjects;

    /**
     * basic information
     *
     * @param labl name of room
     * @param num number of room
     */
    public Vertex(String labl, int num, String describe) {
        name = labl;
        id = num;
        visited = false;
        description = describe;
        listOfObjects = new LinkedList();
    }

    @Override
    public String toString() {
        if (listOfObjects.size() > 0) {
            String temp = description + "\n list of items here are: \n";
            for (GameObject i : listOfObjects) {
                temp += "***" + i.toString() + "\n";
            }
            return temp;
        } else {
            return description;
        }
    }
}
