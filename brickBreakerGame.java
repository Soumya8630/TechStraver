import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class brickBreakerGame extends JFrame implements KeyListener {
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_DIAMETER = 20;
    private static final int BRICK_WIDTH = 80;
    private static final int BRICK_HEIGHT = 30;
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 10;
    private static final int FPS = 25;

    private boolean running = true;
    private boolean gameOver = false;
    private int paddleXPos = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;
    private int ballXPos = GAME_WIDTH / 2 - BALL_DIAMETER / 2;
    private int ballYPos = GAME_HEIGHT / 2 - BALL_DIAMETER / 2;
    private int ballXSpeed = 5;
    private int ballYSpeed = 5;
    private boolean[] bricks;
    private int score = 0;

    public brickBreakerGame() {
        setTitle("Brick Breaker");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addKeyListener(this);

        bricks = new boolean[NUM_ROWS * NUM_COLS];
        for (int i = 0; i < bricks.length; i++) {
            bricks[i] = true;
        }

        Timer timer = new Timer(1000 / FPS, e -> {
            if (!gameOver) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    private void update() {
        ballXPos += ballXSpeed;
        ballYPos += ballYSpeed;

        // Check collision with walls
        if (ballXPos <= 0 || ballXPos >= GAME_WIDTH - BALL_DIAMETER) {
            ballXSpeed *= -1;
        }
        if (ballYPos <= 0) {
            ballYSpeed *= -1;
        }

        // Check collision with paddle
        if (ballYPos + BALL_DIAMETER >= GAME_HEIGHT - PADDLE_HEIGHT && ballXPos + BALL_DIAMETER >= paddleXPos && ballXPos <= paddleXPos + PADDLE_WIDTH) {
            ballYSpeed *= -1;
        }

        // Check collision with bricks
        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i]) {
                int row = i / NUM_COLS;
                int col = i % NUM_COLS;
                int brickX = col * BRICK_WIDTH;
                int brickY = row * BRICK_HEIGHT;

                if (ballXPos + BALL_DIAMETER >= brickX && ballXPos <= brickX + BRICK_WIDTH &&
                        ballYPos + BALL_DIAMETER >= brickY && ballYPos <= brickY + BRICK_HEIGHT) {
                    bricks[i] = false;
                    ballYSpeed *= -1;
                    score++;
                    if (score == bricks.length) {
                        gameOver = true;
                    }
                    break;
                }
            }
        }

        // Check game over
        if (ballYPos >= GAME_HEIGHT) {
            gameOver = true;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Draw paddle
        g.setColor(Color.WHITE);
        g.fillRect(paddleXPos, GAME_HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.setColor(Color.BLUE);
        g.fillOval(ballXPos, ballYPos, BALL_DIAMETER, BALL_DIAMETER);

        // Draw bricks
        g.setColor(Color.GRAY);
        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i]) {
                int row = i / NUM_COLS;
                int col = i % NUM_COLS;
                int brickX = col * BRICK_WIDTH;
                int brickY = row * BRICK_HEIGHT;
                g.fillRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
            }
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);

        // Draw game over message
        if (gameOver) {
            g.drawString("Game Over!", GAME_WIDTH / 2 - 50, GAME_HEIGHT / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            paddleXPos -= 20;
            if (paddleXPos < 0) {
                paddleXPos = 0;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddleXPos += 20;
            if (paddleXPos > GAME_WIDTH - PADDLE_WIDTH) {
                paddleXPos = GAME_WIDTH - PADDLE_WIDTH;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            brickBreakerGame game = new brickBreakerGame();
            game.setVisible(true);
        });
    }
}
