package ui;

import gameState.GameState;
import utils.LoadSave;
import static utils.Constant.UI.Buttons.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class MenuButton {
    int xPos, yPos, rowIndex, index;
    int xOffsetCenter = B_WIDTH / 2;
    GameState state;
    BufferedImage[] imgs;
    boolean mouseOver, mousePressed;
    Rectangle bounds;

    public MenuButton(
            int xPos, int yPos,
            int rowIndex,
            GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(
                xPos - xOffsetCenter, yPos,
                B_WIDTH, B_HEIGHT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtLast(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(
                    i * B_WIDTH_DEFAULT,
                    rowIndex * B_HEIGHT_DEFAULT,
                    B_WIDTH_DEFAULT,
                    B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(
                imgs[index],
                xPos - xOffsetCenter, yPos,
                B_WIDTH, B_HEIGHT,
                null);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
