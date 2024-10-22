package Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

import static utils.Constant.PlayerConstants.*;
import static main.Game.SCALE;
import static utils.Constant.Directions.*;
import static utils.Helper.*;

public class Player extends Entity {

    BufferedImage[][] animation;

    int animTick, animIndex, animSpeed = 40;
    int playerAction = RIGHT;
    int playerDir = 0;
    boolean isMoving = true, isActaking = false;
    int[][] lvlData;
    float xDrawOffset = 2 * Game.SCALE;
    float yDrawOffset = 2 * Game.SCALE;

    // Jumping / Gravity
    float airSpeed = 0f;
    float gravity = 0.04f * Game.SCALE;
    float jumpSpeed = -3f * Game.SCALE;
    float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    boolean inAir = false;

    public boolean isActaking() {
        return isActaking;
    }

    public void setActaking(boolean isActaking) {
        this.isActaking = isActaking;
    }

    boolean left, right, up, isJump;

    float speed = 1.0f * SCALE;

    public int getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(int playerAction) {
        this.playerAction = playerAction;
        int startAnim = playerAction;

        if (startAnim != playerAction) {
            resetAnimTick();
        }
    }

    private void resetAnimTick() {
        animTick = 0;
        animIndex = 0;
    }

    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        loadAnimation();
        initHitbox(30, 32);

    }

    public void update() {
        updatePos();
        updateAnimTick();
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(
                animation[playerAction][animIndex],
                (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset),
                (int) width, (int) height,
                null);
        // drawHitbox(g, (int) lvlOffset);
    }

    public void setDirection(int dir) {
        this.playerDir = dir;
        isMoving = true;
    }

    public void setRectPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void updatePos() {
        isMoving = false;
        if (isJump)
            jump();

        if (!inAir)
            if ((!isLeft() && !isRight()) || (isLeft() && isRight()))
                return;

        float xSpeed = 0;

        if (left && !isRight()) {
            setDirection(DIR_LEFT);
            playerAction = LEFT;
            xSpeed -= speed;
        }
        if (right && !isLeft()) {
            setDirection(DIR_RIGHT);
            playerAction = RIGHT;
            xSpeed += speed;
        }

        // if (xSpeed == 0 && !inAir) {
        // return;
        // }

        if (!inAir) {
            if (!isEntityOnFloor(hitbox, lvlData))
                inAir = true;

        }

        if (inAir) {
            if (CanMoveHere(
                    hitbox.x, hitbox.y + airSpeed,
                    hitbox.width, hitbox.height,
                    lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;

                // updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);

        isMoving = true;
    }

    private void jump() {
        if (inAir)
            return;

        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(
                hitbox.x + xSpeed,
                hitbox.y,
                hitbox.width,
                hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void updateAnimTick() {
        animTick++;
        if (!isMoving && playerDir == DIR_LEFT) {
            animIndex = 0;
            playerAction = LEFT;
            return;
        } else if (!isMoving && playerDir == DIR_RIGHT) {
            animIndex = 0;
            playerAction = RIGHT;
            return;
        }

        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)) {
                animIndex = 0;
                isActaking = false;
            }
        }
    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtLast(LoadSave.PLAYER_ATLAS);
        animation = new BufferedImage[8][4];
        for (int row = 0; row < animation.length; row++) {
            for (int col = 0; col < animation[row].length; col++) {
                animation[row][col] = img.getSubimage(col * 32, row * 32, 32, 32);
            }
        }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    public void resetBooleans() {
        left = false;
        right = false;
        isMoving = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean isJump) {
        this.isJump = isJump;
    }
}