package ink.neokoni.lightchainbreak.Utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class ItemUtils {
    public static int getDurability(ItemStack i){
        if(!((Damageable)i.getItemMeta()).hasDamage()){
            return i.getType().getMaxDurability();
        }
        return i.getType().getMaxDurability() - ((Damageable)i.getItemMeta()).getDamage();
    }

    public static boolean hasDurability(ItemStack i){
        return !(i.getType().getMaxDurability() == 0);
    }

    public static boolean canBreak(ItemStack tool, boolean isItemProtective) {
        if (isItemProtective) {
            if (hasDurability(tool)) {
                int durability = getDurability(tool);
                return durability > 1;
            }
            return hasDurability(tool);
        } else {
            return hasDurability(tool) && getDurability(tool) > 0;
        }
    }
}
