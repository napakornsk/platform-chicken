package object;

import java.awt.geom.Rectangle2D;

import main.Game;

import static utils.Constant.Entity.ANIM_SPEED;
import static utils.Constant.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;

public class GameObject {
    protected int x, y, objType;
    protected Rectangle2D.Float hitbox;
    protected boolean isAnimated;
    protected int animTick, animIndex;
   
    protected int xDrawOffset, yDrawOffset;
    protected boolean isActive;

   

    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    public void reset() {
        animIndex = 0;
        animTick = 0;
        isActive = true;
        // gonna be something here
        isAnimated = true;
    }

    protected void updateAnimationTick() {
        animTick++;
        if (animTick > ANIM_SPEED) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(objType)) {
                animIndex = 0;
            }
        }
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlOffset, Color color) {
        g.setColor(color);
        g.drawRect(
                (int) (hitbox.x - xLvlOffset), // Apply xLvlOffset to hitbox X
                (int) (hitbox.y),
                (int) hitbox.width,
                (int) hitbox.height);
    }

    
    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }
    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    public int getAnimIndex() {
        return animIndex;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
