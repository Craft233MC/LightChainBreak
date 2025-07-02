package ink.neokoni.lightchainbreak.utils;

import org.bukkit.block.Block;

public class blocks {
    private static int countBreakBlocks;
    public static Block[] getRelative(Block block) {
        return new Block[]{
                block.getRelative(0, 1, 0),
                block.getRelative(0, -1, 0),
                block.getRelative(1, 0, 0),
                block.getRelative(-1, 0, 0),
                block.getRelative(0, 0, 1),
                block.getRelative(0, 0, -1),

                block.getRelative(1, 1, 0),
                block.getRelative(1, -1, 0),
                block.getRelative(-1, 1, 0),
                block.getRelative(-1, -1, 0),
                block.getRelative(1, 0, 1),
                block.getRelative(1, 0, -1),
                block.getRelative(-1, 0, 1),
                block.getRelative(-1, 0, -1),
                block.getRelative(0, 1, 1),
                block.getRelative(0, 1, -1),
                block.getRelative(0, -1, 1),
                block.getRelative(0, -1, -1),
                block.getRelative(1, 1, 1),
                block.getRelative(1, 1, -1),
                block.getRelative(1, -1, 1),
                block.getRelative(1, -1, -1),
                block.getRelative(-1, 1, 1),
                block.getRelative(-1, 1, -1),
                block.getRelative(-1, -1, 1),
                block.getRelative(-1, -1, -1)
        };
    }

    public static String getBCountBreakBlocks(){
        return String.valueOf(countBreakBlocks);
    }

    public static void setCountBreakBlocks(int num) {
        countBreakBlocks = num;
    }
}
