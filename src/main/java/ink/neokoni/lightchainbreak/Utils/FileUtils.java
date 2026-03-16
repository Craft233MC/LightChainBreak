package ink.neokoni.lightchainbreak.Utils;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.Configs.PlayerData;
import ink.neokoni.lightchainbreak.Configs.Config;
import ink.neokoni.lightchainbreak.Configs.Language;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public class FileUtils {
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

    public static boolean isFileExist(String path) {
        return dataPath.resolve(path).toFile().exists();
    }

     public static void loadAllConfigs() {
         Config.init();
         Language.init();
         PlayerData.init();
     }

     public static void reloadAllConfigs(@Nullable CommandSender sender) {
        PlayerData.close();

        loadAllConfigs();

        if (sender!=null) {
            sender.sendMessage(TextUtils.getLang("reload"));
        }
     }
}
