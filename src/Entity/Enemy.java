package Entity;

import static main.Game.SCALE;
import static main.Game.TILE_SIZE;
import static utils.Constant.EnemyConstants.*;
import static utils.Helper.CanMoveHere;
import static utils.Helper.GetEntityYPosUnderRoofOrAboveFloor;
import static utils.Helper.isEntityOnFloor;

import static utils.Helper.IsFloor;
import static utils.Helper.IsSightClear;
import static utils.Constant.Entity.*;
import static utils.Constant.Directions.*;

import object.ObjectManager;
import object.Projectile;

public abstract class Enemy extends Entity {
    protected int animIndex, enemyState, enemyType;

    protected int animTick, animSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir = false;
    // protected float fallSpeed;
    protected float gravity = 0.04f * SCALE;
    protected float walkSpeed = 0.4f * SCALE;
    protected int walkDir = ENEMY_DIR_LEFT;

    protected int tileY;
    protected float attackDistance = 7.0f * TILE_SIZE;
    float absValue;
    float detectionRange;
    protected boolean canAttack = true;
    protected long lastShotTime = 0;

    ObjectManager objectManager;

    public Enemy(float x, float y, float width, float height, int enemyType, ObjectManager objectManager) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox((int) width, (int) height);
        this.objectManager = objectManager;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(
                hitbox.x, hitbox.y + airSpeed,
                hitbox.width, hitbox.height,
                lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / TILE_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == ENEMY_DIR_LEFT) {
            xSpeed = -walkSpeed;
        } else if (walkDir == ENEMY_DIR_RIGHT) {
            xSpeed = walkSpeed;
        }

        if (CanMoveHere(
                hitbox.x + xSpeed,
                hitbox.y,
                hitbox.width, hitbox.height,
                lvlData)) {
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        changeWalkDir();
    }

    protected void turnTowardPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = ENEMY_DIR_RIGHT; // Enemy walks right
        else
            walkDir = ENEMY_DIR_LEFT; // Enemy walks left
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / TILE_SIZE);
        int absYDiff = Math.abs(playerTileY - tileY);
        int verticalRange = 0;
        if (absYDiff <= verticalRange) {
            if (isPlayerInRange(player)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        absValue = Math.abs(player.hitbox.x - hitbox.x);
        detectionRange = attackDistance * 5; // Increase multiplier for longer detection range
        return absValue <= detectionRange;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        float absValue = Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    public void attack() {
        if (canAttack) {
            canAttack = false;
            lastShotTime = System.currentTimeMillis();
            objectManager.shootBullet(this);
        }
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        animTick = 0;
        animIndex = 0;
    }

    protected void updateAnimationTick() {
        animTick++;
        if (animTick > animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(enemyType, enemyState)) {
                animIndex = 0;
                if (enemyState == ATTACK)
                    enemyState = IDLE;
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == ENEMY_DIR_LEFT)
            walkDir = ENEMY_DIR_RIGHT;
        else
            walkDir = ENEMY_DIR_LEFT;
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

    public int getWalkDir() {
        return walkDir;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        newState(ENEMY_DIR_LEFT);
        airSpeed = 0;
    }
}
