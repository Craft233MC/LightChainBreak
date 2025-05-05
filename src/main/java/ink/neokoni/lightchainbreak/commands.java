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
    public void register(JavaPlugin plugin) {
        plugin.getCommand("lightchainbreak").setExecutor(this);
        plugin.getCommand("lightchainbreak").setTabCompleter(new TabCompleter() {
            @Override
            public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
                return subCommands;
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
            if (isPlayer(commandSender)){
                toggle((Player) commandSender);
            } else {
                commandSender.sendMessage(text.getLang("error.only-player"));
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
        c.sendMessage(Component.text("/lightchainbreak reload").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.toggle")));
        c.sendMessage(Component.text("/lightchainbreak reload").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.about")));
        c.sendMessage(Component.text("/lightchainbreak reload").color(TextColor.fromCSSHexString("#C8F1EF")).append(text.getLang("help.help")));
    }

    private void about(CommandSender c){
        if (!c.hasPermission("lightchainbreak.abbout")){
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

    private void toggle(Player p){
        if (!p.hasPermission("lightchainbreak.toggle")){
            p.sendMessage(text.getLang("error.no-perms"));
            return;
        }
        YamlConfiguration data = file.getConfig("playerData");

        if(!data.getBoolean(p.getUniqueId()+".enabled")){
            data.set(p.getUniqueId()+".enabled", true);
            new file().saveConfig("playerData", data);
            p.sendMessage(text.getLang("enabled"));
        } else {
            data.set(p.getUniqueId()+".enabled", false);
            new file().saveConfig("playerData", data);
            p.sendMessage(text.getLang("disabled"));
        }

    }
}
