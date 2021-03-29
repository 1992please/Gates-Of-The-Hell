/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Execution;

import Characters.*;
import Parser.*;
import save.Data;

/**
 *
 * @author RedDagger
 */
public class Execute {

    public boolean gameWon;
    public char chapter;
    public Kamina kam;
    public Data player;
    public String currentMission;
    private ChapterOneParser c1p;
    private ChapterTwoParser c2p;
    private ChapterThreeParser c3p;
    private ChapterFourParser c4p;
    private ChapterFiveParser c5p;
    private ChapterSixParser c6p;
    private ChapterSevenParser c7p;
    private ChapterEightParser c8p;
    private ChapterNineParser c9p;
    private ChapterTenParser c10p;
    private ChapterElevenParser c11p;
    private PausablePlayer music1;
    private PausablePlayer music2;
    private PausablePlayer music5;
    private PausablePlayer music6;
    private PausablePlayer music8;
    private PausablePlayer music9;
    private PausablePlayer music9_2;
    private PausablePlayer music10;
    private PausablePlayer music11;
    private PausablePlayer music12;
    public boolean gameFinished;
    public boolean readEnd;
    private boolean justInit;

    public Execute(Data player, Kamina kam) {
        gameWon=false;
        this.kam = kam;
        readEnd = false;
        this.justInit = true;
        this.gameFinished = false;
        this.player = player;
        this.chapter = player.chapter;
        if (player.objectsList.length > 0) {
            kam.initiateObjectLists(player.objectsList);
        }
    }

    public String initiateChapter() {
        if (chapter == 'a') {
            justInit = false;
            music1 = new PausablePlayer("Music/1.mp3");
            music1.play();
            c1p = new ChapterOneParser(kam);
            this.currentMission = c1p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter One"
                    + "-------------------------------------------------------------\n"
                    + c1p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else if (chapter == 'b') {
            if (!justInit) {
                music1.stop();
            } else {
                justInit = false;
            }
            music2 = new PausablePlayer("Music/2.mp3");
            music2.play();
            c2p = new ChapterTwoParser(kam);
            this.currentMission = c2p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Two"
                    + "-------------------------------------------------------------\n"
                    + c2p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else if (chapter == 'e') {
            if (!justInit) {
                music2.stop();
            } else {
                justInit = false;
            }
            music5 = new PausablePlayer("Music/5.mp3");
            music5.play();
            c5p = new ChapterFiveParser(kam);
            this.currentMission = c5p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Five"
                    + "-------------------------------------------------------------\n"
                    + c5p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else if (chapter == 'f') {
            if (!justInit) {
                music5.stop();
            } else {
                justInit = false;
            }
            music6 = new PausablePlayer("Music/6.mp3");
            music6.play();
            c6p = new ChapterSixParser(kam);
            this.currentMission = c6p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Six"
                    + "-------------------------------------------------------------\n"
                    + c6p.start() + "\n**current mission is : " + currentMission + "**" + "\n"
                    + "you can use*punch**kick* or *hit* in compat mode\n" + engine("look");
        } else if (chapter == 'h') {
            if (!justInit) {
                music6.stop();
            } else {
                justInit = false;
            }
            music8 = new PausablePlayer("Music/8.mp3");
            music8.play();
            c8p = new ChapterEightParser(kam);
            this.currentMission = c8p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Eight"
                    + "-------------------------------------------------------------\n"
                    + c8p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else if (chapter == 'i') {
            if (!justInit) {
                music8.stop();
            } else {
                justInit = false;
            }
            music9 = new PausablePlayer("Music/9.mp3");
            music9.play();
            c9p = new ChapterNineParser(kam);
            this.currentMission = c9p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Nine"
                    + "-------------------------------------------------------------\n"
                    + c9p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else if (chapter == 'j') {
            if (!justInit) {
                music9_2.stop();
            } else {
                justInit = false;
            }
            music10 = new PausablePlayer("Music/10.mp3");
            music10.play();
            c10p = new ChapterTenParser(kam);
            this.currentMission = c10p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Ten"
                    + "-------------------------------------------------------------\n"
                    + c10p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else if (chapter == 'k') {
            if (!justInit) {
                music10.stop();
            } else {
                justInit = false;
            }
            music11 = new PausablePlayer("Music/11.mp3");
            music11.play();
            c11p = new ChapterElevenParser(kam);
            this.currentMission = c11p.mission();
            return "-------------------------------------------------------------"
                    + "Chapter Eleven"
                    + "-------------------------------------------------------------\n"
                    + c11p.start() + "\n**current mission is : " + currentMission + "**" + "\n" + engine("look");
        } else {
            return null;
        }
    }

    String endTheChapter() {
        readEnd = true;
        if (chapter == 'a') {
            this.chapter = 'b';
            player.saveNow(this.chapter, kam.score += 20, kam.getObjectCodes());
            return c1p.end() + "\n\n\n.\n.\n.";
        } else if (chapter == 'b') {
            c3p = new ChapterThreeParser(kam);
            c4p = new ChapterFourParser(kam);
            this.chapter = 'e';
            player.saveNow('e', kam.score += 20, kam.getObjectCodes());
            return c2p.end()
                    + "-------------------------------------------------------------"
                    + "Chapter Three"
                    + "-------------------------------------------------------------\n"
                    + c3p.start() + "\n" + c3p.end()
                    + "\n-------------------------------------------------------------"
                    + "Chapter Four"
                    + "-------------------------------------------------------------\n"
                    + c4p.start() + c4p.end() + "\n.\n.\n.";
        } else if (chapter == 'e') {
            this.chapter = 'f';
            player.saveNow(this.chapter, kam.score += 20, kam.getObjectCodes());
            return c5p.end() + "\n\n\n.\n.\n.";
        } else if (chapter == 'f') {
            c7p = new ChapterSevenParser(kam);
            this.chapter = 'h';
            player.saveNow(this.chapter, kam.score += 20, kam.getObjectCodes());
            return c6p.end() + "\n\n"
                    + "-------------------------------------------------------------"
                    + "Chapter Seven"
                    + "-------------------------------------------------------------\n"
                    + c7p.start() + c7p.end() + "\n\n\n.\n.\n.";
        } else if (chapter == 'h') {
            this.chapter = 'i';
            player.saveNow(this.chapter, kam.score += 20, kam.getObjectCodes());
            return c8p.end() + "\n\n\n.\n.\n.";
        } else if (chapter == 'i') {
            music9.stop();
            music9_2 =new PausablePlayer("Music/9_2.mp3");
            music9_2.play();
            this.chapter = 'j';
            player.saveNow(this.chapter, kam.score += 20, kam.getObjectCodes());
            return c9p.end() + "\n\n\n.\n.\n.";
        } else if (chapter == 'j') {
            this.chapter = 'k';
            player.saveNow(this.chapter, kam.score += 20, kam.getObjectCodes());
            return c10p.end() + "\n\n\n.\n.\n.";
        } else if (chapter == 'k') {
            music11.stop();
            music12 = new PausablePlayer("Music/12.mp3");
            music12.play();
            this.gameFinished = true;
            this.gameWon=true;
            return c11p.end();

        } else {
            return null;
        }
    }

    public String engine(String message) {
        String temp = new String("");

        if (chapter == 'a') {
            temp = c1p.parse(message);
            if (c1p.triggerNext) {
                return endTheChapter();
            } else {
                return temp;
            }
        } else if (chapter == 'b') {
            temp = c2p.parse(message);
            if (c2p.triggerNext) {
                return endTheChapter();
            } else {
                return temp;
            }
        } else if (chapter == 'e') {
            temp = c5p.parse(message);
            if (c5p.triggerNext) {
                return endTheChapter();
            } else {
                return temp;
            }
        } else if (chapter == 'f') {
            temp = c6p.parse(message);
            if (c6p.triggerNext) {
                return endTheChapter();
            } else {
                return temp;
            }
        } else if (chapter == 'h') {
            temp = c8p.parse(message);
            if (c8p.triggerNext) {
                return endTheChapter();
            } else {
                return temp;
            }
        } else if (chapter == 'i') {
            temp = c9p.parse(message);
            if (c9p.triggerNext) {
                return endTheChapter();
            } else if (c9p.gameOver()) {
                this.gameFinished = true;
                return temp;
            } else {
                return temp;
            }
        } else if (chapter == 'j') {
            temp = c10p.parse(message);
            if (c10p.triggerNext) {
                return endTheChapter();
            } else if (c10p.gameOver()) {
                this.gameFinished = true;
                return temp;
            } else {
                return temp;
            }
        } else if (chapter == 'k') {
            temp = c11p.parse(message);
            if (c11p.triggerNext) {
                return endTheChapter();
            } else if (c11p.gameOver()) {
                this.gameFinished = true;
                return temp;
            } else {
                return temp;
            }
        } else {
            return null;
        }
    }

    public String getPhotoPath() {
        if (this.gameWon) {
            return "/Maps/Good Bye.png";
        } else if (this.readEnd) {
            return "/Maps/readMode.png";
        } else if (chapter == 'a') {
            return c1p.mapPath();
        } else if (chapter == 'b') {
            return c2p.mapPath();
        } else if (chapter == 'e') {
            return c5p.mapPath();
        } else if (chapter == 'f') {
            return c6p.mapPath();
        } else if (chapter == 'g') {
            return c7p.mapPath();
        } else if (chapter == 'h') {
            return c8p.mapPath();
        } else if (chapter == 'i') {
            return c9p.mapPath();
        } else if (chapter == 'j') {
            return c10p.mapPath();
        } else if (chapter == 'k') {
            return c11p.mapPath();
        } else {
            return null;
        }
    }
}
