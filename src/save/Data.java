/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package save;

import GameObjects.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author RedDagger
 */
public class Data {

    public String name;
    public int score;
    public char chapter;
    public GameObject[] objectsList;

    public Data(String name) {
        this.name = name;
    }

    public boolean checkExist() {
        if (new File("Saves\\" + name + ".dat").exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveNow(char chapter, int score, GameObject[] objectList) {
        this.chapter = chapter;
        this.objectsList = new GameObject[objectList.length];
        for (int i = 0; i < objectList.length; i++) {
            objectsList[i] = objectList[i];
        }
        File f = new File("Saves");
        if (!f.exists()) {
            if (!f.mkdir()) {
                return false;
            }
        }

        FileWriter fw;
        // trying to create/open a file.
        try {
            fw = new FileWriter("Saves/" + name + ".dat", false);
        } catch (IOException ex) {
            return false;
        }

        // trying to write to the file
        try {
            fw.write(chapter + "\n");
            fw.write(score + "\n");
            fw.write(objectsList.length + "\n");
            for (GameObject s : objectsList) {
                fw.write(s.getCodeString() + "\n");
            }
        } catch (IOException ex) {
            return false;
        }

        // closing the file
        try {
            fw.close();
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    public boolean Loadnow() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("Saves/" + name + ".dat"));
        } catch (FileNotFoundException ex) {
            return false;
        }


        try {
            // reading the file
            // the chapter is the first line
            chapter = br.readLine().charAt(0);
            //the score is second line
            score = Integer.parseInt(br.readLine());
            // the number of the objects
            objectsList = new GameObject[Integer.parseInt(br.readLine())];

            String line;
            for (int i = 0; i < objectsList.length; i++) {
                objectsList[i] = objectMapper(br.readLine());
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static GameObject objectMapper(String objectCode) {
        if (objectCode.equals("00")) {
            return new WaterBottle(false);
        } else if (objectCode.equals("01")) {
            return new Brush();
        } else if (objectCode.equals("02")) {
            return new Laptop();
        } else if (objectCode.equals("03")) {
            return new Perfume();
        } else if (objectCode.equals("04")) {
            return new Map();
        } else if (objectCode.equals("05")) {
            return new Mobile();
        } else if (objectCode.equals("06")) {
            return new Mobile();
        } else if (objectCode.equals("07")) {
            return new Bicycle();
        } else if (objectCode.equals("08")) {
            return new Paper();
        } else if (objectCode.equals("09")) {
            return new Armor();
        } else if (objectCode.equals("10")) {
            return new Sword();
        } else if (objectCode.equals("11")) {
            return new WaterBottle(true);
        } else if (objectCode.equals("12")) {
            return new ModelAnswerPaper();
        } else if (objectCode.equals("13")) {
            return new AnswerPaper();
        } else if (objectCode.equals("14")) {
            return new Bag();
        } else if (objectCode.equals("15")) {
            return new WoodPiece();
        } else if (objectCode.equals("16")) {
            return new Knife();
        } else if (objectCode.equals("17")) {
            return new Stone();
        } else if (objectCode.equals("18")) {
            return new Sand();
        } else if (objectCode.equals("19")) {
            return new Pants();
        } else if (objectCode.equals("20")) {
            return new Door();
        } else if (objectCode.equals("21")) {
            return new Window();
        } else if (objectCode.equals("22")) {
            return new LightSwitch(true);
        } else if (objectCode.equals("23")) {
            return new NiaPaper();
        } else if (objectCode.equals("24")) {
            return new AkiliPaper();
        } else {
            return null;
        }
    }
}
