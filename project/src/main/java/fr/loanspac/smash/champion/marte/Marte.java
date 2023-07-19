package fr.loanspac.smash.champion.marte;

import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.champion.marte.spell.MarteSpell;
import fr.loanspac.smash.utils.ItemManager;
import fr.loanspac.smash.utils.Skull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Marte extends Champion {

    @Override
    public String name() {
        return "Marte";
    }

    @Override
    public Double getWeight() {
        return 1d;
    }

    @Override
    public ItemStack[] getArmor(Player player) {
        ItemStack helmet = Skull.getSkull("§bMarte", "http://textures.minecraft.net/texture/968b446b3315130f35e4e0d5daeed0562a1565a11f16b5c2a90f5fad15a089be");
        ItemStack chesplate = ItemManager.getArmor(Material.LEATHER_CHESTPLATE, "§bTunique de Marte", 178,102,98);
        ItemStack leggings = ItemManager.getArmor(Material.LEATHER_LEGGINGS, "§bPantalon de Marte", 158,19,19);
        ItemStack boots = ItemManager.getArmor(Material.LEATHER_BOOTS, "§bBottes de Marte", 23,52,79);

        ItemStack[] armorContents = new ItemStack[4];

        armorContents[3] = helmet;
        armorContents[2] = chesplate;
        armorContents[1] = leggings;
        armorContents[0] = boots;

        return armorContents;
    }

    @Override
    public List<ItemStack> getItems(Player player) {
        ItemStack offensif = ItemManager.getItem(Material.GHAST_TEAR, "§dChope", true);
        ItemStack defensif = ItemManager.getItem(Material.CHAINMAIL_CHESTPLATE, "§dContre Total", true);
        ItemStack recovery = ItemManager.getItem(Material.FEATHER, "§dCharge du Dauphin", true);
        ItemStack passif = ItemManager.getItem(Material.FLINT, "§dPrécision", false);

        ArrayList<ItemStack> items = new ArrayList<>();

        items.add(offensif);
        items.add(defensif);
        items.add(recovery);
        items.add(passif);

        return items;
    }

    @Override
    public ItemStack getMainItem(Player player) {
        return ItemManager.getItem(Material.IRON_SWORD, "§aFalchion", false);
    }

    @Override
    public void setOffensif(Player player, ItemStack item) {
        new MarteSpell().offensif();
    }

    @Override
    public void setDefensif(Player player, ItemStack item) {
        new MarteSpell().defensif();
    }

    @Override
    public void setRecovery(Player player, ItemStack item) {
        new MarteSpell().recovery();
    }

    @Override
    public void setPassif(Player player) {
        new MarteSpell().passif();
    }
}
