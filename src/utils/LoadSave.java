package utils;

import static main.Game.TILE_SIZE;
import static utils.Constant.EnemyConstants.BABY_CHICK;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Entity.BabyChick;
import object.ObjectManager;

import java.util.ArrayList;

public class LoadSave {
    public static final String PLAYER_ATLAS = "resources/player/player.png";
    public static final String LEVEL_ATLAS = "resources/level/lvl.png";
    // public static final String LEVEL_ONE_ATLAS = "resources/level/lvl_one.png";
    public static final String LEVEL_ONE_ATLAS = "resources/level/lvl_one_big.png";
    public static final String MENU_BUTTONS = "resources/ui/button_atlas.png";
    public static final String MENU_BG = "resources/ui/menu_background.png";
    public static final String MENU_BG_IMG = "resources/level/fajrbackground.png";
    public static final String PLAYING_BG_IMG = "resources/level/sunsetbackground.png";
    public static final String BIG_CLOUD = "resources/level/big_clouds.png";
    public static final String SMALL_CLOUD = "resources/level/small_clouds.png";
    public static final String ENEMY = "resources/enemy/enemy.png";
    public static final String HEART = "resources/player/heart.png";
    public static final String BULLET = "resources/enemy/bullet.png";
    public static final String HAYSTACK = "resources/level/haystack.png";

    public static final String LOST_BG = "resources/level/lost-bg.jpg";
    public static final String WIN_BG = "resources/level/win-bg.jpg";

    public static BufferedImage GetSpriteAtLast(String filePath) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filePath);
        if (is == null) {
            System.out.println("Resource not found!, filePath: " + filePath);
        }
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    public static ArrayList<BabyChick> GetBabyChicks(ObjectManager objectManager) {
        BufferedImage img = GetSpriteAtLast(LEVEL_ONE_ATLAS);
        ArrayList<BabyChick> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == BABY_CHICK) {
                    list.add(new BabyChick(i * TILE_SIZE, j * TILE_SIZE, objectManager));
                }
            }

        return list;
    }

    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtLast(LEVEL_ONE_ATLAS);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value > 3) {
                    value = 3;
                }
                lvlData[j][i] = value;
            }

        return lvlData;
    }
}
