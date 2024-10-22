package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gameState.GameState;
import main.GamePanel;

public class MouseInput implements MouseListener, MouseMotionListener {
    GamePanel gP;

    public MouseInput(GamePanel gP) {
        this.gP = gP;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        // gP.setRectPos(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gP.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gP.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gP.getGame().getMenu().mouseClicked(e);
                break;
            case PLAYING:
                gP.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gP.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gP.getGame().getPlaying().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gP.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gP.getGame().getPlaying().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
