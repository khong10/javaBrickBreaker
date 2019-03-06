package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BrickBreaker extends JPanel{
    public int width, height;
    public int tickrate = 30;
    public Player player;
    private Ball ball;
    private int balls = 3;
    public boolean isRunning = false, isPaused = false;

    // Colors for the block by row
    private Color[] rowColors = new Color[]{Color.magenta, Color.green, Color.CYAN, Color.yellow, Color.pink, Color.white};
    public ArrayList<Block> blocks;  // To spawn the multiple blocks

    private Image backgroundTile;

    public long lastUpdate;

    private GameThread gameThread;

    public BrickBreaker(int width, int height){
        this.width = width;
        this.height = height;
        // Set background
        backgroundTile = new ImageIcon(getClass().getResource("/brickBreaker/Background.jpg")).getImage();

        reset();

        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) { // Control the bar
                player.position.x = e.getX()-getWidth()/2;
                repaint(); // To move the bar
            }
        });

        // space = start, esc = pause, X = quit
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isRunning) run();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) togglePause();
                if (e.getKeyCode() == KeyEvent.VK_Q) quit();
            }
        });
    }

    //Reset the game so it can be played
    public void reset() {
        player = new Player(this);
        ball = new Ball(this);
        balls = 3;
        createBlocks(6, 10);
    }

    public void run() {
        if (gameThread != null) if (gameThread.isAlive()) gameThread.interrupt();
        reset();
        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void togglePause() {
        isPaused = !isPaused;    //isPaused = !true
    }

    public void quit() {
        isRunning = false;
    }

    //Spawns the multiple blocks
    private void createBlocks(int rows, int columns) {
        blocks = new ArrayList<Block>();
        int gap = 10;
        float w = (((float)width-10)/columns)-10;
        float h = 30;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Block b = new Block();
                b.mainColor = rowColors[y%rowColors.length];
                b.position.x = (int) (x*(w+gap)+gap)-width/2;
                b.position.y = (int) (y*(h+gap)+gap)-height/2;
                b.height = (int)h;
                b.width = (int)w;
                blocks.add(b);
            }
        }
    }

    public void OnBallLost() {
        balls--;
        if (balls <= 0) OnGameOver(false);
        else ball.position = new Point(0, 0);
    }

    public void OnGameOver(boolean won) {
        quit();
    }
    //Called whenever the Game "ticks"/in every cycle of the game
    public void tick() {
        //deltatime for framerate independence
        double deltatime = (System.nanoTime()-lastUpdate)/1000000.0;//in milliseconds
        ball.tick(deltatime);
        if (blocks.isEmpty()) OnGameOver(true);
        repaint();
    }

    //render the game
    public void paint(Graphics g) {
        super.paint(g);
        g.translate((getWidth()-width)/2, (getHeight()-height)/2);

        // Background
        for (int x = 0; x < width; x+=backgroundTile.getWidth(null)) {
            for (int y = 0; y < height; y+=backgroundTile.getHeight(null)) {
                g.drawImage(backgroundTile, x, y, backgroundTile.getWidth(null), backgroundTile.getHeight(null), null);
            }
        }
        g.translate(width/2, height/2);

        player.render(g);
        ball.render(g);

        if (blocks != null) for (int i = 0; i < blocks.size(); i++) blocks.get(i).render(g);

        // Message
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String msg = "";
        if (!isRunning) msg = "Please press space to start)";
        else if (isPaused) msg = "Game Paused";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(msg, -fm.stringWidth(msg)/2, fm.getHeight()/2);
    }
}
