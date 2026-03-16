package ink.neokoni.lightchainbreak.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.configs.PlayerData;
import ink.neokoni.lightchainbreak.configs.config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class checker {
    private final config.BaseConfig BaseConfig = config.getConfig();
    private final Set<String> skipPerms = Set.of("", "none", "null");
    public boolean isAllowed(Block block, ItemStack tool, Player p) {
        Set<String> groups = BaseConfig.getGroups().keySet();
        Material blockType = block.getType();
        Material toolType = tool.getType();
        for(String group : groups){
            if (group.contains(".")){
                continue;
            }

            List<String> tools = BaseConfig.getGroups().get(group).getTools();
            List<String> target = BaseConfig.getGroups().get(group).getTarget();

            Set<Material> toolSet = new HashSet<>();
            Set<Material> targetSet = new HashSet<>();
            for (String m : tools) {
                toolSet.add(Material.matchMaterial(m));
            }
            for (String m : target) {
                targetSet.add(Material.matchMaterial(m));
            }

            if(toolSet.contains(toolType) && targetSet.contains(blockType) && hasPerms(p, group)){
                return true;
            }
        }

        return false;
    }

    private boolean hasPerms(Player p, String group){
        String perms  = BaseConfig.getGroups().get(group).getPermission().toLowerCase();
        if (skipPerms.contains(perms) || perms.isEmpty() || perms==null){
            return true;
        }
        return p.hasPermission(perms);
    }

    public boolean isMaxBlocks(int count){
        return count >= BaseConfig.getMaxBreak();
    }

    public boolean isPlayerEnabled(Player p){
        return PlayerData.getPlayerData(p, false).isEnabled();
    }

    public boolean isSneaking(Player p){
        if(PlayerData.getPlayerData(p, false).isSneakToEnable()){
            return !p.isSneaking();
        }
        return false;
    }

    public boolean hasResidencePerms(Location l, Player p) {
        if (LightChainBreak.residencePlugin==false) { // not install Residence
            return true;
        }

        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(l);

        if (res==null) { // this location not have residence
            return true;
        }

        ResidencePermissions resPerms = res.getPermissions();
        Boolean defaultValue = Residence.getInstance().getConfigManager().getGlobalResidenceDefaultFlags().getFlags().get("destroy");

        return resPerms.playerHas(p, Flags.destroy, defaultValue);
    }
}
