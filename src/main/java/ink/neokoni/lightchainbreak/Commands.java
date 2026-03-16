package ink.neokoni.lightchainbreak;

import ink.neokoni.lightchainbreak.Configs.Datas.PlayerDataInfo;
import ink.neokoni.lightchainbreak.Configs.PlayerData;
import ink.neokoni.lightchainbreak.Utils.FileUtils;
import ink.neokoni.lightchainbreak.Utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Commands implements CommandExecutor {
    private static final List<String> subCommands = List.of("reload", "help", "toggle", "about");
    private static final List<String> toggleSelector = List.of("enable", "sneak-enable", "display-count", "item-protective");
    public void register(JavaPlugin plugin) {
        plugin.getCommand("lightchainbreak").setExecutor(this);
        plugin.getCommand("lightchainbreak").setTabCompleter((sender, command, string, args) -> {
            if (args.length==1) {
                return subCommands;
            }

            if (args.length==2 && args[0].equals("toggle")){
                return toggleSelector;
            }
            return null;
        });
    }

    private boolean isPlayer(CommandSender commandSender){
        return commandSender instanceof org.bukkit.entity.Player;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings==null || strings.length==0 || strings[0].equals("help")){
            help(commandSender);
            return true;
        }

        if (strings[0].equals("about")){
            about(commandSender);
            return true;
        }

        if (strings[0].equals("toggle")){
            if (!isPlayer(commandSender)){
                 commandSender.sendMessage(TextUtils.getLang("error.only-player"));
                 return true;
            }

            if (strings.length==1 || strings[1].equals("enable") || strings[1].isEmpty()) {
                toggleEnable((Player) commandSender);
                return true;
            } else if (strings[1].equals("sneak-enable")) {
                toggleSneakEnable((Player) commandSender);
                return true;
            } else if (strings[1].equals("display-count")) {
                toggleDisplayCount((Player) commandSender);
                return true;
            } else if ( strings[1].equals("item-protective")) {
                toggleItemProtective((Player) commandSender);
                return true;
            } else {
                commandSender.sendMessage(TextUtils.getLang("toggle.error"));
            }
            return true;
        }

        if (strings[0].equals("reload")){
            FileUtils.reloadAllConfigs(commandSender);
            return true;
        }
        return true;
    }

    private void help(CommandSender c){
        if (!c.hasPermission("lightchainbreak.help")){
            c.sendMessage(TextUtils.getLang("error.no-perms"));
            return;
        }
        c.sendMessage(TextUtils.getLang("running", "%version%", LightChainBreak.version));
        c.sendMessage("");
        c.sendMessage(Component.text("--------------------").color(TextColor.fromCSSHexString("#C8F1EF")));
        c.sendMessage(Component.text("/lightchainbreak reload").color(TextColor.fromCSSHexString("#C8F1EF")).append(TextUtils.getLang("help.reload")));
        c.sendMessage(Component.text("/lightchainbreak toggle [enable|sneak-enable|display-count|item-protective]").color(TextColor.fromCSSHexString("#C8F1EF")).append(TextUtils.getLang("help.toggle")));
        c.sendMessage(Component.text("/lightchainbreak about").color(TextColor.fromCSSHexString("#C8F1EF")).append(TextUtils.getLang("help.about")));
        c.sendMessage(Component.text("/lightchainbreak help").color(TextColor.fromCSSHexString("#C8F1EF")).append(TextUtils.getLang("help.help")));
    }

    private void about(CommandSender c){
        if (!c.hasPermission("lightchainbreak.about")){
            c.sendMessage(TextUtils.getLang("error.no-perms"));
            return;
        }
        c.sendMessage(TextUtils.getLang("running", "%version%", LightChainBreak.version));
        c.sendMessage("");
        c.sendMessage(TextUtils.getLang("about.desc"));
        c.sendMessage("");
        c.sendMessage(TextUtils.getLang("about.link")
                .append(Component.text("[GitHub]").clickEvent(ClickEvent.openUrl("https://github.com/Craft233MC/LightChainBreak"))
                        .color(TextColor.fromCSSHexString("#C8F1EF"))));

    }

    private void toggleEnable(Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(TextUtils.getLang("error.no-perms"));
            return;
        }

        PlayerDataInfo playerData = PlayerData.getPlayerData(p, false);

        if(!playerData.isEnabled()){
            playerData.setEnabled(true);
            p.sendMessage(TextUtils.getLang("toggle.enabled"));
        } else {
            playerData.setEnabled(false);
            p.sendMessage(TextUtils.getLang("toggle.disabled"));
        }

        PlayerData.savePlayerData(p, playerData);
    }

    private void toggleSneakEnable(Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(TextUtils.getLang("error.no-perms"));
            return;
        }

        PlayerDataInfo playerData = PlayerData.getPlayerData(p, false);
        if (playerData.isSneakToEnable()) {
            playerData.setSneakToEnable(false);
            p.sendMessage( TextUtils.getLang("toggle.sneak-to-disabled"));
        } else {
            playerData.setSneakToEnable(true);
            p.sendMessage( TextUtils.getLang("toggle.sneak-to-enabled"));
        }

        PlayerData.savePlayerData(p, playerData);
    }

    private void toggleDisplayCount (Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(TextUtils.getLang("error.no-perms"));
            return;
        }

         PlayerDataInfo playerData = PlayerData.getPlayerData(p, false);
         if (playerData.isDisplayCount()) {
             playerData.setDisplayCount(false);
             p.sendMessage( TextUtils.getLang("toggle.display-count-disabled"));
         } else {
             playerData.setDisplayCount(true);
             p.sendMessage( TextUtils.getLang("toggle.display-count-enabled"));
         }

        PlayerData.savePlayerData(p, playerData);
    }

    private void toggleItemProtective (Player p){
         if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(TextUtils.getLang("error.no-perms"));
            return;
        }

         PlayerDataInfo playerData = PlayerData.getPlayerData(p, false);
         if (playerData.isItemProtective()) {
             playerData.setItemProtective(false);
             p.sendMessage( TextUtils.getLang("toggle.item-protective-disabled"));
         } else {
             playerData.setItemProtective(true);
             p.sendMessage( TextUtils.getLang("toggle.item-protective-enabled"));
         }

        PlayerData.savePlayerData(p, playerData);
    }
}
