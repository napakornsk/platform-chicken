package utils;

import static main.Game.TILE_SIZE;

import java.awt.geom.Rectangle2D;
import main.Game;

public class Helper {
    public static boolean CanMoveHere(
            float x, float y,
            float width, float height,
            int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;

    }

    static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(
            int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        switch (value) {
            case 3:
                return false;
            default:
                return true;
        }
    }

    public static float GetEntityXPosNextToWall(
            Rectangle2D.Float hitbox,
            float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILE_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILE_SIZE;
            int xOffset = (int) (Game.TILE_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILE_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(
            Rectangle2D.Float hitbox,
            float airSpeed) {
        int currTile = (int) (hitbox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            int tileYPos = (currTile + 1) * Game.TILE_SIZE; // Get the position of the next tile (floor)
            int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1; // Keep the player 1 pixel above the floor
        } else
            // Jumping
            return currTile * Game.TILE_SIZE;
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTileClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++)
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
		return true;
	}

	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		if (IsAllTileClear(xStart, xEnd, y, lvlData))
			for (int i = 0; i < xEnd - xStart; i++) {
				if (!IsTileSolid(xStart + i, y + 1, lvlData))
					return false;
			}
		return true;
	}

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float enemyBox, Rectangle2D.Float playerBox,
            int yTile) {
        int firstXTile = (int) (enemyBox.x / Game.TILE_SIZE);

        int secondXTile;
        if (IsSolid(playerBox.x, playerBox.y + playerBox.height + 1, lvlData))
            secondXTile = (int) (playerBox.x / Game.TILE_SIZE);
        else
            secondXTile = (int) ((playerBox.x + playerBox.width) / Game.TILE_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
    }

    // public static boolean IsSightClear(
    // int[][] lvlData, Rectangle2D.Float firstHitbox,
    // Rectangle2D.Float secondHitbox, int tileY) {
    // int firstXTile = (int) (firstHitbox.x / TILE_SIZE);
    // int secondXTile = (int) (secondHitbox.x / TILE_SIZE);
    // if (firstXTile > secondXTile)
    // return IsAllTileWalkable(secondXTile, firstXTile, tileY, lvlData);
    // else
    // return IsAllTileWalkable(firstXTile, secondXTile, tileY, lvlData);
    // }
}
