import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Galaxy extends JPanel implements Runnable {
    Planet[] planet;
    int canvasWidth, canvasHeight;
    int planetSize;

    @Override
    public void run() {
        while (true) {
            repaint();
            collisionObserver();

            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Planet p : planet) {
            p.draw(g);
        }
    }

    protected void collisionObserver() {
        for (int i = 0; i < planet.length; i++) {
            Planet self = planet[i];

            if (self.isCrashed)
                continue;
            
            for (int k = 0; k < planet.length; k++) {
                if (i == k)
                    continue;

                Planet target = planet[k];

                if (target.isCollision(self.x, self.y)) {
                    target.crash();
                    break;
                }
            }
        }
    }

    public Galaxy(int canvasWidth, int canvasHeight, int planetCount, int planetSize) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.planetSize = planetSize;

        // Initialize Planet Array
        planet = new Planet[planetCount];

        // Create Planet Object
        for (int i = 0; i < planet.length; i++) {
            planet[i] = new Planet(canvasWidth, canvasHeight, planetSize);
            planet[i].start();
        }

        setLayout(null);
        setBackground(Color.BLACK);
    }
}
