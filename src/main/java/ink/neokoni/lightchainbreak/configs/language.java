package ink.neokoni.lightchainbreak.configs;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.utils.file;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.nio.file.Path;

public class language {
    private static Path langFile;
    private static String langFileName = "lang.yml";
    @Getter private static YamlConfiguration lang;
    public static void init() {
        langFile = file.getFilePath(langFileName);
        if (!file.isFileExist(langFile)) {
            LightChainBreak.getInstance().saveResource(langFileName, false);
        }
        lang = YamlConfiguration.loadConfiguration(langFile.toFile());
    }
}
