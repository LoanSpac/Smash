package fr.loanspac.smash.champion.bario;

import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.champion.bario.spell.BarioSpell;
import fr.loanspac.smash.utils.ItemManager;
import fr.loanspac.smash.utils.Skull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Bario extends Champion {

    @Override
    public String name() {
        return "Bario";
    }

    @Override
    public Double getWeight() {
        return 1d;
    }

    @Override
    public ItemStack[] getArmor(Player player) {
        ItemStack helmet = Skull.getSkull("§bBario", "http://textures.minecraft.net/texture/d70d3ece62a3b12344c4b1304a839c692ddde3370bc76a38fc0cca79ec5f2ab8");
        ItemStack chesplate = ItemManager.getArmor(Material.LEATHER_CHESTPLATE, "§bTunique de Bario", 213,6,6);
        ItemStack leggings = ItemManager.getArmor(Material.LEATHER_LEGGINGS, "§bPantalon de Bario", 150,6,6);
        ItemStack boots = ItemManager.getArmor(Material.LEATHER_BOOTS, "§bBottes de Bario", 60,99,166);

        ItemStack[] armorContents = new ItemStack[4];

        armorContents[3] = helmet;
        armorContents[2] = chesplate;
        armorContents[1] = leggings;
        armorContents[0] = boots;

        return armorContents;
    }

    @Override
    public List<ItemStack> getItems(Player player) {
        ItemStack offensif = ItemManager.getItem(Material.FIREBALL, "§dBoule de feu", true);
        ItemStack defensif = Skull.getSkull("§bSuper Champignon", "http://textures.minecraft.net/texture/ff96b8d01f5835ed38afd4530228d0b5abb7d45a355519ea680c40ffca32edf2");
        ItemStack recovery = ItemManager.getItem(Material.FEATHER, "§d360 Jump", true);
        ItemStack passif = ItemManager.getItem(Material.SUGAR, "§dSpeed", false);

        ArrayList<ItemStack> items = new ArrayList<>();

        items.add(offensif);
        items.add(defensif);
        items.add(recovery);
        items.add(passif);

        return items;
    }

    @Override
    public ItemStack getMainItem(Player player) {
        return null;
    }

    @Override
    public void setOffensif(Player player, ItemStack item) {
        new BarioSpell().offensif(player, item);
    }

    @Override
    public void setDefensif(Player player, ItemStack item) {
        new BarioSpell().defensif(player, item);
    }

    @Override
    public void setRecovery(Player player, ItemStack item) {
        new BarioSpell().recovery(player, item);
    }

    @Override
    public void setPassif(Player player) {
        new BarioSpell().passif(player);
    }
}
