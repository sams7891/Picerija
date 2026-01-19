package technical;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {

    private Clip clip;

    public void loadFromClasspath(String path) throws Exception {
        if (clip != null && clip.isOpen()) {
            clip.close();
        }

        // Use getResourceAsStream
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Sound file not found: " + path);
        }

        AudioInputStream audioInput = AudioSystem.getAudioInputStream(is);
        clip = AudioSystem.getClip();
        clip.open(audioInput);
    }
    
    public void load(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInput);
    }


    public void play(Runnable onEnd) {
        if (clip == null) return;

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                clip.close(); 
                if (onEnd != null) {
                    onEnd.run();
                }
            }
        });

        clip.start();
    }


    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }


    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
