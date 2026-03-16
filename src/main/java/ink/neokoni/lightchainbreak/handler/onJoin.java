package ink.neokoni.lightchainbreak.handler;

import ink.neokoni.lightchainbreak.configs.Datas.PlayerDataInfo;
import ink.neokoni.lightchainbreak.configs.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class onJoin implements Listener {
    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataInfo thisPlayerData = PlayerData.getPlayerData(player, true);
        if (thisPlayerData!=null) PlayerData.getPlayerData(player, false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData.removePlayerCacheData(event.getPlayer());
    }
}
