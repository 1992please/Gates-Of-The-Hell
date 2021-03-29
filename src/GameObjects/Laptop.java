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
public class Laptop extends GameObject {

    /**
     *
     * @return String holding the Laptop code.
     *
     */
//    private String openLapNC;
//    private String openLapC;
//    private String take;
//    private String put;
//    boolean checked;
//
//    public Laptop() throws FileNotFoundException, IOException {
//        Update("ChapterOne");
//    }
//    
//    public void Update(String chapter) throws FileNotFoundException, IOException {
//        String path = "src\\GameChapters\\"+chapter + "\\Laptop.txt";
//        BufferedReader file;
//        file = new BufferedReader(new FileReader(path));
//        openLapNC = file.readLine();
//        openLapC = file.readLine();
//        take = file.readLine();
//        put = file.readLine();
//        checked = false;
//    }
//
//    public String take() {
//        return take;
//    }
//    public String put() {
//        return put;
//    }
//    
//    public String Open() {
//        if (checked) {
//            return openLapC;
//    
//        }
//        checked =true;
//        return openLapNC;
//    }
    private boolean isOpen;
    private boolean mailChecked;

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public boolean mailChecked() {
        return mailChecked;
    }

    public void checkMail() {
        mailChecked = true;
    }

    @Override
    public String getCodeString() {
        return "02";
    }

    @Override
    public int getCode() {
        return 2;
    }

    @Override
    public String toString() {
        return "Laptop";
    }
}
