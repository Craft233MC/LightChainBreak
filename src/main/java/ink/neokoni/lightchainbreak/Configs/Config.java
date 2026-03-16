package ink.neokoni.lightchainbreak.Configs;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import ink.neokoni.lightchainbreak.Utils.FileUtils;
import lombok.Getter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Config {
    private static BaseConfig config;
    private static String configName = "config.yml";
    private static Path configFile;
    @Configuration
    public static class BaseConfig {
        private final MineGroup mine = new MineGroup(
                "lightchainbreak.group.mine",
                List.of("NETHERITE_PICKAXE",
                        "DIAMOND_PICKAXE",
                        "IRON_PICKAXE",
                        "GOLDEN_PICKAXE",
                        "COPPER_PICKAXE",
                        "STONE_PICKAXE",
                        "WOODEN_PICKAXE"),
                List.of("COAL_ORE",
                        "DEEPSLATE_COAL_ORE",
                        "IRON_ORE",
                        "DEEPSLATE_IRON_ORE",
                        "COPPER_ORE",
                        "DEEPSLATE_COPPER_ORE",
                        "GOLD_ORE",
                        "DEEPSLATE_GOLD_ORE",
                        "REDSTONE_ORE",
                        "DEEPSLATE_REDSTONE_ORE",
                        "EMERALD_ORE",
                        "DEEPSLATE_EMERALD_ORE",
                        "LAPIS_ORE",
                        "DEEPSLATE_LAPIS_ORE",
                        "DIAMOND_ORE",
                        "DEEPSLATE_DIAMOND_ORE",
                        "NETHER_GOLD_ORE",
                        "NETHER_QUARTZ_ORE",
                        "ANCIENT_DEBRIS",
                        "GLOWSTONE"));
        private final MineGroup wood = new MineGroup(
                "lightchainbreak.group.wood",
                List.of("NETHERITE_AXE",
                        "DIAMOND_AXE",
                        "IRON_AXE",
                        "GOLDEN_AXE",
                        "COPPER_AXE",
                        "STONE_AXE",
                        "WOODEN_AXE"),
                List.of("OAK_LOG",
                        "SPRUCE_LOG",
                        "BIRCH_LOG",
                        "JUNGLE_LOG",
                        "ACACIA_LOG",
                        "CHERRY_LOG",
                        "DARK_OAK_LOG",
                        "MANGROVE_LOG",
                        "PALE_OAK_LOG",
                        "CRIMSON_STEM",
                        "WARPED_STEM"));
        private final MineGroup leaves = new MineGroup(
                "lightchainbreak.group.leaves",
                List.of("SHEARS"),
                List.of("OAK_LEAVES",
                        "SPRUCE_LEAVES",
                        "BIRCH_LEAVES",
                        "JUNGLE_LEAVES",
                        "ACACIA_LEAVES",
                        "DARK_OAK_LEAVES",
                        "MANGROVE_LEAVES",
                        "AZALEA_LEAVES",
                        "PALE_OAK_LEAVES",
                        "CHERRY_LEAVES",
                        "FLOWERING_AZALEA_LEAVES"));
        private final MineGroup wart_block = new MineGroup(
                "lightchainbreak.group.wart_block",
                List.of("NETHERITE_HOE",
                        "DIAMOND_HOE",
                        "IRON_HOE",
                        "GOLDEN_HOE",
                        "STONE_HOE",
                        "WOODEN_HOE",
                        "COPPER_HOE"),
                List.of("WARPED_WART_BLOCK",
                        "NETHER_WART_BLOCK"));
        @Comment({"Max chain break block count at once",
                "一次性可以连锁的最大数量"})
        @Getter private int maxBreak=64;
        @Comment({"Enable diagonal block be counted",
                "This may cost more performance for detection",
                "When disabled, only 6 BlockUtils need to be detected, and 26 BlockUtils will be detected after enabled.",
                "启用对角的方块连锁",
                "这可能花费更多性能用于检测",
                "禁用时只需要检测6个方块，启用后会检测26个方块"})
        @Getter private boolean diagonalBreak=false;
        @Comment("数据存储设置")
        @Getter private DataStorageInfo dataStorageInfo = new DataStorageInfo(
                "YAML",
                "127.0.0.1",
                3306,
                "lightchianbreak",
                "Passw0rd",
                "lightchianbreak",
                ""
        );
        @Comment({"Chain break groups",
                "when player has one of the tools in the list, and the block is in the list, the chain break will be enabled",
                "连锁破坏组",
                "当玩家有该组其中之一的工具时，并且方块属于该组中的方块时，连锁破坏将启用"})
        @Getter private Map<String, MineGroup> groups = Map.of(
                "mine", mine,
                "wood", wood,
                "leaves", leaves,
                "wart_block", wart_block);
    }
    public record MineGroup(
        @Comment({"The permission required to use this group",
                "if set null, \"\" , none, NONE or keep empty, the permission will be ignored",
                "使用此组所需的权限",
                "如果设置为null，\"\"，none，NONE或保持空白，则将忽略权限"})
        @Getter String permission,
        @Getter List<String> tools,
        @Getter List<String> target
    ) {}

    public record DataStorageInfo(
            @Comment({"Data Storage Type, available: YAML, SQLITE, MYSQL, MARIADB"
                    ,"数据存储类型，可用: YAML, SQLITE, MYSQL, MARIADB"})
            String type,
            @Comment({"These content is only used when the type is not YAML and SQLITE",
                    "Database host address",
                    "以下内容仅在非YAML和SQLITE时使用",
                    "数据库地址"})
            String host,
            @Comment({"Database port",
                    "数据库端口"})
            int port,
            @Comment({"Database username",
                    "数据库用户名"})
            String username,
            @Comment({"Database password",
                    "数据库密码"})
            String password,
            @Comment({"Database name",
                    "数据库名称"})
            String datebase,
            @Comment({"Other args, when empty or null, will be ignored",
                    "These content will be added to the end of jdbc url (omit \"?\")",
                    "其他参数, 为空或为null时忽略",
                    "内容将会加入到jdbc地址末尾(省略\"?\")"})
            String args
    ) {}

    public static void init() {
        config = new BaseConfig();
        configFile = FileUtils.getFilePath(configName);

        load();
    }

    private static void load() {
        if (!FileUtils.isFileExist(configFile)) {
            YamlConfigurations.save(configFile, BaseConfig.class, config);
        }

        YamlConfigurationProperties properties = YamlConfigurationProperties.newBuilder()
                .inputNulls(true)
                .outputNulls(true)
                .build();

        config = YamlConfigurations.update(configFile, BaseConfig.class, properties);
    }

    public static BaseConfig getConfig() {
        return config;
    }
}
