package ink.neokoni.lightchainbreak.utils;

import ink.neokoni.lightchainbreak.LightChainBreak;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

     public void reloadConfig(){
         if (config==null) {
             reloadConfigLogic();
             return;
         }

         Bukkit.getAsyncScheduler().runNow(plugin, task -> reloadConfigLogic());
     }

     public void reloadConfigLogic(){
         config = loadConfig("config");
         lang = loadConfig("lang");
         playerData = loadConfig("playerData");

         Bukkit.getServer().getOnlinePlayers().forEach(player -> {
             if(playerData.getStringList(String.valueOf(player.getUniqueId())).equals(new ArrayList<String>())){
                 playerData.set(player.getUniqueId()+".enabled", false);
                 saveConfig("playerData", playerData);
             }
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
