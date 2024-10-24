package gameState;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

public class Lost {
    Playing playing;
    MenuButton play, quit;
    BufferedImage bgImg2;
    // bgImg
    int menuX, menuY, menuWidth, menuHeight;

    public Lost(Playing playing) {
        this.playing = playing;
        loadButtons();
        // loadBg();
        bgImg2 = LoadSave.GetSpriteAtLast(LoadSave.LOST_BG);
    }

    private void loadButtons() {
        play = new MenuButton(
                GAME_WIDTH - (GAME_WIDTH / 6), (int) (150 * SCALE), 0, GameState.PLAYING);
        quit = new MenuButton(
                GAME_WIDTH - (GAME_WIDTH / 6), (int) (220 * SCALE), 2, GameState.QUIT);
    }

    public void update() {
        play.update();
        quit.update();
    }

    boolean isIn(MenuButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void draw(Graphics g) {
        g.drawImage(bgImg2, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        play.draw(g);
        quit.draw(g);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(play, e))
            play.setMousePressed(true);
        else if (isIn(quit, e))
            quit.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(play, e)) {
            if (play.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(GameState.PLAYING);
            }
            // play.setMousePressed(true);
        } else if (isIn(quit, e)) {
            if (quit.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(GameState.QUIT);

            }
        }

        play.resetBools();
        quit.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        quit.setMouseOver(false);

        if (isIn(play, e))
            play.setMouseOver(true);
        else if (isIn(quit, e))
            quit.setMouseOver(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameState.state = GameState.PLAYING;
    }

    public void keyReleased(KeyEvent e) {
    }

}
