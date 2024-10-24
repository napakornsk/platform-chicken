package Entity;

import static utils.Constant.EnemyConstants.*;

import java.awt.geom.Rectangle2D;
import java.sql.Time;

import object.ObjectManager;

public class BabyChick extends Enemy {
    Rectangle2D attackBox;
    long cooldownAttack = 600; // 600ms -> 0.6 s

    public BabyChick(float x, float y, ObjectManager objectManager) {
        super(x, y, BABY_CHICK_WIDTH, BABY_CHICK_HEIGHT, BABY_CHICK, objectManager);
        initHitbox(30, 32);
    }

    // private void initAttackBox() {
    // }

    public void update(int[][] lvlData, Player player) {
        long now = System.currentTimeMillis();
        if (!canAttack) {
            if (now - lastShotTime > cooldownAttack) {
                canAttack = true;
            }
        }
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
                            attack();
                        }
                    }
                    // Continue moving even if player is detected
                    move(lvlData);
                    break;
            }
        }
    }
}
