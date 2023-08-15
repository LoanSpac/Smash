package fr.loanspac.smash.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemManager {
    public static ItemStack getArmor(Material material, String name, int r, int g, int b) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setUnbreakable(true);
        LeatherArmorMeta itemColor = (LeatherArmorMeta) itemMeta;
        itemColor.setColor(Color.fromRGB(r,g,b));
        item.setItemMeta(itemColor);
        return item;
    }

    public static ItemStack getItem(Material material, String name, Boolean ench, Boolean unbreak) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemM = item.getItemMeta();
        itemM.setDisplayName(name);
        itemM.setUnbreakable(unbreak);
        if(Boolean.TRUE.equals(ench)) {
            itemM.addEnchant(Enchantment.DURABILITY, 1, true);
            itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemM);
        return item;
    }

    public static ItemStack getByteItem(Material material, String name, Boolean ench, Boolean unbreak, Byte bite) {
        ItemStack item = new ItemStack(material, 1, bite);
        ItemMeta itemM = item.getItemMeta();
        itemM.setDisplayName(name);
        itemM.setUnbreakable(unbreak);
        if(Boolean.TRUE.equals(ench)) {
            itemM.addEnchant(Enchantment.DURABILITY, 1, true);
            itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemM);
        return item;
    }

}
