package ink.neokoni.lightchainbreak.utils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import static ink.neokoni.lightchainbreak.utils.enchantments.hasUnbreak;

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

    public static void tryDamge(ItemStack i, Player p){
        if (!hasUnbreak(i)){
            int now = item.getDurability(i);
            item.setDurability(i, now-1);
        } else {
            int level = i.getEnchantmentLevel(Enchantment.DURABILITY);
            double odds = (double) (100 / (level + 1)) /100;
            if(Math.random() < odds){
                int now = item.getDurability(i);
                item.setDurability(i, now-1);
            }
        }

        if (item.getDurability(i) < 1 && !file.getConfig("playerData").getBoolean( p.getUniqueId() + ".item-protective")) {
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }
}
