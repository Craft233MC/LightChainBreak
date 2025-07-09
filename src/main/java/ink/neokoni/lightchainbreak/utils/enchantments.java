package ink.neokoni.lightchainbreak.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class enchantments {
    public static boolean hasUnbreak(ItemStack i){
        return i.containsEnchantment(Enchantment.DURABILITY);
    }

    public static int getMaxCanBreakFromEnchantment(ItemStack i){ // durage and enchantment -> how many blocks can break
        if(!hasUnbreak(i)){return item.getDurability(i);}

        int durage = item.getDurability(i);
        int level = i.getEnchantmentLevel(Enchantment.DURABILITY);
        double odds = (double) (100 / (level + 1)) /100;
        int times=0;
        for (int j = durage; j >= 1; j--) {
            if(Math.random() > odds){
                times++;
                j++;
            }
        }
        return times;
    }


}
