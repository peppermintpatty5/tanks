import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        window.setVisible(true);
        window.add(new Component(tanks));
    }
}
