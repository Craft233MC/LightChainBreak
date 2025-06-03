package ink.neokoni.lightchainbreak;

import ink.neokoni.lightchainbreak.papi.statusPapi;
import ink.neokoni.lightchainbreak.utils.text;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ink.neokoni.lightchainbreak.utils.file;
import ink.neokoni.lightchainbreak.handler.*;

public final class LightChainBreak extends JavaPlugin {
    private static LightChainBreak instance;
    public static final String version = "0.2";
    @Override
    public void onEnable() {
        instance = this;
        regEvent();
        regCommand();
        new file().reloadConfig();
        regPapi();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LightChainBreak getInstance() {
        return instance;
    }

    private void regEvent(){
        new onBreak().register(this);
        new onJoin().register(this);
    }

    private void regCommand(){
        new commands().register(this);
    }

    private void regPapi() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new statusPapi().register();
        }
        getLogger().info(text.getLangString("linkd-plugin", "PlaceholderAPI"));
    }
}
