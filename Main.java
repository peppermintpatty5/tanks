import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

/**
 * Main
 */
public class Main {

    /**
     * Global variable list of redTeam tanks;
     */
    public static final List<Tank> redTeam = new ArrayList<Tank>();

    /**
     * Global variable list of blueTeam tanks;
     */
    public static final List<Tank> blueTeam = new ArrayList<Tank>();

    /**
     * Global variable list of bullets fired
     */
    public static final List<Bullet> bullets = new ArrayList<Bullet>();
    
    public static int frames;
    
    public static double seconds;
    
    public static AtomicBoolean simulation = new AtomicBoolean(true);

    public static void main(String[] args) {
        Window window = new Window("Gladiator Tanks: Competing Genetic Algorithims", 1920, 1080);

        for (int i = 0; i < 20; i++) {
            redTeam.add(new Tank(Tank.Teams.RED));
            blueTeam.add(new Tank(Tank.Teams.BLUE));
        }
        Component component = new Component(redTeam, blueTeam, bullets, window.getWidth(), window.getHeight());
        window.addKeyListener(component);
        window.add(component);
        window.validate();
        window.setVisible(true);

        EventQueue.invokeLater(() -> {
            new Timer(1000 / 60, e -> {
            	if(simulation.get()) {
            		frames++;
                	seconds = frames / 60.0d;
            	}
                for (int i = 0; i < 3; i++)
                    step();
                window.repaint();
            }).start();
        });

    }

    /**
     * Remove bullets from the playing field when they interact with tank. Decrease the 
     * hit point from the tank as well and remove it when hit points are less than or equal
     * to 0.
     * Remove bullets from the playing field when they interact with tank. Decrease
     * the hit point from the tank as well and remove it when hi toints are less
     * than or equal to 0.
     */
    public static void step() {

        redTeam.forEach(Tank::update);
        blueTeam.forEach(Tank::update);
        bullets.forEach(Bullet::update);

        bullets.removeIf(b -> {
            for (var t : (b.tank.team == Tank.Teams.RED ? blueTeam : redTeam)) {
                if (Math.pow(b.x - t.x, 2) + Math.pow(b.y - t.y, 2) < Math.pow(45, 2)) {
                    t.health--;
                    b.tank.hits++;
                    return true;
                }
            }
            return b.x < 0 || b.y < 0 || b.x > 1920 || b.y > 1080;
        });

        redTeam.stream().filter(t -> t.health <= 0).forEach(t -> t.isEnabled = false);
        blueTeam.stream().filter(t -> t.health <= 0).forEach(t -> t.isEnabled = false);

        // tanks = gen.process(tanks);
    }

    @SuppressWarnings("unused")
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
