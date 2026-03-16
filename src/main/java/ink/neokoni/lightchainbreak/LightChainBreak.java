package ink.neokoni.lightchainbreak;

import ink.neokoni.lightchainbreak.Configs.PlayerData;
import ink.neokoni.lightchainbreak.PAPIs.StatusPAPI;
import ink.neokoni.lightchainbreak.Utils.TextUtils;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ink.neokoni.lightchainbreak.Utils.FileUtils;
import ink.neokoni.lightchainbreak.Handlers.*;

public final class LightChainBreak extends JavaPlugin {
    private static LightChainBreak instance;
    public static String version;
    public static Boolean residencePlugin = false;
    @Override
    public void onEnable() {
        instance = this;
        version = getPluginMeta().getVersion();

        regEvent();
        regCommand();
        FileUtils.loadAllConfigs();
        regPapi();
        regResidence();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerData.close();
    }

    public static LightChainBreak getInstance() {
        return instance;
    }

    private void regEvent(){
        new PlayerBreakBlockListener().register(this);
        new PlayerJoinQuitListener().register(this);
    }

    private void regCommand(){
        new Commands().register(this);
    }

    private void regPapi() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new StatusPAPI().register();
            getLogger().info(TextUtils.getLangString("linkd-plugin", "%plugin%","PlaceholderAPI"));
        }
    }

    private void regResidence() {
        if (Bukkit.getPluginManager().isPluginEnabled("Residence")) {
            residencePlugin = true;
            getLogger().info(TextUtils.getLangString("linkd-plugin", "%plugin%", "Residence"));
        }
    }

    public static AsyncScheduler getAsyncScheduler() {
        return instance.getServer().getAsyncScheduler();
    }
}
