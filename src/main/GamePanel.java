package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import input.KeyboardInput;
import input.MouseInput;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;;

public class GamePanel extends JPanel {
    MouseInput mouseInput;
    Game game;

    public GamePanel(Game game) {
        mouseInput = new MouseInput(this);

        addKeyListener(new KeyboardInput(this));
        setPanelSize();
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        this.game = game;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size: " + GAME_WIDTH + " " + GAME_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
