package ink.neokoni.lightchainbreak.Configs;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.Utils.FileUtils;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.nio.file.Path;

public class Language {
    private static Path langFile;
    private static String langFileName = "lang.yml";
    @Getter private static YamlConfiguration lang;
    public static void init() {
        langFile = FileUtils.getFilePath(langFileName);
        if (!FileUtils.isFileExist(langFile)) {
            LightChainBreak.getInstance().saveResource(langFileName, false);
        }
        lang = YamlConfiguration.loadConfiguration(langFile.toFile());
    }
}
