package ink.neokoni.lightchainbreak;

import ink.neokoni.lightchainbreak.utils.file;
import ink.neokoni.lightchainbreak.utils.text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class commands implements CommandExecutor {
    private static final List<String> subCommands = List.of("reload", "help", "toggle", "about");
    private static final List<String> toggleSelector = List.of("enable", "sneak-enable", "display-count", "item-protective");
    public void register(JavaPlugin plugin) {
        plugin.getCommand("lightchainbreak").setExecutor(this);
        plugin.getCommand("lightchainbreak").setTabCompleter(new TabCompleter() {
            @Override
            public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
                if (strings.length==1) {
                     return subCommands;
                }

                if (strings.length==2 && strings[0].equals("toggle")){
                    return toggleSelector;
                }
                return null;
            }
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
                 commandSender.sendMessage(text.getLang("error.only-player"));
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
                commandSender.sendMessage(text.getLang("toggle.error"));
            }
            return true;
        }

        if (strings[0].equals("reload")){
            new file().reloadConfig();
            commandSender.sendMessage(text.getLang("reload"));
            return true;
        }
        return true;
    }

    private void help(CommandSender c){
        if (!c.hasPermission("lightchainbreak.help")){
            c.sendMessage(text.getLang("error.no-perms"));
            return;
        }
        c.sendMessage(text.getLang("running"));
        c.sendMessage("");
        c.sendMessage(Component.text("--------------------").color(TextColor.fromCSSHexString("#C8F1EF")));
        c.sendMessage(Component.text("/lightchainbreak reload").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.reload")));
        c.sendMessage(Component.text("/lightchainbreak toggle [enable|sneak-enable|display-count|item-protective]").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.toggle")));
        c.sendMessage(Component.text("/lightchainbreak about").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.about")));
        c.sendMessage(Component.text("/lightchainbreak help").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.help")));
    }

    private void about(CommandSender c){
        if (!c.hasPermission("lightchainbreak.about")){
            c.sendMessage(text.getLang("error.no-perms"));
            return;
        }
        c.sendMessage(text.getLang("running"));
        c.sendMessage("");
        c.sendMessage(text.getLang("about.desc"));
        c.sendMessage("");
        c.sendMessage(text.getLang("about.link")
                .append(Component.text("[GitHub]").clickEvent(ClickEvent.openUrl("https://github.com/Craft233MC/LightChainBreak"))
                        .color(TextColor.fromCSSHexString("#C8F1EF"))));

    }

    private void toggleEnable(Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(text.getLang("error.no-perms"));
            return;
        }
        YamlConfiguration data = file.getConfig("playerData");

        if(!data.getBoolean(p.getUniqueId()+".enabled")){
            data.set(p.getUniqueId()+".enabled", true);
            p.sendMessage(text.getLang("toggle.enabled"));
        } else {
            data.set(p.getUniqueId()+".enabled", false);
            p.sendMessage(text.getLang("toggle.disabled"));
        }

        new file().saveConfig("playerData", data);
    }

    private void toggleSneakEnable(Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(text.getLang("error.no-perms"));
            return;
        }

        YamlConfiguration data = file.getConfig("playerData");
        if (data.getBoolean( p.getUniqueId()+".sneak-to-enable")) {
            data.set( p.getUniqueId()+".sneak-to-enable", false);
            p.sendMessage( text.getLang("toggle.sneak-to-disabled"));
        } else {
            data.set( p.getUniqueId()+".sneak-to-enable", true);
            p.sendMessage( text.getLang("toggle.sneak-to-enabled"));
        }

        new file().saveConfig("playerData", data);
    }

    private void toggleDisplayCount (Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(text.getLang("error.no-perms"));
            return;
        }

         YamlConfiguration data = file.getConfig("playerData");
         if (data.getBoolean( p.getUniqueId()+".display-count")) {
             data.set( p.getUniqueId()+".display-count", false);
             p.sendMessage( text.getLang("toggle.display-count-disabled"));
         } else {
             data.set( p.getUniqueId()+".display-count", true);
             p.sendMessage( text.getLang("toggle.display-count-enabled"));
         }

        new file().saveConfig("playerData", data);
    }

    private void toggleItemProtective (Player p){
         if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(text.getLang("error.no-perms"));
            return;
        }

         YamlConfiguration data = file.getConfig("playerData");
         if (data.getBoolean( p.getUniqueId()+".item-protective")) {
             data.set( p.getUniqueId()+".item-protective", false);
             p.sendMessage( text.getLang("toggle.item-protective-disabled"));
         } else {
             data.set( p.getUniqueId()+".item-protective", true);
             p.sendMessage( text.getLang("toggle.item-protective-enabled"));
         }

        new file().saveConfig("playerData", data);
    }
}
