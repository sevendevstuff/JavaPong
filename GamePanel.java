import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    static final int WIDTH = 1000;
    static final int HEIGHT = (int)(WIDTH * (0.55555555));
    static final Dimension SCREEN_SIZ = new Dimension(WIDTH, HEIGHT);
    static final int BALL_DIAM = 20;
    static final int P_WIDTH = 25;
    static final int P_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel() {
        newPaddles();
        newBall();
        score = new Score(WIDTH, HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZ);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        // random = new Random();
        ball = new Ball((WIDTH / 2) - BALL_DIAM / 2, (HEIGHT / 2) - BALL_DIAM / 2, BALL_DIAM, BALL_DIAM);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (HEIGHT / 2) - (P_HEIGHT / 2), P_WIDTH, P_HEIGHT, 1);
        paddle2 = new Paddle(WIDTH - P_WIDTH, (HEIGHT / 2) - (P_HEIGHT / 2), P_WIDTH, P_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        if(paddle1.y <= 0) {
            paddle1.y = 0;
        }

        if(paddle1.y >= (HEIGHT - P_HEIGHT)) {
            paddle1.y = HEIGHT - P_HEIGHT;
        }

        if(paddle2.y <= 0) {
            paddle2.y = 0;
        }

        if(paddle2.y >= (HEIGHT - P_HEIGHT)) {
            paddle2.y = HEIGHT - P_HEIGHT;
        }

        if(ball.y <= 0) {
            ball.setYDirection(-ball.yVel);
        }

        if(ball.y >= HEIGHT - BALL_DIAM) {
            ball.setYDirection(-ball.yVel);
        }

        if(ball.intersects(paddle1)) {
            ball.xVel = Math.abs(ball.xVel);
            ball.xVel++;
            if(ball.yVel > 0) {
                ball.yVel++;
            }
            else {
                ball.yVel--;
            }
            ball.setXDirection(ball.xVel);
            ball.setYDirection(ball.yVel);
        }

        if(ball.intersects(paddle2)) {
            ball.xVel = Math.abs(ball.xVel);
            ball.xVel++;
            if(ball.yVel > 0) {
                ball.yVel++;
            }
            else {
                ball.yVel--;
            }
            ball.setXDirection(-ball.xVel);
            ball.setYDirection(ball.yVel);
        }

        if(ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
        }
        if(ball.x >= WIDTH - BALL_DIAM) {
            score.player1++;
            newPaddles();
            newBall();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 75.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(!false) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
