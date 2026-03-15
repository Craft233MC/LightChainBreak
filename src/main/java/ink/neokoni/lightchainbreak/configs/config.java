package ink.neokoni.lightchainbreak.configs;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;

public class config {
    @Configuration
    public class BaseConfig {
        @Comment({"Max chain break block count at once",
                "一次性可以连锁的最大数量"})
        private int maxBreak=64;
        @Comment({"Enable diagonal block be counted",
                "This may cost more performance for detection",
                "When disabled, only 6 blocks need to be detected, and 26 blocks will be detected after enabled.",
                "启用对角的方块连锁",
                "这可能花费更多性能用于检测",
                "禁用时只需要检测6个方块，启用后会检测26个方块"})
        private boolean diagonalBreak=false;
        private boolean
    }
}
