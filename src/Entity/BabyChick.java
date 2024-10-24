package Entity;

import static utils.Constant.EnemyConstants.*;

import java.awt.geom.Rectangle2D;

public class BabyChick extends Enemy {
    Rectangle2D attackBox;

    public BabyChick(float x, float y) {
        super(x, y, BABY_CHICK_WIDTH, BABY_CHICK_HEIGHT, BABY_CHICK);
        initHitbox(30, 32);
    }

    // private void initAttackBox() {
    // }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick();
    }

    void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardPlayer(player);
                        if (isPlayerCloseForAttack(player)) {
                            newState(ATTACK);
                        }
                    }
                    // Continue moving even if player is detected
                    move(lvlData);
                    break;
            }
        }
    }
}
