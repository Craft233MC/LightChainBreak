package ink.neokoni.lightchainbreak.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class item {
    public static int getDurability(ItemStack i){
        if(!((Damageable)i.getItemMeta()).hasDamage()){
            return i.getType().getMaxDurability();
        }
        return i.getType().getMaxDurability() - ((Damageable)i.getItemMeta()).getDamage();
    }

    public static void setDurability(ItemStack i, int durability){
        ItemMeta meta = i.getItemMeta();
        Damageable damageable = (Damageable) meta;
        damageable.setDamage(i.getType().getMaxDurability() - durability);
        i.setItemMeta(damageable);
    }

    public static boolean hasDurability(ItemStack i){
        return !(i.getType().getMaxDurability() == 0);
    }
}
