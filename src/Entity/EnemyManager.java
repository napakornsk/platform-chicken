package Entity;

import gameState.Playing;
import utils.LoadSave;

import static utils.Constant.EnemyConstants.BABY_CHICK_HEIGHT;
import static utils.Constant.EnemyConstants.BABY_CHICK_HEIGHT_DEFAULT;
import static utils.Constant.EnemyConstants.BABY_CHICK_WIDTH;
import static utils.Constant.EnemyConstants.BABY_CHICK_WIDTH_DEFAULT;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;

public class EnemyManager {
    Playing playing;
    BufferedImage[][] babyChickArr;
    ArrayList<BabyChick> babyChicks = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        babyChicks = LoadSave.GetBabyChicks();
        System.out.println("Size of baby chick " + babyChicks.size());
    }

    public void update() {
        for (BabyChick b : babyChicks) {
            b.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBabyChick(g, xLvlOffset);
    }

    private void drawBabyChick(Graphics g, int xLvlOffset) {
        for (BabyChick b : babyChicks) {
            g.drawImage(babyChickArr[b.getEnemyState()][b.getAnimIndex()],
                    (int) b.getHitbox().x - xLvlOffset, (int) b.getHitbox().y,
                    BABY_CHICK_WIDTH, BABY_CHICK_HEIGHT,
                    null);
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
}
