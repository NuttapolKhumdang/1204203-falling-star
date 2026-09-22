import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main extends JFrame {
    int canvasWidth = 1800;
    int canvasHeight = 1000;
    int planetSize = 50;

    Main(int planetCount) {
        setSize(canvasWidth, canvasHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Galaxy galaxy = new Galaxy(canvasWidth, canvasHeight, planetCount, planetSize);
        add(galaxy);

        Thread thread = new Thread(galaxy);
        thread.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        // Asking for Number of Planet
        String maxPlanet = JOptionPane.showInputDialog(null, "Number Of Planet", "Falling Star",
                JOptionPane.QUESTION_MESSAGE);

        // Parsing number from Quesion Dialog or use 5 if fail to parse
        int planetCount = 5;
        try {
            planetCount = Integer.valueOf(maxPlanet);
        } catch (Exception e) {
        }

        // create Main object
        new Main(planetCount);
    }
}
