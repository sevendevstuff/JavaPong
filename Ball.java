import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    Random random;
    int xVel;
    int yVel;
    int initSpeed = 4;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        int randomXDirection = 0;
        if(randomXDirection == 0) {
            randomXDirection--;
            setXDirection(randomXDirection * initSpeed);
        }
        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0) {
            randomYDirection--;
            setYDirection(randomYDirection * initSpeed);
        }
    }

    public void setXDirection(int randomXDirection) {
        xVel = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        yVel = randomYDirection;
    }

    public void move() {
        x += xVel;
        y += yVel;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, height, width);
    }
}
