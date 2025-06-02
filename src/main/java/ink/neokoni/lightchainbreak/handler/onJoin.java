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

    private boolean isBoolean(String str) {
        if(str==null) return false;
        return str.equals("true") || str.equals("false");
    }
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        YamlConfiguration data = file.getConfig("playerData");
        if(!isBoolean(data.getString(player.getUniqueId()+".enabled"))){
            data.set(player.getUniqueId()+".enabled", false);
        }
        if(!isBoolean(data.getString(player.getUniqueId()+".display-count"))){
            data.set(player.getUniqueId()+".display-count", false);
        }
        if(!isBoolean(data.getString(player.getUniqueId()+".sneak-to-enable"))){
            data.set(player.getUniqueId()+".sneak-to-enable", false);
        }
         if(!isBoolean(data.getString(player.getUniqueId()+".item-protective"))){
            data.set(player.getUniqueId()+".item-protective", false);
        }
        new file().saveConfig("playerData", data);
    }
}
