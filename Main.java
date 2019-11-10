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

        List<Tank> redTeam = new ArrayList<Tank>();
        List<Tank> blueTeam = new ArrayList<Tank>();
        Window window = new Window("Gladiator Tanks: Competing Genetic Algorithims", 1920, 1080);

        for (int i = 0; i < 10; i++) {
            redTeam.add(new Tank(Tank.Teams.RED));
            blueTeam.add(new Tank(Tank.Teams.BLUE));
        }
        window.add(new Component(redTeam, blueTeam, window.getWidth(), window.getHeight()));
        window.validate();
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
