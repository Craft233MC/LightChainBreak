package ink.neokoni.lightchainbreak.Handlers;

import ink.neokoni.lightchainbreak.Configs.Datas.PlayerDataInfo;
import ink.neokoni.lightchainbreak.Configs.PlayerData;
import ink.neokoni.lightchainbreak.LightChainBreak;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinQuitListener implements Listener {
    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        LightChainBreak.getAsyncScheduler().runNow(
                LightChainBreak.getInstance(),
                scheduledTask -> {
                    Player player = event.getPlayer();
                    PlayerDataInfo thisPlayerData = PlayerData.getPlayerData(player, true);
                    if (thisPlayerData!=null) PlayerData.getPlayerData(player, false);
                }
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData.removePlayerCacheData(event.getPlayer());
    }
}
