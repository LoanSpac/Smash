package fr.loanspac.smash.guis.buttons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ButtonGUI {

    private final ItemStack icon;

    protected ButtonGUI(ItemStack icon) {
        this.icon = icon;
    }

    public ItemStack render() {
        return new ItemStack(icon);
    }

    public abstract void play(Player player);

    public Material getIcon() {
        return icon.getType();
    }

}