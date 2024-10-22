package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameState.GameState;
import main.Game;
import main.GamePanel;
import static utils.Constant.Directions.*;
import static utils.Constant.PlayerConstants.ATTACK;

public class KeyboardInput implements KeyListener {
    GamePanel gP;

    public KeyboardInput(GamePanel gP) {
        this.gP = gP;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gP.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gP.getGame().getPlaying().keyPressed(e);

                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gP.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gP.getGame().getPlaying().keyReleased(e);

                break;
            default:
                break;
        }
    }

}
