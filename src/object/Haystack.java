package object;

import static main.Game.SCALE;
import java.awt.Color;
import java.awt.Graphics;
public class Haystack extends GameObject {

    public Haystack(int x, int y, int objType) {
        super(x, y, objType);
        isAnimated = true;
        initHitbox(26, 32);
        xDrawOffset = (int) (2 * SCALE);
        yDrawOffset = (int) (2 * SCALE);
        isActive = true;

    }
    public void update() {
        updateAnimationTick();
    }
}
