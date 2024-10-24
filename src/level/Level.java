package level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import object.Haystack;
import utils.Helper;
import utils.LoadSave;

public class Level {

    int[][] lvlData;
    ArrayList<Haystack> haystacks;
    BufferedImage img;

    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
        createHaystacks();
    }

    private void createHaystacks() {
        img = LoadSave.GetSpriteAtLast(LoadSave.LEVEL_ONE_ATLAS);
        haystacks = Helper.getHaystacks(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public ArrayList<Haystack> getHaystacks() {
        return haystacks;
    }
}
