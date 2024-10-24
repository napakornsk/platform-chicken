package object;

import static main.Game.SCALE;

public class Haystack extends GameObject {
    float hoverOffset;
    int maxHoverOffset, hoverDir = 1;

    public Haystack(int x, int y, int objType) {
        super(x, y, objType);
        isAnimated = true;
        initHitbox(26, 25);
        xDrawOffset = (int) (2 * SCALE);
        yDrawOffset = (int) (2 * SCALE);
        isActive = true;
        hitbox.y += yDrawOffset + (int) (SCALE * 2);
        hitbox.y += xDrawOffset / 2;

        maxHoverOffset = (int) (3 * SCALE);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.05f * SCALE * hoverDir);
        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;

        hitbox.y = y + hoverOffset;
    }
}
