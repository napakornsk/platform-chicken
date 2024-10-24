package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import utils.Constant.Projectile.*;

public class Projectile {
    Rectangle2D.Float hitbox;
    int dir;
    float speed = utils.Constant.Projectile.SPEED;
    int WIDTH = utils.Constant.Projectile.BULLET_WIDTH;
    int HEIGHT = utils.Constant.Projectile.BULLET_WIDTH;

    boolean isActive = true;

    public Projectile(int x, int y, int dir) {
        hitbox = new Rectangle2D.Float(x, y, WIDTH, HEIGHT);
        this.dir = dir;
    }

    public void updatePos() {
        hitbox.x += dir * speed;
    }

    public void drawHitbox(Graphics g, int xLvlOffset, Color color) {
        g.setColor(color);
        g.drawRect(
                (int) (hitbox.x - xLvlOffset), // Apply xLvlOffset to hitbox X
                (int) (hitbox.y),
                (int) hitbox.width,
                (int) hitbox.height);
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

}
