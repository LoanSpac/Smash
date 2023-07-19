package fr.loanspac.smash.champion;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Champion {
    public abstract String name();
    public abstract Double getWeight();
    public abstract ItemStack[] getArmor(Player player);
    public abstract List<ItemStack> getItems(Player player);
    public abstract ItemStack getMainItem(Player player);
    public abstract void setOffensif(Player player, ItemStack item);
    public abstract void setDefensif(Player player, ItemStack item);
    public abstract void setRecovery(Player player, ItemStack item);
    public abstract void setPassif(Player player);
}
