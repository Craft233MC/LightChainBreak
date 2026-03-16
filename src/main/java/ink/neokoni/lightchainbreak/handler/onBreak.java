package ink.neokoni.lightchainbreak.handler;

import ink.neokoni.lightchainbreak.configs.Datas.PlayerDataInfo;
import ink.neokoni.lightchainbreak.configs.PlayerData;
import ink.neokoni.lightchainbreak.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class onBreak implements Listener {
    private Map<Player, Set<Block>> breakingPlayers = new HashMap<>();
    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block startBlock = event.getBlock();
        if (breakingPlayers.containsKey(player)) {
            if (breakingPlayers.get(player).contains(startBlock)) {
                return; // Skip if the block is already being processed for this player
            }
        }
        PlayerDataInfo playerData = PlayerData.getPlayerData(player, false);
        ItemStack tool = player.getInventory().getItemInMainHand();
        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();

        if (player.getGameMode().equals(GameMode.CREATIVE) || // Creative mode
            (tool.getType().equals(Material.AIR)) || // is empty in hand
            !new checker().isAllowed(startBlock, tool, player) || // check permission, block , tool
            !new checker().isPlayerEnabled(player) || // is player enabled chain break
            startBlock.getDrops(tool).isEmpty()  || // can be tool break and drop items?
            new checker().isSneaking(player) || // PlayerData.yml playerUUID.sneaking-to-enable
            !new checker().hasResidencePerms(startBlock.getLocation(), player) // is player has residence perms?
        ){
                return;
        }

        if (item.getDurability(tool)<=1) return;

        queue.add(startBlock);
        visited.add(startBlock);

        int maxCanBreak = item.getDurability(tool);
        if(enchantments.hasUnbreak(tool)) {
            maxCanBreak = enchantments.getMaxCanBreakFromEnchantment(tool);
        }

        if (!playerData.isItemProtective()){
            maxCanBreak++;
        }

        while (!queue.isEmpty()) {
            Block current = queue.poll();
            for (Block relative : blocks.getRelatives(current)) {
                if (new checker().isMaxBlocks(visited.size())) {
                    break;
                }
                if(visited.size() >= maxCanBreak){
                    break;
                }

                if (!new checker().hasResidencePerms(relative.getLocation(), player)) {
                    break;
                }

                if (relative.getType() == current.getType()
                        && !visited.contains(relative)) {
                    visited.add(relative);
                    queue.add(relative);
                }
            }
        }
        breakingPlayers.put(player, visited);
        for (Block block : visited) {
            if(item.hasDurability(tool)){
                player.breakBlock(block);
            }
        }
        breakingPlayers.remove(player);

        if(playerData.isDisplayCount()){
            player.sendActionBar(text.getLang("msg.count-breaks", "%count%", String.valueOf(visited.size())));
        }
    }
}
