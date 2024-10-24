package Entity;

import static main.Game.SCALE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
    protected float x, y;
    protected float width, height;
    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitbox(Graphics g, int xLvlOffset, Color color) {
        g.setColor(color);
        g.drawRect(
                (int) (hitbox.x - xLvlOffset), // Apply xLvlOffset to hitbox X
                (int) (hitbox.y),
                (int) hitbox.width,
                (int) hitbox.height);
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));

        // hitbox = new Rectangle2D.Float(x, y, (int) width * SCALE, (int) height *
        // SCALE);
    }

    protected void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }
}
