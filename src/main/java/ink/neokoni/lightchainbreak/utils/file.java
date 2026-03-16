package ink.neokoni.lightchainbreak.utils;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.configs.PlayerData;
import ink.neokoni.lightchainbreak.configs.config;
import ink.neokoni.lightchainbreak.configs.language;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public class file {
    private static final  LightChainBreak plugin = LightChainBreak.getInstance();
    private static Path dataPath = LightChainBreak.getInstance().getDataFolder().toPath();

    public static Path getFilePath(String path) {
        return new File(dataPath.resolve(path).toUri()).toPath();
    }

    public static File getFile(String path) {
        return new File(getFilePath(path).toUri());
    }

    public static boolean isFileExist(Path path) {
        return path.toFile().exists();
    }

     public static void loadAllConfigs() {
         config.init();
         language.init();
         PlayerData.init();
     }

     public static void reloadAllConfigs(@Nullable CommandSender sender) {
        PlayerData.close();

        loadAllConfigs();

        if (sender!=null) {
            sender.sendMessage(text.getLang("reload"));
        }
     }
}
