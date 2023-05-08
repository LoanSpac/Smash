package fr.loanspac.smash.champion;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Champion implements Listener {
    public abstract String name();
    public abstract void setArmor(Player player);
    public abstract void setItems(Player player);
    public abstract void setOffensif(Player player, ItemStack item);
    public abstract void setDefensif(Player player, ItemStack item);
    public abstract void setRecovery(Player player, ItemStack item);
    public abstract void setPassif(Player player, ItemStack item);
}
