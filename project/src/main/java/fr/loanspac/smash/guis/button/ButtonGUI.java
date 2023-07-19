package fr.loanspac.smash.guis.button;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ButtonGUI {

    private final Material icon;
    private final String name;

    protected ButtonGUI(Material icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public ItemStack render() {
        ItemStack stack = new ItemStack(icon);
        if (!stack.hasItemMeta()) {
            stack.setItemMeta(Bukkit.getItemFactory().getItemMeta(icon));
        }

        stack.getItemMeta().setDisplayName(name);
        return stack;
    }

    public abstract void play(Player player);

    public Material getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

}
