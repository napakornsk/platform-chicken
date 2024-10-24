package utils;

import static main.Game.SCALE;

public class Constant {
    public static class EnemyConstants {
        public static final int BABY_CHICK = 10;

        public static final int RUNNING = 0;
        public static final int ATTACK = 1;
        public static final int IDLE = 2;

        public static final int BABY_CHICK_WIDTH_DEFAULT = 32;
        public static final int BABY_CHICK_HEIGHT_DEFAULT = 32;

        public static final int BABY_CHICK_WIDTH = (int) (BABY_CHICK_WIDTH_DEFAULT * SCALE);
        public static final int BABY_CHICK_HEIGHT = (int) (BABY_CHICK_HEIGHT_DEFAULT * SCALE);

        public static final int BABY_CHICK_DRAWOFFFSET_X = (int) (31 * SCALE);
        public static final int BABY_CHICK_DRAWOFFFSET_Y = (int) (29 * SCALE);

        public static int GetSpriteAmount(int enemyType, int enemyState) {
            switch (enemyType) {
                case BABY_CHICK:
                    switch (enemyState) {
                        case IDLE:
                            return 4;
                        case RUNNING:
                            return 4;
                        case ATTACK:
                            return 2;
                    }
            }
            return 0;
        }

    }

    public static class Environment {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * SCALE * 2);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * SCALE * 2);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * SCALE);

    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * SCALE);

        }

    }

    public static class Directions {
        public static final int DIR_RIGHT = 0;
        public static final int DIR_LEFT = 4;
        public static final int ENEMY_DIR_LEFT = 0;
        public static final int ENEMY_DIR_RIGHT = 2;

    }

    public static class PlayerConstants {
        public static final int RIGHT = 0;
        public static final int LEFT = 4;
        public static final int JUMPING = 0;
        public static final int ATTACK = 5;

        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case RIGHT:
                case LEFT:
                    return 4;
                case ATTACK:
                    return 5;
                default:
                    return 1;
            }
        }
    }
}
