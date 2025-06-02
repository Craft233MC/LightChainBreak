package ink.neokoni.lightchainbreak.handler;

import ink.neokoni.lightchainbreak.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class onBreak implements Listener {
    public static int countBreaks;

    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        YamlConfiguration playerData = file.getConfig("playerData");

        blocks.setCountBreakBlocks(0);

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block startBlock = event.getBlock();
        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();

        if (player.getGameMode().equals(GameMode.CREATIVE) || // Creative mode
            (tool.getType().equals(Material.AIR)) || // is empty in hand
            !new checker().isAllowed(startBlock, tool, player) || // check permission, block , tool
            !new checker().isPlayerEnabled(player) || // is player enabled chain break
            startBlock.getDrops(tool).isEmpty()  || // can be tool break and drop items?
            new checker().isSneaking(player) // playerData.yml playerUUID.sneaking-to-enable
        ){
                return;
        }

        if (item.getDurability(tool)<=1) return;

        queue.add(startBlock);
        visited.add(startBlock);

        int maxCanBreak = item.getDurability(tool);
        boolean itemProtective = playerData.getBoolean(player.getUniqueId()+".item-protective");
        if(enchantments.hasUnbreak(tool)) {
            maxCanBreak = enchantments.getMaxCanBreakFromEnchantment(tool);
        }

        if (!playerData.getBoolean(player.getUniqueId()+".item-protective")){
            maxCanBreak++;
        }

        while (!queue.isEmpty()) {
            Block current = queue.poll();
            for (Block relative : blocks.getRelative(current)) {
                if (new checker().isMaxBlocks(visited.size())) {
                    break;
                }
                if(visited.size() >= maxCanBreak){
                    break;
                }

                if (relative.getType() == current.getType()
                        && !visited.contains(relative)) {
                    visited.add(relative);
                    queue.add(relative);
                }
            }
        }

        visited.forEach(b -> {
            b.breakNaturally(tool);
            if(item.hasDurability(tool)){
                item.tryDamge(tool, player);
            }
        });

        blocks.setCountBreakBlocks(visited.size());

        if(playerData.getBoolean(player.getUniqueId()+".display-count")){
            countBreaks = visited.size(); // set value
            player.sendActionBar(text.getLang("msg.count-breaks"));
            countBreaks = 0; //clean
        }
    }
}
