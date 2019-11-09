import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

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

        for (int i = 0; i < 10; i++)
            tanks.add(new Tank(Tank.Teams.BLUE));

        window.add(new Component(tanks));
        window.setVisible(true);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                window.repaint();
            }
        }).start();
    }

    private static synchronized void playSound(String url) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        new BufferedInputStream(Object.class.getResourceAsStream("assets/sound/" + url)));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
