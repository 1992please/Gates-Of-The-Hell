/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphWork;

import GameObjects.GameObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import save.Data;

/**
 *
 * @author RedDagger
 */
public class Map {

    /**
     *
     */
    public String mapName;
    private int currentPosition;
    private Vertex[] vertexList;   // array of Rooms
    private char[][] adjMat;      // adjacency matrix
    private int nVerts;         // number of vertexs
    private BufferedReader file;   // read file

    /**
     *
     * @param filePath the path of the map file
     * @param currentP the current position for the character
     * @throws IOException don't know what is the hell is that
     */
    //nagy & maher was here
    public Map(String filePath, int currentP) throws IOException {
        currentPosition = currentP;
        file = new BufferedReader(new FileReader(filePath));
        mapName = file.readLine();
        String line = file.readLine();
        nVerts = Integer.parseInt(line);
        vertexList = new Vertex[nVerts];
        adjMat = new char[nVerts][nVerts];
        for (int i = 0; i < nVerts; i++) {
            String[] token = file.readLine().split("<|>|\\|");
            vertexList[i] = new Vertex(token[0], i, token[1]);
            for (int j = 2; j < token.length; j++) {
                vertexList[i].listOfObjects.add(Data.objectMapper(token[j]));
            }
        }
        for (int j = 0; j < nVerts; j++)// set adjacency
        {
            for (int k = 0; k < nVerts; k++) //    matrix to 0
            {
                adjMat[j][k] = 0;
            }
        }
        if ("start".equals(file.readLine())) {
            line = file.readLine();
            while (!"end".equals(line)) {
                String[] token = line.split("\t");
                adjMat[Integer.parseInt(token[0])][Integer.parseInt(token[1])] = token[2].charAt(0);
                line = file.readLine();
            }
        }
    }

    public boolean checkObject(int objectId) {
        for (GameObject go : vertexList[currentPosition].listOfObjects) {
            if (go.getCode() == objectId) {
                return true;
            }
        }
        return false;
    }

    public GameObject getObject(int objectId) {
        for (int i = 0; i < vertexList[currentPosition].listOfObjects.size(); i++) {
            if (vertexList[currentPosition].listOfObjects.get(i).getCode() == objectId) {
                return vertexList[currentPosition].listOfObjects.remove(i);
            }
        }
        return null;
    }

    public void addObject(GameObject o) {
        vertexList[currentPosition].listOfObjects.add(o);
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return vertexList[currentPosition].toString();
    }

    /**
     *
     * @param v the id of the vertex
     * @return the description of the vertex
     */
    public String getDescription(int v) {
        return vertexList[v].toString();
    }

    /**
     *
     * @param v the id of the vertex
     * @return the vertex
     */
    public Vertex getVertex(int v) {
        return vertexList[v];
    }

    /**
     *
     * @return the current position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     *
     * @param dir the direction to go
     * @return the value of the vertex in that direction if no vertex will
     * return true or to indicate if the go to has done successfully
     */
    public boolean whileInGoTo(char dir) {
        for (int i = 0; i < nVerts; i++) {
            if (adjMat[currentPosition][i] == dir) {
                currentPosition = i;
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param vert
     * @param dir
     * @return
     */
    public int whileInGoTo(int vert, char dir) {
        for (int i = 0; i < nVerts; i++) {
            if (adjMat[vert][i] == dir) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @return
     */
    public int getID() {
        return currentPosition;
    }
}
