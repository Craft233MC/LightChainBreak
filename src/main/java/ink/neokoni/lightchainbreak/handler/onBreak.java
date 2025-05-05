package ink.neokoni.lightchainbreak.handler;

import ink.neokoni.lightchainbreak.utils.checker;
import ink.neokoni.lightchainbreak.utils.enchantments;
import ink.neokoni.lightchainbreak.utils.item;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ink.neokoni.lightchainbreak.utils.blocks;

import java.util.*;

public class onBreak implements Listener {

    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        blocks.setCountBreakBlocks(0);

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block startBlock = event.getBlock();
        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();

        if ( player.getGameMode().equals(GameMode.CREATIVE) || // Creative mode
            !new checker().isAllowed(startBlock, tool, player) || // check permission, block , tool
            !new checker().isPlayerEnabled(player) || // is player enabled chain break
            startBlock.getDrops(tool).isEmpty()  || // can be tool break and drop items?
            new checker().isSneaking(player) // config.yml config.sneaking-to-enable
        ){
                return;
        }

        queue.add(startBlock);
        visited.add(startBlock);

        int maxCanBreak = item.getDurability(tool);
        if(enchantments.hasUnbreak(tool)) {
            maxCanBreak = enchantments.getMaxCanBreakFromEnchantment(tool);
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
                enchantments.tryDamge(tool);
            }
        });
        blocks.setCountBreakBlocks(visited.size());
    }
}
