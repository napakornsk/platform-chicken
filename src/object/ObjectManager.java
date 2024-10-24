package object;

import gameState.Playing;
import level.Level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import utils.LoadSave;
import utils.Constant.ObjectConstants.*;

import java.util.ArrayList;
import java.util.Iterator;

import Entity.Enemy;
import Entity.Player;

import java.awt.image.BufferedImage;

import static utils.Constant.Directions.ENEMY_DIR_LEFT;
import static utils.Constant.ObjectConstants.*;
import static utils.Constant.Projectile.*;
import static utils.Helper.IsProjectileHittingLevel;;

public class ObjectManager {
    Playing playing;
    BufferedImage[] haystackImg;
    BufferedImage bulletImg;
    ArrayList<Haystack> haystacks;
    ArrayList<Projectile> projectiles;

    public BufferedImage haystackSprite = null;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        haystacks = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    public void checkObjectTouched(
            Rectangle2D.Float hitbox) {
        for (Haystack h : haystacks) {
            if (h.isActive()) {
                if (hitbox.intersects(h.getHitbox())) {
                    h.setActive(false);
                    afffectPlayer(h);
                }
            }
        }
    }

    public void afffectPlayer(Haystack h) {
        if (h.getObjType() == HAYSTACK)
            playing.getPlayer().collectHaystack(
                    HAYSTACK_COLLECT_AMOUNT);
    }

    public void loadObjects(Level lvl) {
        haystacks = lvl.getHaystacks();

    }

    private void loadImgs() {
        haystackSprite = LoadSave.GetSpriteAtLast(LoadSave.HAYSTACK);
        // g.drawImage(haystackSprite, 100, 100, null);
        haystackImg = new BufferedImage[8];
        for (int i = 0; i < haystackImg.length; i++) {
            haystackImg[i] = haystackSprite.getSubimage(i * HAYSTACK_WIDTH_DEFAULT, 0, HAYSTACK_WIDTH_DEFAULT, 32);
        }

        bulletImg = LoadSave.GetSpriteAtLast(LoadSave.BULLET);

    }

    public void update(int[][] lvlData, Player player) {
        for (Haystack h : haystacks) {
            if (h.isActive) {
                h.update();
            }
        }
        updateProjectiles(lvlData, player);
    }

    public void shootBullet(Enemy enemy) {
        int dir = 1;
        if (enemy.getWalkDir() == ENEMY_DIR_LEFT)
            dir = -1;

        projectiles.add(
                new Projectile((int) enemy.getHitbox().x,
                        (int) enemy.getHitbox().y, dir));

        System.out.println("projectile size: " + projectiles.size());

    }

    void updateProjectiles(int[][] lvlData, Player player) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();

            if (p.isActive()) {
                p.updatePos();

                if (p.getHitbox().intersects(player.getHitbox())) {
                    player.hurt();
                    p.setActive(false);
                } else if (IsProjectileHittingLevel(p, lvlData)) {
                    p.setActive(false);
                    iterator.remove(); // Safely remove the projectile from the list
                }
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawHaystacks(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        synchronized (projectiles) {
            Iterator<Projectile> iterator = projectiles.iterator();
            while (iterator.hasNext()) {
                Projectile p = iterator.next();

                if (p.isActive()) {
                    g.drawImage(bulletImg,
                            (int) p.getHitbox().x - xLvlOffset, (int) p.getHitbox().y,
                            BULLET_WIDTH, BULLET_HEIGHT,
                            null);
                } else {
                    iterator.remove(); // Safely remove inactive projectiles
                }

                p.drawHitbox(g, xLvlOffset, Color.RED);
            }
        }
    }

    private void drawHaystacks(Graphics g, int xLvlOffset) {
        for (Haystack h : haystacks) {
            // System.out.println("h.isActive: " + h.isActive);
            if (h.isActive) {
                if (h.getObjType() == HAYSTACK) {
                    g.drawImage(haystackImg[h.getAnimIndex()],
                            (int) (h.getHitbox().x - h.getxDrawOffset() - xLvlOffset),
                            (int) (h.getHitbox().y - h.getxDrawOffset()),
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

    public void resetAllObjects() {
        for (Haystack h : haystacks) {
            h.reset();
        }
        projectiles.clear();
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
