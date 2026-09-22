import java.awt.*;
import java.io.File;
import java.util.Random;

public class Planet extends Thread {
    Random random = new Random();

    // Getting random planet image path
    String planetPath = System.getProperty("user.dir")
            + File.separator + "images"
            + File.separator + (random.nextInt(10) + 1) + ".png";

    // Getting bomb image path
    String bombPath = System.getProperty("user.dir")
            + File.separator + "images"
            + File.separator + "bomb.gif";

    // Getting images and set current image to planetImage
    Image planetImage = Toolkit.getDefaultToolkit().createImage(planetPath);
    Image bombImage = Toolkit.getDefaultToolkit().createImage(bombPath);
    Image currentImage = planetImage;

    int x, y; // position x, y of planet on canvas
    int vx, vy; // velocity x and y for direction of moving
    int canvasWidth, canvasHeight;
    int planetSize;

    int speed = 1;
    boolean isCrashed = false; // Using isCrashed to mark as crashed and must not collision to other
    boolean isDead = false; // Using isDead to stop the moving thread and all animation of the planet

    public Planet(int canvasWidth, int canvasHeight, int planetSize) {
        this.planetSize = planetSize;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        x = random.nextInt(canvasWidth - planetSize) + planetSize;
        y = random.nextInt(canvasHeight - planetSize) + planetSize;

        do {
            vx = random.nextInt(7) - 3;
            vy = random.nextInt(7) - 3;
        } while (vx == 0 && vy == 0); // od it until some velocity is not zero

        speed = random.nextInt(10) + 10;
    }

    public void draw(Graphics g) {
        if (!isCrashed || !isDead)
            g.drawImage(currentImage, x, y, planetSize, planetSize, null);
    }

    public void crash() {
        vx = 0;
        vy = 0;

        isCrashed = true;
        currentImage = bombImage;
    }

    protected void move() {
        x += vx;
        y += vy;
    }

    // Checking if given tx and ty are collision with the planet
    protected boolean isCollision(int tx, int ty) {
        if (isCrashed)
            return false;

        return Math.abs(x - tx) <= planetSize
                && Math.abs(y - ty) <= planetSize;
    }

    // Checking if planet moving to canvas border
    protected void collisionObserver() {
        if (x <= 0) {
            vx = 1;
            vy = random.nextInt(3) - 1;
            speed++;
        } else if ((x + planetSize) >= canvasWidth) {
            vx = -1;
            vy = random.nextInt(3) - 1;
            speed++;
        }

        if (y <= 0) {
            vx = random.nextInt(3) - 1;
            vy = 1;
            speed++;
        } else if ((y + planetSize) >= canvasHeight) {
            vx = random.nextInt(3) - 1;
            vy = -1;
            speed++;
        }
    }

    @Override
    public void run() {
        while (!isCrashed || !isDead) {
            move();
            collisionObserver();

            if (isCrashed) {
                try {
                    Thread.sleep(800);
                    isDead = true;
                } catch (Exception e) {
                }
            }

            try {
                Thread.sleep(100 / speed);
            } catch (Exception e) {
            }
        }
    }
}