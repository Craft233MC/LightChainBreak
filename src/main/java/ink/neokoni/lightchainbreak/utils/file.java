package ink.neokoni.lightchainbreak.utils;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.handler.onJoin;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class file {
    private static final  LightChainBreak plugin = LightChainBreak.getInstance();
    private static YamlConfiguration config;
    private static YamlConfiguration lang;
    private static YamlConfiguration playerData;

     public boolean isFileExist(String fileName){
         return new File(plugin.getDataFolder(),  fileName+".yml").exists();
     }

     public void createFile(String fileName) {
         plugin.saveResource(fileName+".yml", false);
     }

     public void reloadConfig(@Nullable CommandSender sender){
         if (config==null) {
             reloadConfigLogic();
             return;
         }

         ScheduledTask reloadTask = Bukkit.getAsyncScheduler().runNow(plugin, task -> reloadConfigLogic());
         if (sender != null){
             Bukkit.getAsyncScheduler().runAtFixedRate(plugin, task -> {
                 if (reloadTask.getExecutionState().equals(ScheduledTask.ExecutionState.FINISHED)){
                     Bukkit.getServer().broadcast(text.getLang("reload"));
                     task.cancel();
                 }
             }, 50, 50, TimeUnit.MILLISECONDS); // 1000 / 20 = 50ms = 1tick
         }
     }

     public void reloadConfigLogic(){
         config = loadConfig("config");
         lang = loadConfig("lang");
         playerData = loadConfig("playerData");

         Bukkit.getServer().getOnlinePlayers().forEach(player -> {
             new onJoin().onPlayerJoin(new PlayerJoinEvent(player, Component.empty()));
         });
     }

     public static YamlConfiguration getConfig(String fileName) {
         return switch (fileName) {
             case "config" -> config;
             case "lang" -> lang;
             case "playerData" -> playerData;
             default -> null;
         };
     }

     public YamlConfiguration loadConfig(String fileName) {
         if(!isFileExist(fileName)){
             createFile(fileName);
         }
         return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), fileName+".yml"));
     }

     public boolean saveConfig(String fileName, YamlConfiguration config){
         try {
             config.save(new File(plugin.getDataFolder(), fileName+".yml"));

             switch (fileName) {
                 case "config" -> file.config = config;
                 case "lang" -> lang = config;
                 case "playerData" -> playerData = config;
             }

             return true;
         } catch (IOException e) {
             e.printStackTrace();
             return false;
         }
     }
}
