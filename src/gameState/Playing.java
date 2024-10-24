package gameState;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static main.Game.TILE_IN_WIDTH;
import static main.Game.TILE_SIZE;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Entity.EnemyManager;

import Entity.Player;
import level.LevelManager;
import main.Game;
import object.ObjectManager;
// import ui.LevelCompletedOverlay;
import utils.LoadSave;
import static utils.Constant.Environment.*;
import java.util.ArrayList;
import java.util.Random;

public class Playing extends State implements StateMethod {

    // entities
    Player player;
    LevelManager levelManager;
    EnemyManager enemyManager;

    // level camera var
    int xLvlOffset;
    int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
    int rightBorder = (int) (0.7 * Game.GAME_WIDTH);
    int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    int maxTilesOffset = lvlTilesWide - TILE_IN_WIDTH;
    int maxLvlOffsetX = maxTilesOffset * TILE_SIZE;

    BufferedImage bgImg, bigCloud, smallCloud;
    int[] smallCloudPos;
    Random rnd = new Random();

    // objects
    ObjectManager objectManager;

    // state
    boolean isGameOver = false;
    boolean isWin = false;
    boolean playerDying = false;
    // LevelCompletedOverlay levelCompletedOverlay;
    Lost lost;
    Win win;

    public Playing(Game game) {
        super(game);
        initClasses();
        bgImg = LoadSave.GetSpriteAtLast(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtLast(LoadSave.BIG_CLOUD);
        smallCloud = LoadSave.GetSpriteAtLast(LoadSave.SMALL_CLOUD);
        smallCloudPos = new int[8];
        for (int i = 0; i < smallCloudPos.length; i++) {
            smallCloudPos[i] = (int) (90 * SCALE) + rnd.nextInt((int) (100 * SCALE));
        }
    }

    void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);
        enemyManager = new EnemyManager(this, this.objectManager);
        player = new Player(200, 400,
                Game.TILE_SIZE * 1.2f, Game.TILE_SIZE * 1.2f,
                this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        lost = new Lost(this);
        win = new Win(this);
        // levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {
        if (!isGameOver) {
            levelManager.update();
            player.update();
            enemyManager.update(
                    levelManager.getCurrentLevel().getLevelData(),
                    player);

            objectManager.loadObjects(
                    levelManager.getCurrentLevel());
            objectManager.update(
                    levelManager.getCurrentLevel().getLevelData(),
                    player);
            checkCloseToBoder();
        }

        if (isWin) {
            win.update();
        } else if (isGameOver) {
            lost.update();
        }

    }

    public void resetAll() {
        isGameOver = false;
        isWin = false;
        player.resetAll();
        objectManager.resetAllObjects();
    }

    private void checkCloseToBoder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        xLvlOffset = Math.max(Math.min(xLvlOffset, maxLvlOffsetX), 0);
    }

    @Override
    public void draw(Graphics g) {
        if (!isGameOver) {
            g.drawImage(bgImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
            drawClouds(g);
            levelManager.draw(g, xLvlOffset);
            player.render(g, xLvlOffset);
            enemyManager.draw(g, xLvlOffset);
            objectManager.draw(g, xLvlOffset);
        }

        if (isWin) {
            win.draw(g);
        } else if (isGameOver) {
            lost.draw(g);
        }

    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, (i * BIG_CLOUD_WIDTH) - (int) (xLvlOffset * 0.3), (int) (215 * SCALE),
                    BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT,
                    null);
        }
        for (int i = 0; i < smallCloudPos.length; i++) {
            g.drawImage(
                    smallCloud, (SMALL_CLOUD_WIDTH * 4 * i) - (int) (xLvlOffset * 0.7),
                    smallCloudPos[i],
                    SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
    }

    public void checkHaystackTouched(Rectangle2D.Float hitbox) {
        if (player.getCollectedHaystack() > player.getMaxCollectedHaystack() - 1) {
            isWin = true;
        }
        objectManager.checkObjectTouched(hitbox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isGameOver) {
            lost.mousePressed(e);
        }

        if (isWin) {
            win.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isGameOver) {
            lost.mouseReleased(e);
        }

        if (isWin) {
            win.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isGameOver) {
            lost.mouseMoved(e);
        }

        if (isWin) {
            win.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                GameState.state = GameState.MENU;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
            default:
                break;
        }
    }

    public void windowFocusLost() {
        player.resetBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public void setPlayerDying(boolean b) {
        this.playerDying = b;
    }
}
