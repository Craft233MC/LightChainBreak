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
    private final Map<Player, Set<Block>> playerBlocks = new HashMap<>();
    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        if (playerBlocks.containsKey(event.getPlayer())) {
            return;
        }
        YamlConfiguration playerData = file.getConfig("playerData");

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block startBlock = event.getBlock();
        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();
        ItemStack originalTool = null;


        if (playerData.getBoolean(player.getUniqueId()+".item-protective")){
            originalTool = tool.clone();
        }

        if (player.getGameMode().equals(GameMode.CREATIVE) || // Creative mode
            (tool.getType().equals(Material.AIR)) || // is empty in hand
            !new checker().isAllowed(startBlock, tool, player) || // check permission, block , tool
            !new checker().isPlayerEnabled(player) || // is player enabled chain break
            new checker().isSneaking(player) || // playerData.yml playerUUID.sneaking-to-enable
            !new checker().hasResidencePerms(startBlock.getLocation(), player) // is player has residence perms?
        ){
                return;
        }

        queue.add(startBlock);
        visited.add(startBlock);

        while (!queue.isEmpty()) {
            Block current = queue.poll();
            for (Block relative : blocks.getRelatives(current)) {
                if (new checker().isMaxBlocks(visited.size())) {
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

        playerBlocks.put(player, visited);
        for (Block block : visited) {
            player.breakBlock(block);
        }
        playerBlocks.remove(player);

        if (originalTool!=null) {
            player.getInventory().setItemInMainHand(originalTool);
        }

        if(playerData.getBoolean(player.getUniqueId()+".display-count")){
            player.sendActionBar(text.getLang("msg.count-breaks", "%count%", String.valueOf(visited.size())));
        }
    }
}
