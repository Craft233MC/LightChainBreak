package ink.neokoni.lightchainbreak.handler;

import ink.neokoni.lightchainbreak.utils.file;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class onJoin implements Listener {
    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        YamlConfiguration data = file.getConfig("playerData");
        if(data.getString(player.getUniqueId()+".enabled").isEmpty()){
            data.set(player.getUniqueId()+".enabled", false);
            new file().saveConfig("playerData", data);
        }
    }
}
