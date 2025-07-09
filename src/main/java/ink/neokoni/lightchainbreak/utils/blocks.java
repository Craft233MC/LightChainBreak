package ink.neokoni.lightchainbreak.utils;

import org.bukkit.block.Block;

public class blocks {
    private static int countBreakBlocks;

    private static final int[][] ORTHOGONAL_OFFSETS = {
            {0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}
    };

    private static final int[][] ALL_OFFSETS = {
            {0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1},

            {1, 1, 0}, {1, -1, 0}, {-1, 1, 0}, {-1, -1, 0},
            {1, 0, 1}, {1, 0, -1}, {-1, 0, 1}, {-1, 0, -1},
            {0, 1, 1}, {0, 1, -1}, {0, -1, 1}, {0, -1, -1},

            {1, 1, 1}, {1, 1, -1}, {1, -1, 1}, {1, -1, -1},
            {-1, 1, 1}, {-1, 1, -1}, {-1, -1, 1}, {-1, -1, -1}
    };

    public static Block[] getRelatives(Block block) {
        return file.getConfig("config").getBoolean("diagonal-break", false)
                ? getBlocks(block, ALL_OFFSETS)
                : getBlocks(block, ORTHOGONAL_OFFSETS);
    }

    private static Block[] getBlocks(Block center, int[][] offsets) {
        Block[] result = new Block[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            int[] offset = offsets[i];
            result[i] = center.getRelative(offset[0], offset[1], offset[2]);
        }
        return result;
    }

    public static void setCountBreakBlocks(int num) {
        countBreakBlocks = num;
    }

    public static int getCountBreakBlocks() {
        return countBreakBlocks;
    }
}
