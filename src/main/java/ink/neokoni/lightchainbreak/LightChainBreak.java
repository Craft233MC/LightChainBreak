package ink.neokoni.lightchainbreak;

import ink.neokoni.lightchainbreak.papi.statusPapi;
import ink.neokoni.lightchainbreak.utils.text;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ink.neokoni.lightchainbreak.utils.file;
import ink.neokoni.lightchainbreak.handler.*;

public final class LightChainBreak extends JavaPlugin {
    private static LightChainBreak instance;
    public static String version;
    public static Boolean residencePlugin = false;
    @Override
    public void onEnable() {
        instance = this;
        version = this.getPluginMeta().getVersion();

        regEvent();
        regCommand();
        new file().reloadConfig();
        regPapi();
        regResidence();
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
            getLogger().info(text.getLangString("linkd-plugin", "PlaceholderAPI"));
        }
    }

    private void regResidence() {
        if (Bukkit.getPluginManager().isPluginEnabled("Residence")) {
            residencePlugin = true;
            getLogger().info(text.getLangString("linkd-plugin", "Residence"));
        }
    }
}
