package fr.loanspac.smash.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemManager {
    public static ItemStack getArmor(Material material, String name, int B, int G, int R) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setUnbreakable(true);
        LeatherArmorMeta itemColor = (LeatherArmorMeta) itemMeta;
        itemColor.setColor(Color.fromBGR(B,G,R));
        item.setItemMeta(itemColor);
        return item;
    }

    public static ItemStack getItem(Material material, String name, Boolean ench) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemM = item.getItemMeta();
        itemM.setDisplayName(name);
        itemM.setUnbreakable(true);
        if(ench) {
            itemM.addEnchant(Enchantment.DURABILITY, 1, true);
            itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemM);
        return item;
    }
}
