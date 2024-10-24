package main;

import java.awt.Graphics;

import Entity.Player;
import gameState.GameState;
import gameState.Lost;
import gameState.Menu;
import gameState.Playing;
import level.LevelManager;

public class Game implements Runnable {
    GameWindow gW;
    GamePanel gP;
    Thread gameThread;
    final int FPS_SET = 120;
    final int UPS_SET = 200;
    Playing playing;
    Menu menu;
    Lost lost;

    public final static int TILE_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILE_IN_WIDTH = 26;
    public final static int TILE_IN_HEIGHT = 14;
    public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILE_IN_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILE_IN_HEIGHT;

    Player player;
    LevelManager levelManager;

    public Game() {
        initClasses();
        gP = new GamePanel(this);
        gW = new GameWindow(gP);
        gP.requestFocus();
        initGameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        // lost = new Lost(this);
    }

    void initGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            // case LOST:
            // lost.update();
            // break;
            case QUIT:
                System.out.println("out!!!");
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            // case LOST:
            //     lost.draw(g);
            default:
                break;
        }

    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frame = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currTime = System.nanoTime();

            deltaU += (currTime - previousTime) / timePerUpdate;
            deltaF += (currTime - previousTime) / timePerFrame;
            previousTime = currTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gP.repaint();

                deltaF--;
                frame++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frame + " | UPS: " + updates);
                frame = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (GameState.state == GameState.PLAYING)
            playing.getPlayer().resetBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}