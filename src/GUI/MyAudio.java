/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;

/**
 *
 * @author RedDagger
 */
public class MyAudio extends PlaybackListener implements Runnable {

    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;

    public MyAudio(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
            String urlAsString =
                    "file:///"
                    + new java.io.File(".").getCanonicalPath()
                    + "/"
                    + this.filePath;

            this.player = new AdvancedPlayer(
                    new java.net.URL(urlAsString).openStream(),
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        } catch (Exception ex) {
            return;
        }
    }

    public void stop() throws JavaLayerException {
        player.stop();
    }
    // PlaybackListener members

    @Override
    public void playbackStarted(PlaybackEvent playbackEvent) {
        System.out.println("playbackStarted()");
    }

    @Override
    public void playbackFinished(PlaybackEvent playbackEvent) {
        System.out.println("playbackEnded()");
    }

    // Runnable members
    @Override
    public void run() {
        try {
            this.player.play();
        } catch (javazoom.jl.decoder.JavaLayerException ex) {
            return;
        }

    }
}
