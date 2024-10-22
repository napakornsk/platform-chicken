package Entity;

import static utils.Constant.EnemyConstants.*;

public abstract class Enemy extends Entity {
    int animIndex, enemyState, enemyType;

    int animTick, animSpeed = 25;

    public Enemy(float x, float y, float width, float height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox((int) width, (int) height);
    }

    void updateAnimationTick() {
        animTick++;
        if (animTick > animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(enemyType, enemyState)) {
                animIndex = 0;
            }
        }
    }

    public void update() {
        updateAnimationTick();
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public void setAnimIndex(int animIndex) {
        this.animIndex = animIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
    }
}
