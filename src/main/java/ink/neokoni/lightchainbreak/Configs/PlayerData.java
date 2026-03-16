package ink.neokoni.lightchainbreak.Configs;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.Configs.Datas.PlayerDataInfo;
import ink.neokoni.lightchainbreak.Configs.SQL.MariadbAdapter;
import ink.neokoni.lightchainbreak.Configs.SQL.SQLAdapter;
import ink.neokoni.lightchainbreak.Configs.SQL.SQLiteAdapter;
import ink.neokoni.lightchainbreak.Utils.FileUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerData {
    private static File playerDataYamlFile;
    private final static String oldPlayerDataYamlFileName = "playerData.yml";
    private final static String playerDataYamlFileName = "PlayerData.yml";
    @Getter private static String dataType;
    private static YamlConfiguration playerDataYaml;
    private static SQLAdapter sqlAdapter;
    private static Map<Player, PlayerDataInfo> cachedPlayerDataMap;
    public static void init() {
        dataType = Config.getConfig().getDataStorageInfo().type();
        cachedPlayerDataMap = new ConcurrentHashMap<>();
        switch (dataType.toLowerCase()) {
            case "yaml" -> initYaml();
            case "mysql" -> sqlAdapter = new SQLAdapter();
            case "mariadb" -> sqlAdapter = new MariadbAdapter();
            case "sqlite" -> sqlAdapter = new SQLiteAdapter();
            default -> {
                LightChainBreak.getInstance().getLogger()
                        .warning("Unknow data storage type: " + dataType + ", defaulting to YAML.");
                initYaml();
            }
        }
    }

    public static PlayerDataInfo getPlayerData(Player player, boolean nullable) {
        if (cachedPlayerDataMap.containsKey(player)) {
            return cachedPlayerDataMap.get(player);
        }
        PlayerDataInfo data = null;
        if (nullable) return data; // not create
        if (Objects.equals(dataType, "yaml")) {
            data = new PlayerDataInfo(
                    player.getUniqueId(),
                    playerDataYaml.getBoolean(player.getUniqueId() + ".enabled", false),
                    playerDataYaml.getBoolean(player.getUniqueId() + ".display-count", false),
                    playerDataYaml.getBoolean(player.getUniqueId() + ".sneak-to-enable", false),
                    playerDataYaml.getBoolean(player.getUniqueId() + ".item-protective", false)
            );
        } else {
            data = sqlAdapter.getPlayerData(player);
        }
        savePlayerData(player, data);
        return data;
    }

    public static void removePlayerCacheData(Player player) {
        cachedPlayerDataMap.remove(player);
    }

    @SneakyThrows
    public static void savePlayerData(Player player, PlayerDataInfo data) {
        if (Objects.equals(dataType, "yaml")) {
            playerDataYaml.set(player.getUniqueId() + ".enabled", data.isEnabled());
            playerDataYaml.set(player.getUniqueId() + ".display-count", data.isDisplayCount());
            playerDataYaml.set(player.getUniqueId() + ".sneak-to-enable", data.isSneakToEnable());
            playerDataYaml.set(player.getUniqueId() + ".item-protective", data.isItemProtective());
            playerDataYaml.save(playerDataYamlFile);
        } else {
            LightChainBreak.getAsyncScheduler().runNow(LightChainBreak.getInstance(),
                    scheduledTask -> sqlAdapter.savePlayerData(player, data));
        }
        cachedPlayerDataMap.put(player, data);
    }

    public static void initYaml() {
        if (FileUtils.isFileExist(oldPlayerDataYamlFileName)) {
            LightChainBreak.getInstance().getLogger().info("Found old playerData.yml, renaming to PlayerData.yml");
            FileUtils.getFile(oldPlayerDataYamlFileName).renameTo(FileUtils.getFile(playerDataYamlFileName));
        }
        playerDataYamlFile = FileUtils.getFile(playerDataYamlFileName);
        playerDataYaml = YamlConfiguration.loadConfiguration(playerDataYamlFile);
        dataType = "yaml";
    }

    public static void close() {
        if (!dataType.equals("yaml")) {
            sqlAdapter.close();
        }
    }
}
