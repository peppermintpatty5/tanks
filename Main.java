import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {

        List<Tank> tanks = new ArrayList<Tank>();
        Window window = new Window();
        Random rand = new Random();

        for (int i = 0; i < 10; i++)
            tanks.add(new Tank(rand.nextInt(100), rand.nextInt(100), 0));

        window.add(new Component(tanks));
        window.setVisible(true);
    }

    private static synchronized void playSound(String url) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                        Object.class.getResourceAsStream("/minesweeper/resources/sounds/" + url)));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
