package ink.neokoni.lightchainbreak.Utils;

import ink.neokoni.lightchainbreak.Configs.Config;
import org.bukkit.block.Block;

public class BlockUtils {
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
        return Config.getConfig().isDiagonalBreak()
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
}
