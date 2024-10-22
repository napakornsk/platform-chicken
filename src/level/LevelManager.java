package level;

import static main.Game.TILE_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {
    Game game;
    BufferedImage[] levelSprite;
    Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        // levelSprite = LoadSave.GetSpriteAtLast(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtLast(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[4];
        for (int i = 0; i < levelSprite.length; i++) {
            levelSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
        }
    }

    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < Game.TILE_IN_HEIGHT; j++)
            for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(
                        levelSprite[index],
                        (i * TILE_SIZE) - lvlOffset, j * TILE_SIZE,
                        TILE_SIZE, TILE_SIZE,
                        null);
            }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
