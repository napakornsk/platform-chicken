package object;

import gameState.Playing;

import java.awt.Color;
import java.awt.Graphics;
import utils.LoadSave;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import static utils.Constant.ObjectConstants.*;

public class ObjectManager {
    Playing playing;
    BufferedImage[] haystackImg;
    ArrayList<Haystack> haystacks;
    public BufferedImage haystackSprite = null;
    
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        haystacks = new ArrayList<>();
        haystacks.add(new Haystack(400, 200, HAYSTACK));
        haystacks.add(new Haystack(500, 200, HAYSTACK));
    }
    private void loadImgs() {
        haystackSprite = LoadSave.GetSpriteAtLast(LoadSave.HAYSTACK);
        // g.drawImage(haystackSprite, 100, 100, null);
        haystackImg = new BufferedImage[8];
        for (int i = 0; i < haystackImg.length; i++) {
            haystackImg[i] = haystackSprite.getSubimage(i * HAYSTACK_WIDTH_DEFAULT, 0, HAYSTACK_WIDTH_DEFAULT, 32);
        }

    }

    public void update() {
        for (Haystack h : haystacks) {
            if (h.isActive) {
                h.update();
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawHaystacks(g, xLvlOffset);
    }
    private void drawHaystacks(Graphics g, int xLvlOffset) {
        for (Haystack h : haystacks) {
            // System.out.println("h.isActive: " + h.isActive);
            if (h.isActive) {
                if (h.getObjType() == HAYSTACK) {
                    g.drawImage(haystackImg[h.getAnimIndex()],
                    (int)(h.getHitbox().x - h.getxDrawOffset() - xLvlOffset),
                    (int)(h.getHitbox().y - h.getxDrawOffset()),
                    HAYSTACK_WIDTH,
                    HAYSTACK_HEIGHT,
                    null);
                }
            }
            h.drawHitbox(g, xLvlOffset, Color.PINK);
        }
    }

    public BufferedImage getHaystackSprite() {
        return haystackSprite;
    }
}
