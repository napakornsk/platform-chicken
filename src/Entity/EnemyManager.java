package Entity;

import gameState.Playing;
import object.ObjectManager;
import utils.LoadSave;

import static utils.Constant.Directions.ENEMY_DIR_LEFT;
import static utils.Constant.Directions.ENEMY_DIR_RIGHT;
import static utils.Constant.EnemyConstants.BABY_CHICK_HEIGHT;
import static utils.Constant.EnemyConstants.BABY_CHICK_HEIGHT_DEFAULT;
import static utils.Constant.EnemyConstants.BABY_CHICK_WIDTH;
import static utils.Constant.EnemyConstants.BABY_CHICK_WIDTH_DEFAULT;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EnemyManager {
    Playing playing;
    BufferedImage[][] babyChickArr;
    ArrayList<BabyChick> babyChicks = new ArrayList<>();

    public EnemyManager(Playing playing, ObjectManager objectManager) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies(objectManager);
    }

    private void addEnemies(ObjectManager objectManager) {
        babyChicks = LoadSave.GetBabyChicks(objectManager);
        System.out.println("Size of baby chick " + babyChicks.size());
    }

    public void update(int[][] lvlData, Player player) {
        for (BabyChick b : babyChicks) {
            b.update(lvlData, player);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBabyChick(g, xLvlOffset);

    }

    private void drawBabyChick(Graphics g, int xLvlOffset) {
        Graphics2D g2d = (Graphics2D) g; // Use Graphics2D for advanced rendering
        for (BabyChick b : babyChicks) {
            int x = (int) b.getHitbox().x - xLvlOffset;
            int y = (int) b.getHitbox().y;
            int width = BABY_CHICK_WIDTH;
            int height = BABY_CHICK_HEIGHT;

            // check the enemy's direction and flip the image in place
            if (b.getWalkDir() == ENEMY_DIR_RIGHT) {
                // Flip in place for walking right
                g2d.drawImage(babyChickArr[b.getEnemyState()][b.getAnimIndex()],
                        x + width, y, -width, height, null);
            } else {
                // Normal drawing for walking left
                g2d.drawImage(babyChickArr[b.getEnemyState()][b.getAnimIndex()],
                        x, y, width, height, null);
            }

            b.drawHitbox(g, xLvlOffset, Color.MAGENTA);
        }
    }

    private void loadEnemyImgs() {
        babyChickArr = new BufferedImage[4][4];
        BufferedImage temp = LoadSave.GetSpriteAtLast(LoadSave.ENEMY);

        for (int j = 0; j < babyChickArr.length; j++) {
            for (int i = 0; i < babyChickArr[j].length; i++) {
                babyChickArr[j][i] = temp.getSubimage(
                        i * BABY_CHICK_WIDTH_DEFAULT,
                        j * BABY_CHICK_HEIGHT_DEFAULT,
                        BABY_CHICK_WIDTH_DEFAULT,
                        BABY_CHICK_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetAllEnemies() {
        for (BabyChick b : babyChicks) {
            b.resetEnemy();
        }
    }
}
